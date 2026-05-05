package com.example.turfbooking;

public class Booking {
    private int id;
    private int userId;
    private int turfId;
    private String date;
    private String timeSlot;
    private String status;

    public Booking() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getTurfId() { return turfId; }
    public void setTurfId(int turfId) { this.turfId = turfId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
