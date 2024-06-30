package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private RecyclerView hotelsRecyclerView;
    private Button addHotelButton;
    private DatabaseReference hotelsRef;
    private List<Hotel> hotelList;
    private HotelUserAdapter hotelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Initialize Firebase
        hotelsRef = FirebaseDatabase.getInstance().getReference().child("hotels");

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Room Rover");

        // Initialize RecyclerView and Adapter
        hotelsRecyclerView = findViewById(R.id.hotels_recycler_view);
        hotelsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        hotelList = new ArrayList<>();
        hotelAdapter = new HotelUserAdapter(hotelList, this);
        hotelsRecyclerView.setAdapter(hotelAdapter);



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
                Toast.makeText(UserActivity.this, "Failed to retrieve hotels: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out_menu) {
            startActivity(new Intent(UserActivity.this, SignOutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
