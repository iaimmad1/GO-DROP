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

public class forgot_password extends AppCompatActivity {

    private EditText remail, rmobile, rpass, rcpass;
    private Button resetbtn;
    private TextView back;
    private String url = "http://192.168.1.109:8080/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        remail = findViewById(R.id.remail);
        rmobile = findViewById(R.id.rmobile);
        rpass = findViewById(R.id.rpass);
        rcpass = findViewById(R.id.rcpass);
        back= findViewById(R.id.backTxt);
        resetbtn = findViewById(R.id.resetbtn);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

                remail.setText("");
                rmobile.setText("");
                rpass.setText("");
                rcpass.setText("");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgot_password.this,login.class));
                finish();
            }
        });
    }
    private void resetPassword() {
        if (remail.getText().toString().isEmpty() || rmobile.getText().toString().isEmpty() || rpass.getText().toString().isEmpty() || rcpass.getText().toString().isEmpty()) {
            Toast.makeText(forgot_password.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else if (!rpass.getText().toString().equals(rcpass.getText().toString())) {
            Toast.makeText(forgot_password.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        } else {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            JSONObject jsonRequest = new JSONObject();
            try {
                jsonRequest.put("email", remail.getText().toString());
                jsonRequest.put("contact", rmobile.getText().toString());
                jsonRequest.put("newPassword", rpass.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + "profileApi/resetPassword", jsonRequest, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        Toast.makeText(forgot_password.this, message, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(forgot_password.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("VolleyError", error.toString());
                }
            });

            requestQueue.add(jsonObjectRequest);
        }
    }
}
