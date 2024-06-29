package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private List<Hotel> hotelList;

    public HotelAdapter(List<Hotel> hotelList) {
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View hotelView = inflater.inflate(R.layout.item_hotel, parent, false);
        return new ViewHolder(hotelView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.hotelNameTextView.setText(hotel.getName());
        holder.hotelLocationTextView.setText(hotel.getLocation());
        holder.hotelRatingTextView.setText(String.valueOf(hotel.getRating()));
        holder.hotelPriceTextView.setText(String.valueOf(hotel.getPricePerNight()));
        holder.hotelAvailabilityTextView.setText(hotel.isAvailable() ? "Available" : "Not Available");
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView hotelNameTextView;
        TextView hotelLocationTextView;
        TextView hotelRatingTextView;
        TextView hotelPriceTextView;
        TextView hotelAvailabilityTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTextView = itemView.findViewById(R.id.hotel_name_text);
            hotelLocationTextView = itemView.findViewById(R.id.hotel_location_text);
            hotelRatingTextView = itemView.findViewById(R.id.hotel_rating_text);
            hotelPriceTextView = itemView.findViewById(R.id.hotel_price_text);
            hotelAvailabilityTextView = itemView.findViewById(R.id.hotel_availability_text);
        }
    }
}
