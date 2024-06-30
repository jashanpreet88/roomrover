package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewHotelActivity extends AppCompatActivity {

    private ImageView hotelImageView;
    private TextView hotelNameTextView;
    private TextView hotelLocationTextView;
    private TextView hotelRatingTextView;
    private TextView hotelPriceTextView;
    private TextView hotelAvailabilityTextView;
    private DatabaseReference hotelRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_hotel);

        String hotelId = getIntent().getStringExtra("hotelId");
        hotelRef = FirebaseDatabase.getInstance().getReference().child("hotels").child(hotelId);

        hotelImageView = findViewById(R.id.hotel_image_view);
        hotelNameTextView = findViewById(R.id.hotel_name_text_view);
        hotelLocationTextView = findViewById(R.id.hotel_location_text_view);
        hotelRatingTextView = findViewById(R.id.hotel_rating_text_view);
        hotelPriceTextView = findViewById(R.id.hotel_price_text_view);
        hotelAvailabilityTextView = findViewById(R.id.hotel_availability_text_view);

        loadHotelDetails();
    }

    private void loadHotelDetails() {
        hotelRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot snapshot) {
                Hotel hotel = snapshot.getValue(Hotel.class);
                if (hotel != null) {
                    hotelNameTextView.setText(hotel.getName());
                    hotelLocationTextView.setText(hotel.getLocation());
                    hotelRatingTextView.setText(String.valueOf(hotel.getRating()));
                    hotelPriceTextView.setText(String.format("$%s", hotel.getPricePerNight()));
                    hotelAvailabilityTextView.setText(hotel.isAvailable() ? "Available" : "Not Available");
                    Picasso.get().load(hotel.getImageUrl()).into(hotelImageView);
                }
            }

            @Override
            public void onCancelled(@androidx.annotation.NonNull DatabaseError error) {
                // Handle possible errors.
            }
        });
    }
}
