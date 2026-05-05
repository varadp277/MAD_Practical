package com.example.turfbooking;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyBookingsActivity extends AppCompatActivity {

    private int userId;
    private RecyclerView rvMyBookings;
    private BookingAdapter bookingAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bookings);

        userId = getIntent().getIntExtra("USER_ID", -1);
        databaseHelper = new DatabaseHelper(this);

        rvMyBookings = findViewById(R.id.rvMyBookings);
        rvMyBookings.setLayoutManager(new LinearLayoutManager(this));

        List<Booking> myBookings = databaseHelper.getBookingsForUser(userId);
        bookingAdapter = new BookingAdapter(this, myBookings);
        rvMyBookings.setAdapter(bookingAdapter);
    }
}
