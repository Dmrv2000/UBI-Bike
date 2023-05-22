package com.example.ubi_bike;

public class User {
    private String name, date, email;
    private long distance, idBike, points;

    public User() {
        // Required empty constructor for Firestore
    }

    public User(String name, String date, String email, long distance, long idBike, long points) {
        this.name = name;
        this.date = date;
        this.email = email;
        this.distance = distance;
        this.idBike = idBike;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getDistance() {
        return distance;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public long getIdBike() {
        return idBike;
    }

    public void setIdBike(long idBike) {
        this.idBike = idBike;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
