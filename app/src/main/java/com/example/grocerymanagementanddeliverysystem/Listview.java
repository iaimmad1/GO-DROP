package com.example.grocerymanagementanddeliverysystem;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Listview extends AppCompatActivity {

    private ListView lv;
    private ArrayList<String> categoriesList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        lv = findViewById(R.id.listView);
        categoriesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriesList);
        lv.setAdapter(adapter);

        fetchCategories();
    }

    private void fetchCategories() {
        String url = "https://192.168.1.109/categoryApi/find";
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
                    Toast.makeText(Listview.this, "Categories fetched successfully!", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(Listview.this, "Error fetching categories: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error: " + error.getMessage());

                Toast.makeText(Listview.this, "Error fetching categories by volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                if (error instanceof NetworkError) {
                    Toast.makeText(Listview.this, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Listview.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Listview.this, "Authentication failure. Please check your credentials.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Listview.this, "Error parsing data. Please try again later.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Listview.this, "No internet connection. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(Listview.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        queue.add(request);


    }
}