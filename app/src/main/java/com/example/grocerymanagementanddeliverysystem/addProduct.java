package com.example.grocerymanagementanddeliverysystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
public class addProduct extends AppCompatActivity {
    private EditText search;
    private EditText name;
    private EditText quantity;
    private EditText price;
    private EditText description;
    private ImageView image;
    private Button addimage;
    private Button save;
    private Button update;
    private Button delete;
    private Spinner scrapspinner;
    private TextView scrap;
    private TableLayout tableLayout;
    private RequestQueue requestQueue;
    private List<String> categoryNames = new ArrayList<>();
    private List<Integer> categoryIds = new ArrayList<>();
    private String url = "http://192.168.1.109:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        search = findViewById(R.id.search);
        name = findViewById(R.id.product);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.et_price);
        description = findViewById(R.id.description);
        image = findViewById(R.id.image);
        addimage = findViewById(R.id.select);
        save = findViewById(R.id.create);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        scrapspinner = findViewById(R.id.res);
        scrap = findViewById(R.id.id_pro);
        tableLayout = findViewById(R.id.protab); // Ensure this matches your layout
        requestQueue = Volley.newRequestQueue(this);

        getId();
        fetchProducts();
        fetchCategories();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
                Toast.makeText(addProduct.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                clearFields();
                getId();
                fetchProducts();
                fetchCategories();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });
    }

    private void fetchProducts() {
        tableLayout = findViewById(R.id.protab); // Ensure this matches your layout
        String url = "http://192.168.1.109:8080/productApi/find";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            tableLayout.removeAllViews(); // Clear the table layout

                            // Add header row
                            TableRow headerRow = new TableRow(addProduct.this);
                            headerRow.setBackgroundColor(getResources().getColor(R.color.green));
                            TextView textViewId = new TextView(addProduct.this);
                            textViewId.setText("ID");
                            textViewId.setTextColor(getResources().getColor(R.color.white));
                            textViewId.setPadding(10, 10, 10, 10);
                            headerRow.addView(textViewId);
                            TextView textViewName = new TextView(addProduct.this);
                            textViewName.setText("Name");
                            textViewName.setTextColor(getResources().getColor(R.color.white));
                            textViewName.setPadding(10, 10, 10, 10);
                            headerRow.addView(textViewName);
                            TextView descriptionTextView = new TextView(addProduct.this);
                            descriptionTextView.setText("Description");
                            descriptionTextView.setTextColor(getResources().getColor(R.color.white));
                            descriptionTextView.setPadding(10, 10, 10, 10);
                            headerRow.addView(descriptionTextView);
                            TextView textViewQuantity = new TextView(addProduct.this);
                            textViewQuantity.setText("Quantity");
                            textViewQuantity.setTextColor(getResources().getColor(R.color.white));
                            textViewQuantity.setPadding(10, 10, 10, 10);
                            headerRow.addView(textViewQuantity);
                            TextView textViewPrice = new TextView(addProduct.this);
                            textViewPrice.setText("Price");
                            textViewPrice.setTextColor(getResources().getColor(R.color.white));
                            textViewPrice.setPadding(10, 10, 10, 10);
                            headerRow.addView(textViewPrice);

                            tableLayout.addView(headerRow);

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject productObject = response.getJSONObject(i);
                                Long id = productObject.getLong("id");
                                String productName = productObject.getString("name");
                                String productDescription = productObject.getString("description");
                                long productQuantity = productObject.getLong("quantity");
                                String priceString = productObject.getString("price");
                                float productPrice = Float.parseFloat(priceString);

                                TableRow row = new TableRow(addProduct.this);
                                row.setLayoutParams(new TableRow.LayoutParams(
                                        TableRow.LayoutParams.MATCH_PARENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));

                                TextView idTextView = new TextView(addProduct.this);
                                idTextView.setText(String.valueOf(id));
                                idTextView.setPadding(10, 10, 10, 10);
                                row.addView(idTextView);

                                TextView nameTextView = new TextView(addProduct.this);
                                nameTextView.setText(productName);
                                nameTextView.setPadding(10, 10, 10, 10);
                                row.addView(nameTextView);

                                TextView descTextView = new TextView(addProduct.this);
                                descTextView.setText(productDescription);
                                descTextView.setPadding(10, 10, 10, 10);
                                row.addView(descTextView);

                                TextView quantityTextView = new TextView(addProduct.this);
                                quantityTextView.setText(String.valueOf(productQuantity));
                                quantityTextView.setPadding(10, 10, 10, 10);
                                row.addView(quantityTextView);

                                TextView priceTextView = new TextView(addProduct.this);
                                priceTextView.setText(String.valueOf(productPrice));
                                priceTextView.setPadding(10, 10, 10, 10);
                                row.addView(priceTextView);

                                tableLayout.addView(row);

                                row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        bring(id, productName, productDescription, productQuantity, productPrice);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(addProduct.this, "Error parsing JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(addProduct.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void bring(Long id, String productName, String productDescription, long productQuantity, float productPrice) {
        name.setText(productName);
        description.setText(productDescription);
        quantity.setText(String.valueOf(productQuantity));
        price.setText(String.valueOf(productPrice));
        scrap.setText(String.valueOf(id)); // Assuming scrap is the TextView to display product ID
    }

    private void createProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String categoryName = scrapspinner.getSelectedItem().toString();
        int categoryId = categoryIds.get(scrapspinner.getSelectedItemPosition());

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("name", name.getText().toString());
            jsonObject.put("quantity", Integer.parseInt(quantity.getText().toString()));
            jsonObject.put("price", Double.parseDouble(price.getText().toString()));
            jsonObject.put("description", description.getText().toString());
            jsonObject.put("categoryName", categoryName); // Add the category_name to the JSONObject
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                "http://192.168.1.109:8080/productApi/create",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(addProduct.this, "Product created successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                        getId();
                        fetchProducts();
                        fetchCategories();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Error", "Error creating product: " + error.getMessage());
                        Toast.makeText(addProduct.this, "Error creating product", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
    private void updateProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Long productId = Long.parseLong(scrap.getText().toString());

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("id", productId);
            jsonObject.put("name", name.getText().toString());
            jsonObject.put("quantity", Integer.parseInt(quantity.getText().toString()));
            jsonObject.put("price", Double.parseDouble(price.getText().toString()));
            jsonObject.put("description", description.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                "http://192.168.1.109:8080/productApi/read/"+ productId,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(addProduct.this, "Product updated successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                        fetchProducts();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        String errorMessage = "Error updating product: ";
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            errorMessage += "Status Code: " + statusCode + "\n";
                            try {
                                String responseBody = new String(error.networkResponse.data, "utf-8");
                                JSONObject data = new JSONObject(responseBody);
                                errorMessage += "Message: " + data.getString("message");
                            } catch (JSONException | UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("Error", errorMessage);
                        Toast.makeText(addProduct.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
    private void deleteProduct() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Long productId = Long.parseLong(scrap.getText().toString());

        String deleteUrl = "http://192.168.1.109:8080/productApi/delete/" + productId;

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                deleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(addProduct.this, "Product deleted successfully", Toast.LENGTH_SHORT).show();
                        clearFields();
                        fetchProducts();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Error", "Error deleting product: " + error.getMessage());
                        Toast.makeText(addProduct.this, "Error deleting product", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }

    private void fetchCategories() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url + "categoryApi/find",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            categoryNames.clear();
                            categoryIds.clear();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObject = response.getJSONObject(i);
                                String categoryName = categoryObject.getString("categoryName");
                                int categoryId = categoryObject.getInt("id");
                                categoryNames.add(categoryName);
                                categoryIds.add(categoryId);
                            }
                            populateSpinner();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(addProduct.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(addProduct.this, "Error fetching data", Toast.LENGTH_SHORT).show();
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


    private void getId() {
        String url = "http://192.168.1.109:8080/productApi/find";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Long maxId = 0L; // Initialize maxId to zero
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObject = response.getJSONObject(i);
                                Long Id = categoryObject.getLong("id");
                                if (Id > maxId) {
                                    maxId = Id; // Update maxId if current Id is greater
                                }
                            }
                            scrap.setText(String.valueOf(maxId + 1)); // Set scrap to maxId + 1
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("Error", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.e("Error", "Error fetching data: " + error.getMessage());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void clearFields() {
        name.setText("");
        quantity.setText("");
        price.setText("");
        description.setText("");
        scrap.setText("");
    }
}
