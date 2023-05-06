package com.example.ubi_bike;

public class Product {

    private String amountAvailable;
    private String name;
    private String value;

    public Product() {
        // Required empty constructor for Firestore
    }

    public Product(String amountAvailable, String value, String name) {
        this.amountAvailable = amountAvailable;
        this.value = value;
        this.name = name;
    }

    public String getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(String amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
