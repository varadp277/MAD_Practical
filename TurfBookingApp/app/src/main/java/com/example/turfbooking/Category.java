package com.example.turfbooking;

public class Category {
    private String name;
    private int iconResId;
    private int colorResId;

    public Category(String name, int iconResId, int colorResId) {
        this.name = name;
        this.iconResId = iconResId;
        this.colorResId = colorResId;
    }

    public String getName() {
        return name;
    }

    public int getIconResId() {
        return iconResId;
    }

    public int getColorResId() {
        return colorResId;
    }
}
