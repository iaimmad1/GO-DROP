package com.example.grocerymanagementanddeliverysystem;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class shop_information extends AppCompatActivity {

    private EditText shopNameEditText, shopContactEditText, shopEmailEditText, shopAddressEditText;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_information);

        shopNameEditText = findViewById(R.id.shopNameEditText);
        shopContactEditText = findViewById(R.id.shopContactEditText);
        shopEmailEditText = findViewById(R.id.shopEmailEditText);
        shopAddressEditText = findViewById(R.id.shopAddressEditText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertSellerData();
            }
        });
    }

    private void insertSellerData() {
        String url = "http://192.168.1.109:8080/sellerApi/create";  // Replace with your actual API URL

        // Extract data from EditText fields
        String shopName = shopNameEditText.getText().toString().trim();
        String shopContact = shopContactEditText.getText().toString().trim();
        String shopEmail = shopEmailEditText.getText().toString().trim();
        String shopAddress = shopAddressEditText.getText().toString().trim();
        long profileId = 1; // Set the profile ID to 1 as specified

        // Create the JSON object to be sent to the server
        JSONObject sellerData = new JSONObject();
        try {
            sellerData.put("shopName", shopName);
            sellerData.put("shopContact", shopContact);
            sellerData.put("shopEmail", shopEmail);
            sellerData.put("shopAddress", shopAddress);
            sellerData.put("profile", new JSONObject().put("id", profileId));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Create a Volley request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the response from the server
                        Toast.makeText(shop_information.this, "Seller data saved successfully!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Toast.makeText(shop_information.this, "Failed to save data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public byte[] getBody() {
                return sellerData.toString().getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
