package com.example.grocerymanagementanddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Categories extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> categoriesList;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catagories);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.bottomNavigationView), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            String message;

            if (itemId == R.id.products) {
                Intent intent = new Intent(Categories.this, MainActivity.class);
                startActivity(intent);
            } else if (itemId == R.id.category) {
                Intent intent = new Intent(Categories.this, Categories.class);
                startActivity(intent);
            } else if (itemId == R.id.cart) {
                Intent intent = new Intent(Categories.this, MyCart.class);
                startActivity(intent);
            } else if (itemId == R.id.account) {
                Intent intent = new Intent(Categories.this, Profile.class);
                startActivity(intent);
            } else {
                Toast.makeText(Categories.this,"invalid choice", Toast.LENGTH_SHORT).show();
            }
            return true;
        });


        listView = findViewById(R.id.listViewOrders);
        categoriesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriesList);
        listView.setAdapter(adapter);
        fetchCategories();
    }

    private void fetchCategories() {
        String url = "http://192.168.1.109:8080/categoryApi/find";
        RequestQueue queue = Volley.newRequestQueue(this);


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    categoriesList.clear(); // clear the list before adding new items
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String categoryName = jsonObject.getString("categoryName");
                        categoriesList.add(categoryName);
                    }
                    adapter.notifyDataSetChanged();
                    Log.d("Volley", "Response received: " + response.toString());
                    Toast.makeText(Categories.this, "Categories fetched successfully!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(Categories.this, "Error fetching categories: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error: " + error.getMessage());

                Toast.makeText(Categories.this, "Error fetching categories by volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                if (error instanceof NetworkError) {
                    Toast.makeText(Categories.this, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Categories.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Categories.this, "Authentication failure. Please check your credentials.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Categories.this, "Error parsing data. Please try again later.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Categories.this, "No internet connection. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Categories.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        queue.add(request);


    }
}