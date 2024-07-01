package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditHotelActivity extends AppCompatActivity {

    private EditText editHotelName, editHotelLocation, editHotelRating, editHotelPrice;
    private CheckBox editHotelAvailable;
    private Button saveButton;

    private DatabaseReference hotelsRef;
    private String hotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_hotel);

        // Initialize Firebase Database reference
        hotelsRef = FirebaseDatabase.getInstance().getReference().child("hotels");

        // Initialize Views
        editHotelName = findViewById(R.id.edit_hotel_name);
        editHotelLocation = findViewById(R.id.edit_hotel_location);
        editHotelRating = findViewById(R.id.edit_hotel_rating);
        editHotelPrice = findViewById(R.id.edit_hotel_price);
        editHotelAvailable = findViewById(R.id.edit_hotel_available);
        saveButton = findViewById(R.id.save_button);

        // Retrieve hotelId passed from previous activity
        hotelId = getIntent().getStringExtra("hotelId");

        // Load current hotel details for editing
        loadHotelDetails();

        // Save changes button click listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });
    }

    private void loadHotelDetails() {
         hotelsRef.child(hotelId).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
             @Override
             public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                 Hotel hotel = dataSnapshot.getValue(Hotel.class);
                 if (hotel != null) {
                     editHotelName.setText(hotel.getName());
                     editHotelLocation.setText(hotel.getLocation());
                     editHotelRating.setText(String.valueOf(hotel.getRating()));
                     editHotelPrice.setText(String.valueOf(hotel.getPricePerNight()));
                     editHotelAvailable.setChecked(hotel.isAvailable());
                 }
             }

             @Override
             public void onCancelled(@NonNull com.google.firebase.database.DatabaseError databaseError) {
                 Toast.makeText(EditHotelActivity.this, "Failed to load hotel details", Toast.LENGTH_SHORT).show();
             }
         });
    }

    private void saveChanges() {
        String name = editHotelName.getText().toString().trim();
        String location = editHotelLocation.getText().toString().trim();
        double rating = Double.parseDouble(editHotelRating.getText().toString().trim());
        double pricePerNight = Double.parseDouble(editHotelPrice.getText().toString().trim());
        boolean available = editHotelAvailable.isChecked();

        // Update hotel details in Firebase Database
        hotelsRef.child(hotelId).child("name").setValue(name);
        hotelsRef.child(hotelId).child("location").setValue(location);
        hotelsRef.child(hotelId).child("rating").setValue(rating);
        hotelsRef.child(hotelId).child("pricePerNight").setValue(pricePerNight);
        hotelsRef.child(hotelId).child("available").setValue(available)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditHotelActivity.this, "Hotel details updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditHotelActivity.this, "Failed to update hotel details: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
