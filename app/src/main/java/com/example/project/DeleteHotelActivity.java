package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeleteHotelActivity extends AppCompatActivity {

    private TextView hotelNameTextView, hotelLocationTextView, hotelRatingTextView, hotelPriceTextView, hotelAvailabilityTextView;
    private Button deleteButton;

    private DatabaseReference hotelsRef;
    private String hotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_hotel);

        // Initialize Firebase Database reference
        hotelsRef = FirebaseDatabase.getInstance().getReference().child("hotels");

        // Initialize Views
        hotelNameTextView = findViewById(R.id.hotel_name_text_view);
        hotelLocationTextView = findViewById(R.id.hotel_location_text_view);
        hotelRatingTextView = findViewById(R.id.hotel_rating_text_view);
        hotelPriceTextView = findViewById(R.id.hotel_price_text_view);
        hotelAvailabilityTextView = findViewById(R.id.hotel_availability_text_view);
        deleteButton = findViewById(R.id.delete_button);

        // Retrieve hotelId passed from previous activity
        hotelId = getIntent().getStringExtra("hotelId");

        // Load current hotel details for display
        loadHotelDetails();

        // Delete button click listener
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHotel();
            }
        });
    }

    private void loadHotelDetails() {
        // TODO: Implement method to load hotel details from Firebase and populate TextView fields
        // Example code:
         hotelsRef.child(hotelId).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
             @Override
             public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                 Hotel hotel = dataSnapshot.getValue(Hotel.class);
                 if (hotel != null) {
                     hotelNameTextView.setText(hotel.getName());
                     hotelLocationTextView.setText(hotel.getLocation());
                     hotelRatingTextView.setText(String.valueOf(hotel.getRating()));
                     hotelPriceTextView.setText(String.format("$%s", hotel.getPricePerNight()));
                     hotelAvailabilityTextView.setText(hotel.isAvailable() ? "Available" : "Not Available");
                 }
             }

             @Override
             public void onCancelled(@NonNull com.google.firebase.database.DatabaseError databaseError) {
                 Toast.makeText(DeleteHotelActivity.this, "Failed to load hotel details", Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void deleteHotel() {
        // Delete hotel from Firebase Database
        hotelsRef.child(hotelId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DeleteHotelActivity.this, "Hotel deleted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DeleteHotelActivity.this, "Failed to delete hotel: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
