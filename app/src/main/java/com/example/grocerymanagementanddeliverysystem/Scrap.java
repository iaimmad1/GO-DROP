package com.example.grocerymanagementanddeliverysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Scrap extends AppCompatActivity {
    private Spinner scrapspinner;
    private EditText etscrap;
    private Button btnScrap;
    private RequestQueue requestQueue;
    private List<String> categoryNames = new ArrayList<>();
    String url = "http://192.168.1.109:8080/categoryApi/find";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);

        scrapspinner = findViewById(R.id.scrap);
        etscrap = findViewById(R.id.etscrap);
        btnScrap = findViewById(R.id.btn_scrap);
        requestQueue = Volley.newRequestQueue(this);
        fetchCategories();

        btnScrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedCategory = scrapspinner.getSelectedItem().toString();
                etscrap.setText(selectedCategory);
            }
        });
    }

    private void fetchCategories() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObject = response.getJSONObject(i);
                                String categoryName = categoryObject.getString("categoryName");
                                categoryNames.add(categoryName);
                            }
                            populateSpinner();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Scrap.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Scrap.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void populateSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                categoryNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        scrapspinner.setAdapter(adapter);
    }
}