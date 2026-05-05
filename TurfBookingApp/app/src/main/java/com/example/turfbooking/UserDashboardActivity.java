package com.example.turfbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class UserDashboardActivity extends AppCompatActivity {

    private RecyclerView rvTurfs, rvCategories, rvTopRated;
    private TurfAdapter turfAdapter;
    private CategoryAdapter categoryAdapter;
    private DatabaseHelper databaseHelper;
    private int userId;
    private TextView tvGreeting;
    private LinearLayout btnNavBookings, btnNavProfile;
    private List<Turf> allTurfs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        
        userId = getIntent().getIntExtra("USER_ID", -1);
        
        databaseHelper = new DatabaseHelper(this);
        
        tvGreeting = findViewById(R.id.tvGreeting);
        User user = databaseHelper.getUser(userId);
        if (user != null) {
            tvGreeting.setText("Hello, " + user.getUsername());
        }

        // --- Categories Setup ---
        rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("Football", R.drawable.ic_football, R.color.primary_dark));
        categoryList.add(new Category("Cricket", R.drawable.ic_cricket, R.color.accent));
        categoryList.add(new Category("Tennis", R.drawable.ic_tennis, R.color.accent));
        categoryList.add(new Category("All", R.drawable.ic_more, R.color.accent));
        
        categoryAdapter = new CategoryAdapter(this, categoryList, category -> {
            filterTurfsByCategory(category.getName());
        });
        rvCategories.setAdapter(categoryAdapter);

        // --- Turfs Setup ---
        rvTurfs = findViewById(R.id.rvTurfs);
        rvTurfs.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        allTurfs = databaseHelper.getAllTurfs();
        turfAdapter = new TurfAdapter(this, new ArrayList<>(allTurfs), userId);
        rvTurfs.setAdapter(turfAdapter);

        // --- Top Rated Setup ---
        rvTopRated = findViewById(R.id.rvTopRated);
        rvTopRated.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        
        List<Turf> topRatedTurfs = new ArrayList<>();
        for (Turf t : allTurfs) {
            float rating = databaseHelper.getAverageRatingForTurf(t.getId());
            t.setRating(rating);
            topRatedTurfs.add(t);
        }
        
        // Sort descending by rating
        java.util.Collections.sort(topRatedTurfs, (t1, t2) -> Float.compare(t2.getRating(), t1.getRating()));
        
        // Take top 5
        if (topRatedTurfs.size() > 5) {
            topRatedTurfs = topRatedTurfs.subList(0, 5);
        }
        
        TurfAdapter topRatedAdapter = new TurfAdapter(this, topRatedTurfs, userId);
        rvTopRated.setAdapter(topRatedAdapter);

        // --- Dashboard Buttons Setup ---
        TextView btnFilters = findViewById(R.id.btnFilters);
        TextView btnBannerRegister = findViewById(R.id.btnBannerRegister);
        TextView btnSeeAllTurfs = findViewById(R.id.btnSeeAllTurfs);
        TextView btnSeeAllTopRated = findViewById(R.id.btnSeeAllTopRated);

        btnFilters.setOnClickListener(v -> Toast.makeText(this, "Filters coming soon!", Toast.LENGTH_SHORT).show());
        btnBannerRegister.setOnClickListener(v -> Toast.makeText(this, "Tournament registration opening next week!", Toast.LENGTH_SHORT).show());
        btnSeeAllTurfs.setOnClickListener(v -> {
            Intent intent = new Intent(this, AllTurfsActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });
        btnSeeAllTopRated.setOnClickListener(v -> Toast.makeText(this, "Loading top rated turfs...", Toast.LENGTH_SHORT).show());

        ImageView ivProfile = findViewById(R.id.ivProfile);
        ivProfile.setOnClickListener(v -> showProfileMenu(v));

        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTurfsBySearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // --- Bottom Navigation Actions ---
        LinearLayout btnNavHome = findViewById(R.id.btnNavHome);
        btnNavBookings = findViewById(R.id.btnNavBookings);
        LinearLayout btnNavTopRated = findViewById(R.id.btnNavTopRated);
        btnNavProfile = findViewById(R.id.btnNavProfile);

        btnNavHome.setOnClickListener(v -> {
            Toast.makeText(this, "You are already on the Home screen", Toast.LENGTH_SHORT).show();
        });

        btnNavBookings.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyBookingsActivity.class);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        btnNavTopRated.setOnClickListener(v -> {
            // Scroll to Top Rated Turfs section
            rvTopRated.getParent().requestChildFocus(rvTopRated, rvTopRated);
        });

        btnNavProfile.setOnClickListener(v -> showProfileMenu(v));
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

    private void filterTurfsByCategory(String categoryName) {
        if ("All".equalsIgnoreCase(categoryName) || "More".equalsIgnoreCase(categoryName)) {
            turfAdapter.updateTurfs(new ArrayList<>(allTurfs));
            return;
        }
        
        List<Turf> filteredTurfs = new ArrayList<>();
        for (Turf turf : allTurfs) {
            // Null check handling in case the category was never assigned in database
            if (turf.getCategory() != null && turf.getCategory().equalsIgnoreCase(categoryName)) {
                filteredTurfs.add(turf);
            } else if (turf.getCategory() == null) {
                // If it's a legacy turf without a category, you can optionally include it 
                // in default, but for now we just skip it or log it.
            }
        }
        
        turfAdapter.updateTurfs(filteredTurfs);
        
        if (filteredTurfs.isEmpty()) {
            Toast.makeText(this, "No " + categoryName + " turfs available", Toast.LENGTH_SHORT).show();
        }
    }

    private void filterTurfsBySearch(String query) {
        List<Turf> filteredTurfs = new ArrayList<>();
        for (Turf turf : allTurfs) {
            if (turf.getName().toLowerCase().contains(query.toLowerCase()) || 
                turf.getLocation().toLowerCase().contains(query.toLowerCase())) {
                filteredTurfs.add(turf);
            }
        }
        turfAdapter.updateTurfs(filteredTurfs);
    }
}
