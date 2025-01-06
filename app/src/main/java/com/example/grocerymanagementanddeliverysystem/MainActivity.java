package com.example.grocerymanagementanddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private RequestQueue requestQueue;

    private List<Product> productList;
    private Button searchButton;
    private EditText searchEditText;
    private ArrayAdapter<String> adapter;
    private String url = "http://192.168.1.109:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        // Set padding for bottom navigation view to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNavigationView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            String message;

            if (itemId == R.id.products) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.category) {
                Intent intent = new Intent(MainActivity.this, Categories.class);
                startActivity(intent);
            } else if (itemId == R.id.cart) {
                Intent intent = new Intent(MainActivity.this, MyCart.class);
                startActivity(intent);
            } else if (itemId == R.id.account) {
                Intent intent = new Intent(MainActivity.this, Profile.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this,"invalid choice", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

//         Initialize UI components
        listView = findViewById(R.id.list);
        searchEditText = findViewById(R.id.editText2);
        searchButton = findViewById(R.id.searchproducts);

        // Initialize product list and adapter
        productList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        listView.setAdapter(adapter);

        // Set item click listener to open product details
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Product product = productList.get(position);
            fetchProductDetails(product.getId());
        });

//         Set search button click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchEditText.getText().toString();
                if (!query.isEmpty()) {
//                    searchProducts(query);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a search term", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Fetch and display the products when the activity starts
        getProducts();
    }

//    private void searchProducts() {
//        String query = searchEditText.getText().toString().trim();
//        if (!query.isEmpty()) {
//            Intent intent = new Intent(MainActivity.this, searched_products.class);
//            intent.putExtra("query", query);
//            startActivity(intent);
//        } else {
//            Toast.makeText(MainActivity.this, "Please enter a query", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void getProducts() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url + "productApi/find", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            productList.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("name");
                                String price = jsonObject.getString("price");
                                productList.add(new Product(id, name, price));
                            }
                            updateListView();
//                            Log.d("Volley", "Products fetched successfully: " + response.toString());
//                            Toast.makeText(MainActivity.this, "Products fetched successfully!", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e("Error", "Error parsing products: " + e.getMessage());
                            Toast.makeText(MainActivity.this, "Error fetching products: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + (error.getMessage() != null ? error.getMessage() : "Unknown error"));
                        Toast.makeText(MainActivity.this, "Error fetching products: " + (error.getMessage() != null ? error.getMessage() : "Unknown error"), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(request);
    }

     private void updateListView() {
        List<String> productNames = productList.stream()
                .map(Product::getName)
                .collect(Collectors.toList());

        adapter.clear();
        adapter.addAll(productNames);
        adapter.notifyDataSetChanged();
    }

    private void fetchProductDetails(int productId) {
        String productUrl = url + "productApi/find/" + productId;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, productUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            String price = response.getString("price");
                            String description = response.getString("description");

                            Intent intent = new Intent(MainActivity.this, ProductDetail.class);
                            intent.putExtra("id", productId);
                            intent.putExtra("name", name);
                            intent.putExtra("price", price);
                            intent.putExtra("description", description);
                            startActivity(intent);
                        } catch (JSONException e) {
                            Log.e("Error", "Error parsing product details: " + e.getMessage());
                            Toast.makeText(MainActivity.this, "Error fetching product details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error: " + (error.getMessage() != null ? error.getMessage() : "Unknown error"));
                        Toast.makeText(MainActivity.this, "Error fetching product details: " + (error.getMessage() != null ? error.getMessage() : "Unknown error"), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }

    private void searchProducts(String query) {
        String searchUrl = url + "productApi/search?name=" + query;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                searchUrl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        productList.clear(); // Clear the existing list

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject productJson = response.getJSONObject(i);
                                int id = productJson.getInt("id");
                                String name = productJson.getString("name");
                                String price = productJson.getString("price");

                                // Create a Product object and add it to the list
                                Product product = new Product(id, name, price);
                                productList.add(product);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        updateListView(); // Update the ListView with the new data
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }


    // Product class
    public static class Product {
        private final int id;
        private final String name;
        private final String price;

        public Product(int id, String name, String price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }
    }
}
