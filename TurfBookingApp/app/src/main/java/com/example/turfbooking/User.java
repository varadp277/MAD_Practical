package com.example.turfbooking;

public class User {
    private int id;
    private String username;
    private String role; // "admin" or "user"
    private String favoriteSport;
    private String skillLevel;

    public User() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public boolean isAdmin() {
        return "admin".equals(role);
    }

    public String getFavoriteSport() { return favoriteSport; }
    public void setFavoriteSport(String favoriteSport) { this.favoriteSport = favoriteSport; }

    public String getSkillLevel() { return skillLevel; }
    public void setSkillLevel(String skillLevel) { this.skillLevel = skillLevel; }
}
