package com.example.turfbooking;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    private int turfId, userId;
    private DatabaseHelper databaseHelper;

    private CalendarView calendarView;
    private RecyclerView rvTimeSlots;
    private Button btnConfirmBooking;

    private String selectedDate;
    private String selectedSlot = null;
    private TimeSlotAdapter timeSlotAdapter;
    private List<String> timeSlotsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        turfId = getIntent().getIntExtra("TURF_ID", -1);
        userId = getIntent().getIntExtra("USER_ID", -1);
        databaseHelper = new DatabaseHelper(this);

        calendarView = findViewById(R.id.calendarView);
        rvTimeSlots = findViewById(R.id.rvTimeSlots);
        btnConfirmBooking = findViewById(R.id.btnConfirmBooking);
        
        createNotificationChannel();

        timeSlotsList = Arrays.asList("09:00 AM - 10:00 AM", "10:00 AM - 11:00 AM", "11:00 AM - 12:00 PM",
                "04:00 PM - 05:00 PM", "05:00 PM - 06:00 PM", "06:00 PM - 07:00 PM", "07:00 PM - 08:00 PM");

        rvTimeSlots.setLayoutManager(new GridLayoutManager(this, 2));

        // Setup initial date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        selectedDate = sdf.format(new Date(calendarView.getDate()));
        
        setupTimeSlotAdapter();

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
            updateAvailableSlots();
        });

        btnConfirmBooking.setOnClickListener(v -> {
            if (selectedSlot == null) {
                Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
                return;
            }

            if (databaseHelper.isSlotBooked(turfId, selectedDate, selectedSlot)) {
                Toast.makeText(this, "Slot already booked! Please select another time.", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isBooked = databaseHelper.bookTurf(userId, turfId, selectedDate, selectedSlot);
            if (isBooked) {
                Toast.makeText(this, "Booking Successful!", Toast.LENGTH_SHORT).show();
                sendBookingNotification();
                finish();
            } else {
                Toast.makeText(this, "Booking Failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupTimeSlotAdapter() {
        List<String> bookedSlots = getBookedSlotsForDate(selectedDate);
        timeSlotAdapter = new TimeSlotAdapter(this, timeSlotsList, bookedSlots, slot -> {
            selectedSlot = slot;
        });
        rvTimeSlots.setAdapter(timeSlotAdapter);
    }

    private void updateAvailableSlots() {
        selectedSlot = null;
        List<String> bookedSlots = getBookedSlotsForDate(selectedDate);
        timeSlotAdapter.updateBookedSlots(bookedSlots);
    }

    private List<String> getBookedSlotsForDate(String date) {
        List<String> booked = new ArrayList<>();
        List<Booking> allBookings = databaseHelper.getAllBookings(); // Optimally should be query by date
        for (Booking b : allBookings) {
            if (b.getTurfId() == turfId && b.getDate().equals(date) && "confirmed".equals(b.getStatus())) {
                booked.add(b.getTimeSlot());
            }
        }
        return booked;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Booking Notifications";
            String description = "Notifications for successful bookings";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("booking_channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void sendBookingNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 101);
                // Return and don't show notification if we just requested permission
                return;
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "booking_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Booking Confirmed!")
                .setContentText("Your turf is booked for " + selectedDate + " at " + selectedSlot)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
