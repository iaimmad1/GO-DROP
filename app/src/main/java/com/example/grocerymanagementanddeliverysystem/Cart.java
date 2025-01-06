package com.example.grocerymanagementanddeliverysystem;

public class Cart {
    private Long id;
    private String item;
    private Long quantity;
    private float total_price;

    public Cart() {
        // Default constructor
    }

    public Cart(Long id, String item, Long quantity, float total_price) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }
}
