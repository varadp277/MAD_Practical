package com.example.turfbooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageBookingsActivity extends AppCompatActivity {

    private RecyclerView rvManageBookings;
    private BookingAdapter bookingAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bookings);

        databaseHelper = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbarManageBookings);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Manage Bookings");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        rvManageBookings = findViewById(R.id.rvManageBookings);
        rvManageBookings.setLayoutManager(new LinearLayoutManager(this));

        List<Booking> allBookings = databaseHelper.getAllBookings();
        bookingAdapter = new BookingAdapter(this, allBookings);
        rvManageBookings.setAdapter(bookingAdapter);
    }
}
