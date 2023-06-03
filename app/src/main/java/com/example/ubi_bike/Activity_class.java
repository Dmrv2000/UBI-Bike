package com.example.ubi_bike;


public class Activity_class {

    private String date;
    private String distance;
    private String time;

    public Activity_class() {
        // Required empty constructor for Firestore
    }

    public Activity_class(String date, String distance, String time) {
        this.date = date;
        this.distance = distance;
        this.time = time;
    }

    public String getDate() {
        return date;
    }


    public String getTime() {
        return time;
    }

    public String getDistance() {
        return distance;
    }
}