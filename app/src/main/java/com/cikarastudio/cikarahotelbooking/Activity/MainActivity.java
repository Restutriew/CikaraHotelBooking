package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cikarastudio.cikarahotelbooking.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");

        Button roomListButton = findViewById(R.id.btn_homeRoomList);
        roomListButton.setOnClickListener(this);
        Button myBookingButton = findViewById(R.id.btn_homeMyBooking);
        myBookingButton.setOnClickListener(this);
        Button galleryButton = findViewById(R.id.btn_homeGallery);
        galleryButton.setOnClickListener(this);
        Button locationButton = findViewById(R.id.btn_homeLocation);
        locationButton.setOnClickListener(this);
        Button infoButton = findViewById(R.id.btn_homeInfo);
        infoButton.setOnClickListener(this);
        Button myAccountButton = findViewById(R.id.btn_homeMyAccount);
        myAccountButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_homeRoomList:
                Intent roomListHome = new Intent(MainActivity.this, RoomListActivity.class);
                startActivity(roomListHome);
                break;

            case R.id.btn_homeMyBooking:
                Intent myBookingHome = new Intent(MainActivity.this, MyBookingActivity.class);
                startActivity(myBookingHome);
                break;

            case R.id.btn_homeGallery:
                Intent galleryHome = new Intent(MainActivity.this, GalleryActivity.class);
                startActivity(galleryHome);
                break;

            case R.id.btn_homeLocation:
                Intent locationHome = new Intent(MainActivity.this, LocationActivity.class);
                startActivity(locationHome);
                break;
            case R.id.btn_homeInfo:
                Intent infoHome = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(infoHome);
                break;
            case R.id.btn_homeMyAccount:
                Intent myAccountHome = new Intent(MainActivity.this, MyAccountActivity.class);
                startActivity(myAccountHome);
                break;
        }
    }
}
