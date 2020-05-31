package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.cikarastudio.cikarahotelbooking.Model.TipeKamar;
import com.cikarastudio.cikarahotelbooking.R;

public class KamarListActivity extends AppCompatActivity {

    public static final String EXTRA_TIPEKAMAR = "extra_tipekamar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamar_list);
        getSupportActionBar().setTitle("List Kamar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        TextView tv_tipeId = findViewById(R.id.tv_tipeid);
//        TextView tv_tipeKamar = findViewById(R.id.tv_tipeKamar);

        TipeKamar tipekamar = getIntent().getParcelableExtra(EXTRA_TIPEKAMAR);
        String tipe_id = tipekamar.getTipe_id();
        String nama_tipe = tipekamar.getNama_tipe();
//        tv_tipeId.setText(tipe_id);
//        tv_tipeKamar.setText(nama_tipe);
    }
}
