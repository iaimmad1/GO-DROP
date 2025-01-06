package com.example.grocerymanagementanddeliverysystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {

    private static final String CART_URL = "http://192.168.1.109:8080/cartApi/find";
    private RequestQueue requestQueue;
    private ListView listViewCart;
    private CartAdapter cartAdapter;
    private List<Cart> cartList;
    private TextView textViewSubtotal;
    private TextView textViewDeliveryCost;
    private TextView textViewTotalPrice;
    private Button checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNavigationView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        listViewCart = findViewById(R.id.listViewCart);
        textViewSubtotal = findViewById(R.id.textViewSubtotal);
        textViewDeliveryCost = findViewById(R.id.textViewDeliveryCost);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        requestQueue = Volley.newRequestQueue(this);

        // Initialize the list and adapter
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, cartList);
        listViewCart.setAdapter(cartAdapter);

        // Fetch cart data
        fetchCartData();

        // Set up button listener
        Button checkout = findViewById(R.id.buttonCheckout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               placeOrder();
            }
        });
    }

    private void fetchCartData() {
        SharedPreferences sharedPreferences = getSharedPreferences("Rajesh", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId",null);
        if (userId == null) {
            Toast.makeText(MyCart.this, "User ID not found", Toast.LENGTH_SHORT).show();
            return;
        }else {

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, CART_URL + "/" + userId, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {

                                // Clear the list before adding new data
                                cartList.clear();

                                // Loop through the response and parse each item
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    // Parse JSON data
                                    String item = jsonObject.getString("item");
                                    long quantity = jsonObject.getLong("quantity");
                                    float totalPrice = (float) jsonObject.getDouble("total_price");

                                    // Create a new Cart object and add it to the list
                                    Cart cart = new Cart();
                                    cart.setItem(item);
                                    cart.setQuantity(quantity);
                                    cart.setTotal_price(totalPrice);

                                    cartList.add(cart);
                                }

                                // Notify the adapter about data changes
                                cartAdapter.notifyDataSetChanged();

                                // Calculate and update costs
                                paisa();

                            } catch (JSONException e) {
                                Log.d("exceptioncart", e.toString());
                                Toast.makeText(MyCart.this, "Error parsing cart data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MyCart", "Volley error: " + error.toString());
                    Toast.makeText(MyCart.this, "Failed to fetch cart data", Toast.LENGTH_SHORT).show();
                }
            });

            // Add the request to the queue
            requestQueue.add(request);
        }
    }

    private void paisa() {
        float subtotal = 0;

        // Calculate subtotal
        for (Cart cart : cartList) {
            subtotal += cart.getTotal_price() * cart.getQuantity();
        }

        float deliveryCost = 50; // Flat delivery cost
        float totalCost = subtotal + deliveryCost;

        // Update the TextViews with calculated values
        textViewSubtotal.setText("Subtotal: Rs" + String.format("%.2f", subtotal));
        textViewDeliveryCost.setText("Delivery Cost: Rs" + String.format("%.2f", deliveryCost));
        textViewTotalPrice.setText("Total: Rs" + String.format("%.2f", totalCost));
    }


    private void placeOrder() {
        try {
            JSONObject orderJson = new JSONObject();
            JSONArray cartsJsonArray = new JSONArray();

            // Create JSON array of carts
            for (Cart cart : cartList) {
                JSONObject cartJson = new JSONObject();
                cartJson.put("item", cart.getItem());
                cartJson.put("quantity", cart.getQuantity());
                cartJson.put("total_price", cart.getTotal_price());
                cartsJsonArray.put(cartJson);
            }

            // Populate the order JSON
            orderJson.put("total", textViewTotalPrice.getText().toString().replace("Total: Rs", ""));
            orderJson.put("carts", cartsJsonArray);
            orderJson.put("profile", new JSONObject().put("id", 1)); // Set profile ID as 1 or dynamically as required

            // Create a JsonObjectRequest to insert order data
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    "http://192.168.1.109:8080/orderInfoApi/create",
                    orderJson,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(MyCart.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                            Log.d("OrderResponse", response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MyCart.this, "Failed to place order: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("OrderError", error.toString());
                        }
                    }
            );

            // Add the request to the RequestQueue
            requestQueue.add(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MyCart.this, "Error creating order JSON", Toast.LENGTH_SHORT).show();
        }
    }

}
