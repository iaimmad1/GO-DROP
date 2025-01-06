package com.example.grocerymanagementanddeliverysystem;

import android.os.Bundle;
import android.util.Log;
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
public class seller_profile extends AppCompatActivity {

    private TextView fullnameTextView;
    private TextView emailTextView;
    private TextView mobileTextView;
    private TextView addressTextView;
    private RequestQueue queue;
    private TextView shopNameTextView;
    private TextView shopPhoneTextView;
    private TextView shopEmailTextView;
    private TextView shopAddressTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_profile);

        // Initialize TextViews
        fullnameTextView = findViewById(R.id.fullnameq);
        emailTextView = findViewById(R.id.emails);
        mobileTextView = findViewById(R.id.mob);
        addressTextView = findViewById(R.id.addr);
        // Initialize TextViews for shop information
        shopNameTextView = findViewById(R.id.shopName);
        shopPhoneTextView = findViewById(R.id.shopPhone);
        shopEmailTextView = findViewById(R.id.shopEmail);
        shopAddressTextView = findViewById(R.id.shopAddress);


        // Initialize the RequestQueue
        queue = Volley.newRequestQueue(this);

        int productId = getIntent().getIntExtra("id", 0);  Log.d("ProductDetail", "Product ID: " + productId);
        personalinfo();
        shopinfo();
    }

    private void personalinfo() {
        String url = "http://192.168.1.109:8080/profileApi/read/1";
        Log.d("ProductDetail", "Request URL: " + url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("ProductDetail", "Response: " + response.toString());
                try {
                    String fullname = response.getString("firstName")+"  "+response.getString("middleName")+"  "+response.getString("lastName");
                    String email = response.getString("email");
                    String contact = response.getString("contact");
                    String address = response.getString("address");

                    fullnameTextView.setText(fullname);
                    emailTextView.setText("Email Address:  "+email);
                    addressTextView.setText("Contact Number:  "+contact);
                    mobileTextView.setText("Address:  "+address);

                } catch (JSONException e) {
                    Log.e("ProductDetail", "JSON Parsing error: " + e.getMessage());
                    Toast.makeText(seller_profile.this, "Error fetching product details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ProductDetail", "Volley error: " + error.toString());
                Toast.makeText(seller_profile.this, "Error fetching product details by volley Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                handleErrorResponse(error);
            }
        });

        queue.add(request);
    }
    private void shopinfo() {
        String url = "http://192.168.1.109:8080/sellerApi/find/1"; // Replace with your actual API URL and seller ID

        // Create a JsonObjectRequest to fetch seller data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                      try {
                            // Extract data from the JSON response
                            String shopName = response.getString("shopName");
                            String shopContact = response.getString("shopContact");
                            String shopEmail = response.getString("shopEmail");
                            String shopAddress = response.getString("shopAddress");

                          shopNameTextView.setText("Shop Name:  "+shopName);
                          shopPhoneTextView.setText("Shop PhoneNumber:  "+shopContact);
                          shopAddressTextView.setText("Shop Address:  "+shopAddress);
                          shopEmailTextView.setText("Shop Email:  "+shopEmail);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(seller_profile.this, "Failed to parse data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle the error
                        Toast.makeText(seller_profile.this, "Failed to fetch data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    private void handleErrorResponse(VolleyError error) {
        Toast.makeText(seller_profile.this, "Error fetching details: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        if (error instanceof NetworkError) {
            Toast.makeText(seller_profile.this, "Network error. Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(seller_profile.this, "Server error. Please try again later.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(seller_profile.this, "Authentication failure. Please check your credentials.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(seller_profile.this, "Error parsing data. Please try again later.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(seller_profile.this, "No internet connection. Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(seller_profile.this, "Timeout error. Please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}