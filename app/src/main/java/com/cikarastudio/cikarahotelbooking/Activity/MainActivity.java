package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cikarastudio.cikarahotelbooking.Manager.SessionManager;
import com.cikarastudio.cikarahotelbooking.R;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Home");

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        CardView roomListButton = findViewById(R.id.btn_homeRoomList);
        roomListButton.setOnClickListener(this);
        CardView myBookingButton = findViewById(R.id.btn_homeMyBooking);
        myBookingButton.setOnClickListener(this);
        CardView galleryButton = findViewById(R.id.btn_homeGallery);
        galleryButton.setOnClickListener(this);
        CardView locationButton = findViewById(R.id.btn_homeLocation);
        locationButton.setOnClickListener(this);
        CardView infoButton = findViewById(R.id.btn_homeInfo);
        infoButton.setOnClickListener(this);
        CardView myAccountButton = findViewById(R.id.btn_homeMyAccount);
        myAccountButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_homeRoomList:
                Intent roomListHome = new Intent(MainActivity.this, TipeKamarListActivity.class);
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
