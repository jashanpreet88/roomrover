package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HotelManagerAdapter extends RecyclerView.Adapter<HotelManagerAdapter.ViewHolder> {

    private List<Hotel> hotelList;
    private Context context;

    public HotelManagerAdapter(List<Hotel> hotelList, Context context) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View hotelView = inflater.inflate(R.layout.manager_item_hotel, parent, false);
        return new ViewHolder(hotelView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);

        holder.hotelNameTextView.setText(hotel.getName());
        holder.hotelLocationTextView.setText(hotel.getLocation());
        holder.hotelRatingTextView.setText(String.valueOf(hotel.getRating()));
        holder.hotelPriceTextView.setText(String.format("$%s", hotel.getPricePerNight()));
        holder.hotelAvailabilityTextView.setText(hotel.isAvailable() ? "Available" : "Not Available");

        // Load hotel image using Picasso
        Picasso.get().load(hotel.getImageUrl()).into(holder.hotelImageView);

        // Set up the buttons' onClick listeners
        holder.viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewHotelActivity.class);
            intent.putExtra("hotelId", hotel.getId());
            context.startActivity(intent);
        });

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditHotelActivity.class);
            intent.putExtra("hotelId", hotel.getId());
            context.startActivity(intent);
        });

        holder.deleteButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, DeleteHotelActivity.class);
            intent.putExtra("hotelId", hotel.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hotelImageView;
        TextView hotelNameTextView;
        TextView hotelLocationTextView;
        TextView hotelRatingTextView;
        TextView hotelPriceTextView;
        TextView hotelAvailabilityTextView;
        Button viewButton;
        Button editButton;
        Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImageView = itemView.findViewById(R.id.hotel_image);
            hotelNameTextView = itemView.findViewById(R.id.hotel_name_text);
            hotelLocationTextView = itemView.findViewById(R.id.hotel_location_text);
            hotelRatingTextView = itemView.findViewById(R.id.hotel_rating_text);
            hotelPriceTextView = itemView.findViewById(R.id.hotel_price_text);
            hotelAvailabilityTextView = itemView.findViewById(R.id.hotel_availability_text);
            viewButton = itemView.findViewById(R.id.view_button);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
