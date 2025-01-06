package com.example.grocerymanagementanddeliverysystem;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class Table extends AppCompatActivity {

    private TableLayout tableLayout;
    private RequestQueue requestQueue;
    private EditText search;
    private String url = "http://192.168.1.109:8080/categoryApi/find";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        tableLayout = findViewById(R.id.tablecat);
        search= findViewById(R.id.search_cat);
        requestQueue = Volley.newRequestQueue(this);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // This method is called when the user presses the "Search" button on the keyboard
                    searchCategories();
                    return true;
                }
                return false;
            }
        });
        readCategories();
    }

//    private void readCategories() {
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                Request.Method.GET,
//                url,
//                null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            tableLayout.removeAllViews();
//
//                            TableRow tableRow = new TableRow(Table.this);
//                            tableRow.setBackgroundColor(getResources().getColor(R.color.green));
//                            TextView textViewId = new TextView(Table.this);
//                            textViewId.setText("ID");
//                            textViewId.setTextColor(getResources().getColor(R.color.white));
//                            textViewId.setPadding(10, 10, 10, 10);
//                            tableRow.addView(textViewId);
//                            TextView textViewName = new TextView(Table.this);
//                            textViewName.setText("Name");
//                            textViewName.setTextColor(getResources().getColor(R.color.white));
//                            textViewName.setPadding(10, 10, 10, 10);
//                            tableRow.addView(textViewName);
//                            tableLayout.addView(tableRow);
//
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject categoryObject = response.getJSONObject(i);
//                                Long categoryId = categoryObject.getLong("id");
//                                String categoryName = categoryObject.getString("categoryName");
//
//                                tableRow = new TableRow(Table.this);
//                                textViewId = new TextView(Table.this);
//                                textViewId.setText(String.valueOf(categoryId));
//                                textViewId.setPadding(10, 10, 10, 10);
//                                tableRow.addView(textViewId);
//                                textViewName = new TextView(Table.this);
//                                textViewName.setText(categoryName);
//                                textViewName.setPadding(10, 10, 10, 10);
//                                tableRow.addView(textViewName);
//                                tableLayout.addView(tableRow);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(Table.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Toast.makeText(Table.this, "Error fetching categories", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//
//        requestQueue.add(jsonArrayRequest);
//    }
private void readCategories() {
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        tableLayout.removeAllViews();

                        TableRow tableRow = new TableRow(Table.this);
                        tableRow.setBackgroundColor(getResources().getColor(R.color.green));
                        TextView textViewId = new TextView(Table.this);
                        textViewId.setText("ID");
                        textViewId.setTextColor(getResources().getColor(R.color.white));
                        textViewId.setPadding(10, 10, 10, 10);
                        tableRow.addView(textViewId);
                        TextView textViewName = new TextView(Table.this);
                        textViewName.setText("Name");
                        textViewName.setTextColor(getResources().getColor(R.color.white));
                        textViewName.setPadding(10, 10, 10, 10);
                        tableRow.addView(textViewName);
                        tableLayout.addView(tableRow);

                        for (int i = 0; i < response.length(); i++) {
                            JSONObject categoryObject = response.getJSONObject(i);
                            Long categoryId = categoryObject.getLong("id");
                            String categoryName = categoryObject.getString("categoryName");

                            TableRow row = new TableRow(Table.this);
                            TextView idTextView = new TextView(Table.this);
                            idTextView.setText(String.valueOf(categoryId));
                            idTextView.setPadding(10, 10, 10, 10);
                            row.addView(idTextView);
                            TextView nameTextView = new TextView(Table.this);
                            nameTextView.setText(categoryName);
                            nameTextView.setPadding(10, 10, 10, 10);
                            row.addView(nameTextView);

                            // Create a ViewHolder to hold references to the TextViews
                            ViewHolder viewHolder = new ViewHolder(idTextView, nameTextView);

                            // Add the ViewHolder to the TableRow's tag
                            row.setTag(viewHolder);

                            // Add click listener to table row
                            row.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Get the ViewHolder from the TableRow's tag
                                    ViewHolder viewHolder = (ViewHolder) v.getTag();

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
                        Toast.makeText(Table.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(Table.this, "Error fetching categories", Toast.LENGTH_SHORT).show();
                }
            }
    );

    // Add the request to the RequestQueue
    requestQueue.add(jsonArrayRequest);
}
    public static class ViewHolder {
        public TextView idTextView;
        public TextView nameTextView;

        public ViewHolder(TextView idTextView, TextView nameTextView) {
            this.idTextView = idTextView;
            this.nameTextView = nameTextView;
        }
    }
    private void searchCategories() {
        String categoryName = search.getText().toString().trim();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url + "/find",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                tableLayout.removeAllViews();

                                TableRow tableRow = new TableRow(Table.this);
                                tableRow.setBackgroundColor(getResources().getColor(R.color.green));
                                TextView textViewId = new TextView(Table.this);
                                textViewId.setText("ID");
                                textViewId.setTextColor(getResources().getColor(R.color.white));
                                textViewId.setPadding(10, 10, 10, 10);
                                tableRow.addView(textViewId);
                                TextView textViewName = new TextView(Table.this);
                                textViewName.setText("Name");
                                textViewName.setTextColor(getResources().getColor(R.color.white));
                                textViewName.setPadding(10, 10, 10, 10);
                                tableRow.addView(textViewName);
                                tableLayout.addView(tableRow);

                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject categoryObject = response.getJSONObject(i);
                                    Long categoryId = categoryObject.getLong("id");
                                    String categoryNameFromDB = categoryObject.getString("categoryName");

                                    if (categoryNameFromDB.toLowerCase().contains(categoryName.toLowerCase())) {
                                        TableRow row = new TableRow(Table.this);
                                        TextView idTextView = new TextView(Table.this);
                                        idTextView.setText(String.valueOf(categoryId));
                                        idTextView.setPadding(10, 10, 10, 10);
                                        row.addView(idTextView);
                                        TextView nameTextView = new TextView(Table.this);
                                        nameTextView.setText(categoryNameFromDB);
                                        nameTextView.setPadding(10, 10, 10, 10);
                                        row.addView(nameTextView);

                                        tableLayout.addView(row);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Table.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(Table.this, "Error searching categories", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            requestQueue.add(jsonArrayRequest);
        }

    }
