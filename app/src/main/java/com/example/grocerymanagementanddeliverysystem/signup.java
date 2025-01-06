package com.example.grocerymanagementanddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class signup extends AppCompatActivity {

    private EditText firstname;
    private EditText middlename;
    private EditText lastname;
    private EditText email;
    private EditText mobile;
    private EditText address;
    private EditText password;
    private EditText ppassword;
    private Button signUp;
    private TextView already;
    String url = "http://192.168.1.109:8080/";

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        already=findViewById(R.id.signtext);
         already.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 startActivity(new Intent(signup.this,login.class));
                 finish();
             }
         });

        //......................................................
         firstname = findViewById(R.id.firstname);
         middlename = findViewById(R.id.middlename);
         lastname = findViewById(R.id.lastname);
         email = findViewById(R.id.email);
         mobile = findViewById(R.id.mobile);
         address = findViewById(R.id.address);
         password = findViewById(R.id.password2);
         ppassword = findViewById(R.id.password3);
          signUp = findViewById(R.id.signupbtn);
         signUp.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 sendData();
                 Log.d("data","Data sent");
                 firstname.setText("");
                 middlename.setText("");
                 lastname.setText("");
                 email.setText("");
                 mobile.setText("");
                 address.setText("");
                 password.setText("");
                 ppassword.setText("");
                 startActivity(new Intent(signup.this, login.class));
                 finish();
             }
         });
    }
    public void sendData() {
        if (firstname.getText().toString().isEmpty() ||
                lastname.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() ||
                address.getText().toString().isEmpty() ||
                mobile.getText().toString().isEmpty() ||
                password.getText().toString().isEmpty() ||
                ppassword.getText().toString().isEmpty()) {

            Toast.makeText(signup.this, "Error in signup, Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (!password.getText().toString().equals(ppassword.getText().toString())) {
            Toast.makeText(signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JSONObject jsonRequest = new JSONObject();
            try {
                jsonRequest.put("firstName", firstname.getText().toString());
                jsonRequest.put("middleName", middlename.getText().toString());
                jsonRequest.put("lastName", lastname.getText().toString());
                jsonRequest.put("email", email.getText().toString());
                jsonRequest.put("address", address.getText().toString());
                jsonRequest.put("contact", mobile.getText().toString());
                jsonRequest.put("password", ppassword.getText().toString());
            } catch (JSONException e) {
                Toast.makeText(signup.this, e.toString(), Toast.LENGTH_SHORT).show();
                email.setText(e.toString());
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + "profileApi/create", jsonRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        if (message.equals("Registration successful")) {
                            Toast.makeText(signup.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            // Redirect to login activity

                        } else {
                            Toast.makeText(signup.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("VolleyError", error.toString());
                }
            });

            requestQueue.add(jsonObjectRequest);
        }
    }

}