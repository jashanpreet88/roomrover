package com.example.project;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddHotelActivity extends AppCompatActivity {

    private EditText hotelNameEditText, hotelLocationEditText, hotelRatingEditText,
            hotelImageUrlEditText, hotelPriceEditText;
    private CheckBox hotelAvailableCheckbox;
    private Button saveHotelButton;
    private DatabaseReference hotelsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        // Initialize Firebase
        hotelsRef = FirebaseDatabase.getInstance().getReference().child("hotels");

        // Initialize Views
        hotelNameEditText = findViewById(R.id.hotel_name_edittext);
        hotelLocationEditText = findViewById(R.id.hotel_location_edittext);
        hotelRatingEditText = findViewById(R.id.hotel_rating_edittext);
        hotelImageUrlEditText = findViewById(R.id.hotel_image_url_edittext);
        hotelPriceEditText = findViewById(R.id.hotel_price_edittext);
        hotelAvailableCheckbox = findViewById(R.id.hotel_available_checkbox);
        saveHotelButton = findViewById(R.id.save_hotel_button);

        // Save Hotel Button Click Listener
        saveHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHotel();
            }
        });
    }

    private void saveHotel() {
        String name = hotelNameEditText.getText().toString().trim();
        String location = hotelLocationEditText.getText().toString().trim();
        String ratingStr = hotelRatingEditText.getText().toString().trim();
        String imageUrl = hotelImageUrlEditText.getText().toString().trim();
        String priceStr = hotelPriceEditText.getText().toString().trim();
        boolean available = hotelAvailableCheckbox.isChecked();

        if (TextUtils.isEmpty(name)) {
            hotelNameEditText.setError("Please enter hotel name");
            return;
        }

        if (TextUtils.isEmpty(location)) {
            hotelLocationEditText.setError("Please enter location");
            return;
        }

        double rating = 0.0;
        if (!TextUtils.isEmpty(ratingStr)) {
            rating = Double.parseDouble(ratingStr);
        }

        if (TextUtils.isEmpty(imageUrl)) {
            hotelImageUrlEditText.setError("Please enter image URL");
            return;
        }

        double pricePerNight = 0.0;
        if (!TextUtils.isEmpty(priceStr)) {
            pricePerNight = Double.parseDouble(priceStr);
        }

        // Generate a new key for the hotel
        String hotelId = hotelsRef.push().getKey();

        // Create hotel object
        Hotel hotel = new Hotel(hotelId, name, location, rating, imageUrl, pricePerNight, available);

        // Save hotel to Firebase
        if (hotelId != null) {
            hotelsRef.child(hotelId).setValue(hotel)
                    .addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddHotelActivity.this, "Hotel added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new com.google.android.gms.tasks.OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddHotelActivity.this, "Failed to add hotel: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
