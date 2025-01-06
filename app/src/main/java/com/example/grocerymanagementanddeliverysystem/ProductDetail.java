package com.example.grocerymanagementanddeliverysystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetail extends AppCompatActivity {

    private TextView titleTxt, priceTxt, descriptionTxt;
    private EditText quantity;
    private Button addToCartBtn, view;
    private RequestQueue queue;
    private int productId;
    private String productName;
    private double productPrice;
    private String productDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        titleTxt = findViewById(R.id.titleTxt);
        priceTxt = findViewById(R.id.priceTxt);
        quantity = findViewById(R.id.cartQuantity);
        descriptionTxt = findViewById(R.id.descriptionTxt);
        addToCartBtn = findViewById(R.id.addTocartBtn);
        view = findViewById(R.id.view);

        queue = Volley.newRequestQueue(this);

        productId = getIntent().getIntExtra("id", 0);
        Log.d("ProductDetail", "Product ID: " + productId);
        fetchProductDetails(productId);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProductToCart();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductDetail.this, item_cart.class);
                startActivity(intent);
            }
        });
    }

    private void fetchProductDetails(int productId) {
        String url = "http://192.168.1.109:8080/productApi/find/" + productId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(ProductDetail.this, "success ", Toast.LENGTH_SHORT).show();
                            productName = response.getString("name");
                            productPrice = response.getDouble("price");
                            productDescription = response.getString("description");

                            titleTxt.setText(productName);
                            priceTxt.setText("Rs. " + productPrice);
                            descriptionTxt.setText(productDescription);
                        } catch (JSONException e) {
                            Log.e("ProductDetail", "JSON Parsing error: " + e.getMessage());
                            Toast.makeText(ProductDetail.this, "Error fetching product details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ProductDetail", "Volley error: " + error.toString());
//                handleVolleyError(error);
            }
        });

        queue.add(request);
    }

    private void addProductToCart() {
        String url = "http://192.168.1.109:8080/cartApi/create";

        JSONObject cartItem = new JSONObject();
        try {
            cartItem.put("item",titleTxt.getText().toString());
            cartItem.put("quantity",quantity.getText().toString());
            String price = priceTxt.getText().toString();
            String pricce1 = price.substring(price.indexOf(" ")+1);
            cartItem.put("total_price", Double.parseDouble(pricce1));

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, cartItem,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(ProductDetail.this, "Product added to cart!", Toast.LENGTH_SHORT).show();
                            Log.d("flow"," cart response"+response);
                            try {
                                String cartId = response.getString("id");
                                updateUserId(cartId);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            startActivity(new Intent(ProductDetail.this, MyCart.class));

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("ProductDetail", "Volley error: " + error.toString());
//                    handleVolleyError(error);
                }
            });

            queue.add(request);
        } catch (JSONException e) {
            Log.e("ProductDetail", "JSON Error: " + e.getMessage());
            Toast.makeText(ProductDetail.this, "Error adding product to cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void handleVolleyError(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(ProductDetail.this, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(ProductDetail.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(ProductDetail.this, "Authentication failure. Please check your credentials.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(ProductDetail.this, "Error parsing data. Please try again later.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(ProductDetail.this, "No internet connection. Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(ProductDetail.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
    public void updateUserId(String cartId){
        SharedPreferences sharedPreferences = getSharedPreferences("Rajesh", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId",null);
        String url = "http://192.168.1.109:8080/cartApi/update/"+cartId+"/"+userId;

        JSONObject cartItem = new JSONObject();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ProductDetail.this, "profileID Updated", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ProductDetail", "Volley error: " + error.toString());
                handleVolleyError(error);
            }
        });

        queue.add(request);
    }
}
