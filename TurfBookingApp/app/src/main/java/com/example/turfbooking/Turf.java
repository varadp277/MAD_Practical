package com.example.turfbooking;

public class Turf {
    private int id;
    private String name;
    private String location;
    private double pricePerHour;
    private String imageName;
    private String category;
    private float rating; // Average rating

    public Turf() {}

    public Turf(String name, String location, double pricePerHour, String imageName, String category) {
        this.name = name;
        this.location = location;
        this.pricePerHour = pricePerHour;
        this.imageName = imageName;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }

    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }
}
