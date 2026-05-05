package com.example.turfbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private List<Booking> bookingList;
    private DatabaseHelper databaseHelper;

    public BookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        Turf turf = databaseHelper.getTurf(booking.getTurfId());

        if (turf != null) {
            holder.tvBookingTurfName.setText(turf.getName());
        } else {
            holder.tvBookingTurfName.setText("Unknown Turf");
        }
        
        User user = databaseHelper.getUser(booking.getUserId());
        if (user != null) {
            holder.tvBookingUsername.setText("Booked By: " + user.getUsername());
        } else {
            holder.tvBookingUsername.setText("Booked By: Unknown");
        }

        holder.tvBookingDate.setText("Date: " + booking.getDate());
        holder.tvBookingSlot.setText("Time Slot: " + booking.getTimeSlot());
        holder.tvBookingStatus.setText("Status: " + booking.getStatus());
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookingTurfName, tvBookingUsername, tvBookingDate, tvBookingSlot, tvBookingStatus;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookingTurfName = itemView.findViewById(R.id.tvBookingTurfName);
            tvBookingUsername = itemView.findViewById(R.id.tvBookingUsername);
            tvBookingDate = itemView.findViewById(R.id.tvBookingDate);
            tvBookingSlot = itemView.findViewById(R.id.tvBookingSlot);
            tvBookingStatus = itemView.findViewById(R.id.tvBookingStatus);
        }
    }
}
