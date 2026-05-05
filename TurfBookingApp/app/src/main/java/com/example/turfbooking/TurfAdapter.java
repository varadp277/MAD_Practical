package com.example.turfbooking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TurfAdapter extends RecyclerView.Adapter<TurfAdapter.TurfViewHolder> {

    private List<Turf> turfList;
    private Context context;
    private int userId;

    public TurfAdapter(Context context, List<Turf> turfList, int userId) {
        this.context = context;
        this.turfList = turfList;
        this.userId = userId;
    }

    @NonNull
    @Override
    public TurfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_turf, parent, false);
        return new TurfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TurfViewHolder holder, int position) {
        Turf turf = turfList.get(position);
        holder.tvTurfName.setText(turf.getName());
        holder.tvTurfLocation.setText(turf.getLocation());
        holder.tvTurfPrice.setText("$" + turf.getPricePerHour() + " / hr");
        
        // Use placeholder dynamically
        int resId = context.getResources().getIdentifier(turf.getImageName(), "drawable", context.getPackageName());
        if (resId != 0) {
            holder.ivTurfImage.setImageResource(resId);
        } else {
            holder.ivTurfImage.setImageResource(android.R.drawable.ic_menu_gallery);
        }
        
        if (turf.getRating() > 0) {
            holder.tvTurfRating.setText(String.format("%.1f", turf.getRating()));
        } else {
            holder.tvTurfRating.setText("New");
        }
        
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TurfDetailActivity.class);
            intent.putExtra("TURF_ID", turf.getId());
            intent.putExtra("USER_ID", userId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return turfList.size();
    }

    public void updateTurfs(List<Turf> newTurfs) {
        this.turfList = newTurfs;
        notifyDataSetChanged();
    }

    public static class TurfViewHolder extends RecyclerView.ViewHolder {
        TextView tvTurfName, tvTurfLocation, tvTurfPrice, tvTurfRating;
        ImageView ivTurfImage;

        public TurfViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTurfName = itemView.findViewById(R.id.tvTurfName);
            tvTurfLocation = itemView.findViewById(R.id.tvTurfLocation);
            tvTurfPrice = itemView.findViewById(R.id.tvTurfPrice);
            tvTurfRating = itemView.findViewById(R.id.tvTurfRating);
            ivTurfImage = itemView.findViewById(R.id.ivTurfImage);
        }
    }
}
