package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cikarastudio.cikarahotelbooking.Adapter.GaleryAdapter;
import com.cikarastudio.cikarahotelbooking.Adapter.TipeKamarAdapter;
import com.cikarastudio.cikarahotelbooking.Model.TipeKamar;
import com.cikarastudio.cikarahotelbooking.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class GalleryActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private GaleryAdapter galeryAdapter;
    private ArrayList<TipeKamar> tipeKamarList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        getSupportActionBar().setTitle("Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_galleryList);
        recyclerView.setHasFixedSize(true);

        tipeKamarList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);

        parseJSON();
    }

//    private void parseJSON() {

        private void parseJSON() {
            String url = "http://kingdom.chatomz.com/json/hotel/tipe";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject tipe = response.getJSONObject(i);
                                    String tipe_id = tipe.getString("id");
                                    String nama_tipe = tipe.getString("nama_tipe");
                                    String harga = tipe.getString("harga");
                                    String kapasitas = tipe.getString("kapasitas");
                                    String deskripsi = tipe.getString("deskripsi");
                                    String gambar = tipe.getString("gambar");

                                    tipeKamarList.add(new TipeKamar(tipe_id, nama_tipe, harga, kapasitas, deskripsi, gambar));
                                }
                                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                galeryAdapter = new GaleryAdapter(getApplicationContext(), tipeKamarList);
                                recyclerView.setAdapter(galeryAdapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();

                }
            });
            requestQueue.add(jsonArrayRequest);
    }
}
