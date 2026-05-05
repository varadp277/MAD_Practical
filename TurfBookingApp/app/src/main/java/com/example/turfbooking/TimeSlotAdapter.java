package com.example.turfbooking;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    private Context context;
    private List<String> timeSlots;
    private List<String> bookedSlots;
    private String selectedSlot = null;
    private OnSlotSelectedListener listener;

    public interface OnSlotSelectedListener {
        void onSlotSelected(String slot);
    }

    public TimeSlotAdapter(Context context, List<String> timeSlots, List<String> bookedSlots, OnSlotSelectedListener listener) {
        this.context = context;
        this.timeSlots = timeSlots;
        this.bookedSlots = bookedSlots;
        this.listener = listener;
    }

    public void updateBookedSlots(List<String> bookedSlots) {
        this.bookedSlots = bookedSlots;
        this.selectedSlot = null; // reset selection on date change
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_time_slot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String slot = timeSlots.get(position);
        holder.tvTimeSlot.setText(slot);

        boolean isBooked = bookedSlots.contains(slot);

        if (isBooked) {
            holder.tvTimeSlot.setBackgroundColor(Color.parseColor("#FF5252")); // Red
            holder.tvTimeSlot.setTextColor(Color.WHITE);
            holder.itemView.setOnClickListener(null);
            holder.itemView.setAlpha(0.6f);
        } else {
            holder.itemView.setAlpha(1.0f);
            if (slot.equals(selectedSlot)) {
                holder.tvTimeSlot.setBackgroundColor(Color.parseColor("#4CAF50")); // Green selected
                holder.tvTimeSlot.setTextColor(Color.WHITE);
            } else {
                holder.tvTimeSlot.setBackgroundResource(R.drawable.bg_search_bar); // Default
                holder.tvTimeSlot.setTextColor(Color.WHITE);
            }

            holder.itemView.setOnClickListener(v -> {
                selectedSlot = slot;
                listener.onSlotSelected(slot);
                notifyDataSetChanged();
            });
        }
    }

    @Override
    public int getItemCount() {
        return timeSlots.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeSlot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTimeSlot = itemView.findViewById(R.id.tvTimeSlot);
        }
    }
}
