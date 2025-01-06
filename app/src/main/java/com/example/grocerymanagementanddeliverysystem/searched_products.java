package com.example.grocerymanagementanddeliverysystem;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class searched_products extends Activity {

    private ListView searchedProductsListView;
    private RequestQueue requestQueue;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_products);

        searchedProductsListView = findViewById(R.id.listviewofserchproductwalapage);

        requestQueue = Volley.newRequestQueue(this);

        String query = getIntent().getStringExtra("query");
        if (query != null && !query.isEmpty()) {
//            searchProducts(query);
        } else {
            Toast.makeText(this, "No query found", Toast.LENGTH_SHORT).show();
        }
    }
    public static class Product {
        private Long id;
        private String name;
        private float price;
        private long quantity;
        private String description;

        public Product(Long id, String name, float price, long quantity, String description) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.description = description;
        }

        // Getters for the product properties
        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public float getPrice() {
            return price;
        }

        public long getQuantity() {
            return quantity;
        }

        public String getDescription() {
            return description;
        }
    }


}
