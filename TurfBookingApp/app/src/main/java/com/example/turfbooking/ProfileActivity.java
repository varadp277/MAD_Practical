package com.example.turfbooking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ProfileActivity extends AppCompatActivity {

    private int userId;
    private DatabaseHelper databaseHelper;
    private User user;

    private TextView tvUsername, tvRole;
    private EditText etFavoriteSport, etSkillLevel;
    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Profile");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        userId = getIntent().getIntExtra("USER_ID", -1);
        databaseHelper = new DatabaseHelper(this);
        user = databaseHelper.getUser(userId);

        tvUsername = findViewById(R.id.tvProfileUsername);
        tvRole = findViewById(R.id.tvProfileRole);
        etFavoriteSport = findViewById(R.id.etProfileFavoriteSport);
        etSkillLevel = findViewById(R.id.etProfileSkillLevel);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        if (user != null) {
            tvUsername.setText(user.getUsername());
            tvRole.setText(user.getRole().toUpperCase());
            etFavoriteSport.setText(user.getFavoriteSport() != null ? user.getFavoriteSport() : "");
            etSkillLevel.setText(user.getSkillLevel() != null ? user.getSkillLevel() : "");
        }

        btnSaveProfile.setOnClickListener(v -> {
            String sport = etFavoriteSport.getText().toString().trim();
            String skill = etSkillLevel.getText().toString().trim();

            if (userId != -1) {
                boolean success = databaseHelper.updateUserProfile(userId, sport, skill);
                if (success) {
                    Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
