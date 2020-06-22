package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.cikarastudio.cikarahotelbooking.R;

import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getSupportActionBar().setTitle("Location");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView cardviewmaps = findViewById(R.id.cardview_maps);

        cardviewmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mTitle = "Hotel Sapphire Home";
                String geoUri = "http://maps.google.com/maps?q=loc:" + -6.838304 + "," + 107.928150 + " (" + mTitle + ")";
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                startActivity(intent);
            }
        });

    }
}
