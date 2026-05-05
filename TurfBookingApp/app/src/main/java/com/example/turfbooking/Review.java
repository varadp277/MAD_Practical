package com.example.turfbooking;

public class Review {
    private int id;
    private int userId;
    private int turfId;
    private float rating;
    private String reviewText;
    private String username; // To display in UI

    public Review() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTurfId() { return turfId; }
    public void setTurfId(int turfId) { this.turfId = turfId; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
