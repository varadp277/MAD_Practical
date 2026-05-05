package com.example.turfbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.widget.LinearLayout;
import android.view.View;

public class AdminDashboardActivity extends AppCompatActivity {

    private CardView cardAddTurf, cardManageBookings;
    private ImageView btnAdminLogout;
    private TextView tvTotalUsers, tvTotalTurfs, tvTotalBookings, tvTotalRevenue;
    private DatabaseHelper databaseHelper;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardAddTurf = findViewById(R.id.cardAddTurf);
        cardManageBookings = findViewById(R.id.cardManageBookings);
        btnAdminLogout = findViewById(R.id.btnAdminLogout);

        userId = getIntent().getIntExtra("USER_ID", -1);

        tvTotalUsers = findViewById(R.id.tvTotalUsers);
        tvTotalTurfs = findViewById(R.id.tvTotalTurfs);
        tvTotalBookings = findViewById(R.id.tvTotalBookings);
        tvTotalRevenue = findViewById(R.id.tvTotalRevenue);

        databaseHelper = new DatabaseHelper(this);
        loadAnalytics();

        cardAddTurf.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, AddTurfActivity.class));
        });

        cardManageBookings.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, ManageBookingsActivity.class));
        });

        btnAdminLogout.setOnClickListener(v -> showProfileMenu(v));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAnalytics();
    }

    private void loadAnalytics() {
        tvTotalUsers.setText(String.valueOf(databaseHelper.getTotalUsers()));
        tvTotalTurfs.setText(String.valueOf(databaseHelper.getTotalTurfs()));
        tvTotalBookings.setText(String.valueOf(databaseHelper.getTotalBookings()));
        tvTotalRevenue.setText("$" + String.format("%.2f", databaseHelper.getTotalRevenue()));
    }

    private void showProfileMenu(android.view.View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_profile_bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        LinearLayout btnViewProfile = bottomSheetView.findViewById(R.id.btnSheetViewProfile);
        LinearLayout btnLogout = bottomSheetView.findViewById(R.id.btnSheetLogout);

        btnViewProfile.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        bottomSheetDialog.show();
    }
}
