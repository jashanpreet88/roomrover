package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class HotelUserAdapter extends RecyclerView.Adapter<HotelUserAdapter.ViewHolder> {

    private List<Hotel> hotelList;
    private Context context;

    public HotelUserAdapter(List<Hotel> hotelList, Context context) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View hotelView = inflater.inflate(com.example.project.R.layout.user_item_hotel, parent, false);
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
            android.content.Intent intent = new android.content.Intent(context, ViewHotelActivity.class);
            intent.putExtra("hotelId", hotel.getId());
            context.startActivity(intent);
        });

        holder.bookingButton.setOnClickListener(v -> {
            android.content.Intent intent = new android.content.Intent(context, BookingHotelActivity.class);
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
        Button viewButton, bookingButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelImageView = itemView.findViewById(R.id.hotel_image);
            hotelNameTextView = itemView.findViewById(R.id.hotel_name_text);
            hotelLocationTextView = itemView.findViewById(R.id.hotel_location_text);
            hotelRatingTextView = itemView.findViewById(R.id.hotel_rating_text);
            hotelPriceTextView = itemView.findViewById(R.id.hotel_price_text);
            hotelAvailabilityTextView = itemView.findViewById(R.id.hotel_availability_text);
            viewButton = itemView.findViewById(R.id.view_button);
            bookingButton = itemView.findViewById(R.id.booking_button);
        }
    }
}
