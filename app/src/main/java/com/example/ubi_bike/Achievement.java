package com.example.ubi_bike;

public class Achievement {
    private String name, description, points;

    public Achievement() {
        // Required empty constructor for Firestore
    }

    public Achievement(String name, String description, String points) {
        this.name = name;
        this.description = description;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points){
        this.points = points;
    }
}
