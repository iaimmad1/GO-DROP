package com.example.grocerymanagementanddeliverysystem;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
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

public class item_cart extends AppCompatActivity {

        private TextView name, price,quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_cart);
     name = findViewById(R.id.nameofproducts);
     quantity = findViewById(R.id.quantityofproducts);
     price = findViewById(R.id.priceofproducts);
//     fetch();

    }
    private void fetch() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "http://192.168.1.109:8080/cartApi/find";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("kk"," "+response.toString());
                        for( int i = 0; i< response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Toast.makeText(item_cart.this, " "+ jsonObject.getString("item"), Toast.LENGTH_SHORT).show();
                                String item = jsonObject.getString("item");
                                String quantity1 = jsonObject.getString("quantity");
                                String price1 = jsonObject.getString("total_price");
                                name.setText(item);
                                quantity.setText(quantity1);
                                price.setText(price1);


                            } catch (JSONException e) {
                                Log.d("exceptioncart",e.toString());
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ProductDetail", "Volley error: " + error.toString());
            }
        });
        queue.add(request);
    }
}