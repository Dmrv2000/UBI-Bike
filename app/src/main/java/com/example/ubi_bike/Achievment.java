package com.example.ubi_bike;

public class Achievment {

    private String name;
    private String description;

    public Achievment() {
        // Required empty constructor for Firestore
    }

    public Achievment(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }


    public String getDescription() {
        return description;
    }





}
