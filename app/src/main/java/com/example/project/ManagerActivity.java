package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerActivity extends AppCompatActivity {

    private RecyclerView hotelsRecyclerView;
    private Button addHotelButton;
    private DatabaseReference hotelsRef;
    private List<Hotel> hotelList;
    private HotelAdapter hotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        // Initialize Firebase
        hotelsRef = FirebaseDatabase.getInstance().getReference().child("hotels");

        // Initialize RecyclerView and Adapter
        hotelsRecyclerView = findViewById(R.id.hotels_recycler_view);
        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelList = new ArrayList<>();
        hotelAdapter = new HotelAdapter(hotelList);
        hotelsRecyclerView.setAdapter(hotelAdapter);

        // Initialize Add Hotel Button
        addHotelButton = findViewById(R.id.add_hotel_button);
        addHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, AddHotelActivity.class));
            }
        });

        // Retrieve hotels from Firebase
        retrieveHotels();
    }

    private void retrieveHotels() {
        hotelsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hotelList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Hotel hotel = snapshot.getValue(Hotel.class);
                    if (hotel != null) {
                        hotel.setId(snapshot.getKey());
                        hotelList.add(hotel);
                    }
                }
                hotelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ManagerActivity.this, "Failed to retrieve hotels: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
