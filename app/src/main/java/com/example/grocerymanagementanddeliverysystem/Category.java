package com.example.grocerymanagementanddeliverysystem;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Category extends AppCompatActivity {

    private EditText etInsertCat;
    private EditText search;
    private TextView idread;
    private TableLayout tableLayout;
    private Button btnAdd, btnUpdate, btnDelete;
    private RequestQueue requestQueue;
    private String url = "http://192.168.1.109:8080/categoryApi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        etInsertCat = findViewById(R.id.et_insert_cat);
        idread = findViewById(R.id.id_cat);
        search =findViewById(R.id.search_cat);
        btnAdd = findViewById(R.id.cat_add);
        btnUpdate = findViewById(R.id.cat_update);
        btnDelete = findViewById(R.id.cat_delete);
        tableLayout = findViewById(R.id.tablecat);
        requestQueue = Volley.newRequestQueue(this);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCategory();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCategory();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCategory();
            }
        });

        readCategories();
        getCategoryIdsFromDatabase();
    }

    private static class ViewHolder {
        public TextView idTextView;
        public TextView nameTextView;

        public ViewHolder(TextView idTextView, TextView nameTextView) {
            this.idTextView = idTextView;
            this.nameTextView = nameTextView;
        }
    }
    private void readCategories() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url+"/find",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            tableLayout.removeAllViews();

                            TableRow tableRow = new TableRow(Category.this);
                            tableRow.setBackgroundColor(getResources().getColor(R.color.green));
                            TextView textViewId = new TextView(Category.this);
                            textViewId.setText("ID");
                            textViewId.setTextColor(getResources().getColor(R.color.white));
                            textViewId.setPadding(10, 10, 10, 10);
                            tableRow.addView(textViewId);
                            TextView textViewName = new TextView(Category.this);
                            textViewName.setText("Name");
                            textViewName.setTextColor(getResources().getColor(R.color.white));
                            textViewName.setPadding(10, 10, 10, 10);
                            tableRow.addView(textViewName);
                            tableLayout.addView(tableRow);

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObject = response.getJSONObject(i);
                                Long categoryId = categoryObject.getLong("id");
                                String categoryName = categoryObject.getString("categoryName");

                                TableRow row = new TableRow(Category.this);
                                TextView idTextView = new TextView(Category.this);
                                idTextView.setText(String.valueOf(categoryId));
                                idTextView.setPadding(10, 10, 10, 10);
                                row.addView(idTextView);
                                TextView nameTextView = new TextView(Category.this);
                                nameTextView.setText(categoryName);
                                nameTextView.setPadding(10, 10, 10, 10);
                                row.addView(nameTextView);

                                // Create a ViewHolder to hold references to the TextViews
                                Table.ViewHolder viewHolder = new Table.ViewHolder(idTextView, nameTextView);

                                // Add the ViewHolder to the TableRow's tag
                                row.setTag(viewHolder);

                                // Add click listener to table row
                                row.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Get the ViewHolder from the TableRow's tag
                                        Table.ViewHolder viewHolder = (Table.ViewHolder) v.getTag();

                                        // Get the TextViews from the ViewHolder
                                        TextView idTextView = viewHolder.idTextView;
                                        TextView nameTextView = viewHolder.nameTextView;

                                        // Display the data in a text view and edit text
                                        TextView displayTextView = findViewById(R.id.id_cat);
                                        EditText displayEditText = findViewById(R.id.et_insert_cat);

                                        displayTextView.setText( idTextView.getText());
                                        displayEditText.setText(nameTextView.getText());
                                    }
                                });

                                tableLayout.addView(row);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Category.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(Category.this, "Error fetching categories", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    private void createCategory() {
        String categoryName = etInsertCat.getText().toString().trim();

        if (categoryName.isEmpty()) {
            Toast.makeText(Category.this, "Error in inserting Category, please enter Category Name", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("categoryName", categoryName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url + "/create",
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(Category.this, "Category created successfully", Toast.LENGTH_SHORT).show();
                            etInsertCat.setText("");
                            readCategories();
                            getCategoryIdsFromDatabase();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(Category.this, "Error creating category", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);
        }
    }
    private void updateCategory() {
        String categoryName = etInsertCat.getText().toString();
        Long id = Long.parseLong(idread.getText().toString()); // Get the category ID from the displayTextView

        if (categoryName.isEmpty()) {
            Toast.makeText(Category.this, "Error in Updating Category, please enter Category Name", Toast.LENGTH_SHORT).show();
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", id);
                jsonObject.put("categoryName", categoryName);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.PUT,
                    url + "/update/" + id.toString(), 
                    jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(Category.this, "Category updated successfully", Toast.LENGTH_SHORT).show();
                            etInsertCat.setText("");
                            readCategories();
                            getCategoryIdsFromDatabase();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(Category.this, "Error updating category", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(jsonObjectRequest);
        }
    }
    private void deleteCategory() {
    String categoryName = etInsertCat.getText().toString();
    Long id = Long.parseLong(idread.getText().toString());

    if (categoryName.isEmpty()) {
        Toast.makeText(Category.this, "Error in Deleting Category, please enter Category Name", Toast.LENGTH_SHORT).show();
    } else {
               JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                url + "/delete/" + id, // Correctly format the URL
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(Category.this, "Category deleted successfully", Toast.LENGTH_SHORT).show();
                        etInsertCat.setText("");
                        readCategories();
                        getCategoryIdsFromDatabase();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

//                        Toast.makeText(Category.this, "Error deleting category", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
    private void displayCategoryId() {
        // Assuming you have a list of category IDs
        List<Long> categoryIds = getCategoryIdsFromDatabase();

        if (categoryIds!= null &&!categoryIds.isEmpty()) {
            Long categoryId = categoryIds.get(0); // Get the first ID for example
            idread.setText(String.valueOf(categoryId));
        } else {
            Toast.makeText(Category.this, "No categories found", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Long> getCategoryIdsFromDatabase() {

        List<Long> categoryIds = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url + "/find",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObject = response.getJSONObject(i);
                                Long Id = categoryObject.getLong("id");
                                idread.setText(String.valueOf(Id+1));
                                categoryIds.add(Id);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);

        return categoryIds;
    }
    private void fetchDataFromTableLayout() {
        TableLayout tableLayout = findViewById(R.id.tablecat);
        int rowCount = tableLayout.getChildCount();

        for (int i = 1; i < rowCount; i++) { // skip the header row
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            TextView textViewId = (TextView) tableRow.getChildAt(0);
            TextView textViewName = (TextView) tableRow.getChildAt(1);

            Long categoryId = Long.parseLong(textViewId.getText().toString());
            String categoryName = textViewName.getText().toString();

            // Set the values to the EditText and TextView
            idread.setText(String.valueOf(categoryId));
            etInsertCat.setText(categoryName);
        }
    }
    }


