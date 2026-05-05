package com.example.turfbooking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TurfDetailActivity extends AppCompatActivity {

    private int turfId, userId;
    private DatabaseHelper databaseHelper;
    private Turf turf;

    private TextView tvName, tvLocation, tvPrice;
    private Button btnBookNow, btnSubmitReview;
    private ImageView ivTurf;
    private RecyclerView rvReviews;
    private RatingBar rbNewReview;
    private EditText etReviewText;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turf_detail);

        Toolbar toolbar = findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Turf Details");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        turfId = getIntent().getIntExtra("TURF_ID", -1);
        userId = getIntent().getIntExtra("USER_ID", -1);

        databaseHelper = new DatabaseHelper(this);
        turf = databaseHelper.getTurf(turfId);

        tvName = findViewById(R.id.tvDetailName);
        tvLocation = findViewById(R.id.tvDetailLocation);
        tvPrice = findViewById(R.id.tvDetailPrice);
        btnBookNow = findViewById(R.id.btnBookNow);
        ivTurf = findViewById(R.id.ivDetailTurf);

        if (turf != null) {
            tvName.setText(turf.getName());
            tvLocation.setText(turf.getLocation());
            tvPrice.setText("$" + turf.getPricePerHour() + " / hr");
            
            int resId = getResources().getIdentifier(turf.getImageName(), "drawable", getPackageName());
            if (resId != 0) {
                ivTurf.setImageResource(resId);
            }
        }

        btnBookNow.setOnClickListener(v -> {
            Intent intent = new Intent(TurfDetailActivity.this, BookingActivity.class);
            intent.putExtra("TURF_ID", turfId);
            intent.putExtra("USER_ID", userId);
            startActivity(intent);
        });

        // Reviews Setup
        rvReviews = findViewById(R.id.rvReviews);
        rvReviews.setLayoutManager(new LinearLayoutManager(this));
        
        loadReviews();

        // Submit Review Setup
        rbNewReview = findViewById(R.id.rbNewReview);
        etReviewText = findViewById(R.id.etReviewText);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);

        btnSubmitReview.setOnClickListener(v -> {
            float rating = rbNewReview.getRating();
            String text = etReviewText.getText().toString().trim();

            if (rating == 0) {
                Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userId == -1) {
                Toast.makeText(this, "Please login to review", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean success = databaseHelper.addReview(userId, turfId, rating, text);
            if (success) {
                Toast.makeText(this, "Review added!", Toast.LENGTH_SHORT).show();
                etReviewText.setText("");
                rbNewReview.setRating(0);
                loadReviews();
            } else {
                Toast.makeText(this, "Failed to add review", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadReviews() {
        List<Review> reviews = databaseHelper.getReviewsForTurf(turfId);
        if (reviewAdapter == null) {
            reviewAdapter = new ReviewAdapter(this, reviews);
            rvReviews.setAdapter(reviewAdapter);
        } else {
            reviewAdapter.updateReviews(reviews);
        }
    }
}
