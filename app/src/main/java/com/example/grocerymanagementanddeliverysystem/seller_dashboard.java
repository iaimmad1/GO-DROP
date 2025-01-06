package com.example.grocerymanagementanddeliverysystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class seller_dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.sel_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.seller_menu, menu);
        return super.onCreateOptionsMenu(menu);
        // return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu) {

        return true;
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.option1) {
            startActivity(new Intent(seller_dashboard.this, seller_profile.class));
        } else if (itemId == R.id.option2) {

            startActivity(new Intent(seller_dashboard.this, Category.class));

        } else if (itemId == R.id.option3) {
            startActivity(new Intent(seller_dashboard.this, addProduct.class));
        } else if (itemId == R.id.option4) {
            startActivity(new Intent(seller_dashboard.this, order_management.class));
        } else if (itemId == R.id.option5) {
            startActivity(new Intent(seller_dashboard.this, settings.class));
        } else if (itemId == R.id.option6) {
            startActivity(new Intent(seller_dashboard.this, login.class));
            finish();
        } else if (itemId == R.id.option7) {
            startActivity(new Intent(seller_dashboard.this, shop_information.class));

        } else {
            Toast.makeText(this, "Invalid option selected", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}