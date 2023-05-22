package com.example.ubi_bike;

public class Product {
    private String amountAvailable, name, price;

    public Product(String id, String birth, String email, Long distance, Long bikeid, Long points) {
        // Required empty constructor for Firestore
    }

    public Product(String amountAvailable, String price, String name) {
        this.amountAvailable = amountAvailable;
        this.price = price;
        this.name = name;
    }

    public String getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(String amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
