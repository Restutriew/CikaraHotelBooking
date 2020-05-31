package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cikarastudio.cikarahotelbooking.Adapter.TipeKamarAdapter;
import com.cikarastudio.cikarahotelbooking.Model.TipeKamar;
import com.cikarastudio.cikarahotelbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TipeKamarAdapter tipeKamarAdapter;
    private ArrayList<TipeKamar> tipeKamarList;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        getSupportActionBar().setTitle("Tipe Kamar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rv_roomList);
        recyclerView.setHasFixedSize(true);

        tipeKamarList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

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

                                tipeKamarList.add(new TipeKamar(tipe_id,nama_tipe, harga, kapasitas, deskripsi, gambar));
                            }

                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            tipeKamarAdapter = new TipeKamarAdapter(getApplicationContext(), tipeKamarList);
                            recyclerView.setAdapter(tipeKamarAdapter);
                            tipeKamarAdapter.setOnItemClickCallback(new TipeKamarAdapter.OnItemClickCallback() {
                                @Override
                                public void onItemClicked(TipeKamar data) {
                                    Intent detailmassage = new Intent(RoomListActivity.this, KamarListActivity.class);
                                    detailmassage.putExtra(KamarListActivity.EXTRA_TIPEKAMAR, data);
                                    startActivity(detailmassage);
                                }
                            });

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
