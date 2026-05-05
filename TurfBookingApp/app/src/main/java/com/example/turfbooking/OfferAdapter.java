package com.example.turfbooking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    private Context context;
    private List<Offer> offerList;

    public OfferAdapter(Context context, List<Offer> offerList) {
        this.context = context;
        this.offerList = offerList;
    }

    @NonNull
    @Override
    public OfferViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_offer, parent, false);
        return new OfferViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferViewHolder holder, int position) {
        Offer offer = offerList.get(position);
        holder.tvOfferTitle.setText(offer.getTitle());
        holder.tvOfferSubtitle.setText(offer.getSubtitle());
        holder.ivOfferImage.setImageResource(offer.getImageResId());
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {
        ImageView ivOfferImage;
        TextView tvOfferTitle, tvOfferSubtitle;

        public OfferViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOfferImage = itemView.findViewById(R.id.ivOfferImage);
            tvOfferTitle = itemView.findViewById(R.id.tvOfferTitle);
            tvOfferSubtitle = itemView.findViewById(R.id.tvOfferSubtitle);
        }
    }
}
