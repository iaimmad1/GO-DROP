package com.example.grocerymanagementanddeliverysystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_item_cart, parent, false);
        }

        // Get the cart item for the current position
        Cart cart = cartList.get(position);

        // Find views in the item_cart layout
        TextView nameOfProduct = convertView.findViewById(R.id.nameofproducts);
        TextView quantityOfProduct = convertView.findViewById(R.id.quantityofproducts);
        TextView priceOfProduct = convertView.findViewById(R.id.priceofproducts);
        ImageView productImage = convertView.findViewById(R.id.imageView); // Assuming you want to use this

        // Set data to views
        nameOfProduct.setText(cart.getItem());  // Set item name
        quantityOfProduct.setText("Qty: " + cart.getQuantity());  // Set quantity
        priceOfProduct.setText("Rs " + cart.getTotal_price());  // Set total price



        return convertView;
    }
}
