package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cikarastudio.cikarahotelbooking.Adapter.MyBookingAdapter;
import com.cikarastudio.cikarahotelbooking.Manager.SessionManager;
import com.cikarastudio.cikarahotelbooking.Model.Booking;
import com.cikarastudio.cikarahotelbooking.Model.TipeKamar;
import com.cikarastudio.cikarahotelbooking.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MyBookingActivity extends AppCompatActivity {

    private ArrayList<Booking> bookingList;
    String nama_rekening,no_rekening,hotel_boking_id;
    String id_user, uid_booking;
    SessionManager sessionManager;
    private MyBookingAdapter myBookingAdapter;
    private RecyclerView recyclerView;

    private static String URL_INPUTPAY = "http://ngoding.chatomz.com/cikara_hotel/pay_book.php";
    private static String URL_UPDATEKAMAR = "http://ngoding.chatomz.com/cikara_hotel/update_booking.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        id_user = user.get(sessionManager.ID);

        getSupportActionBar().setTitle("My Booking");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bookingList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_bookingList);
        recyclerView.setHasFixedSize(true);

        load_dataBooking();

    }

    //LOAD DATA BOOKING
    private void load_dataBooking() {
        String URL_READBOOKING = "http://www.kingdom.chatomz.com/json/databoking/"+id_user;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_READBOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id_booking = object.getString("id").trim();
                                    String nama_tipe = object.getString("nama_tipe").trim();
                                    String tgl_checkin = object.getString("tgl_checkin").trim();
                                    String harga = object.getString("harga").trim();
                                    String lama_hari = object.getString("lama_hari").trim();
                                    String status = object.getString("status").trim();

                                    int iharga = Integer.parseInt(harga);
                                    int ilama_hari = Integer.parseInt(lama_hari);
                                    int itotal_harga = iharga*ilama_hari;
                                    String total_harga =""+itotal_harga;

                                    bookingList.add(new Booking(id_booking, nama_tipe, tgl_checkin, lama_hari, total_harga, status));
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    myBookingAdapter = new MyBookingAdapter(getApplicationContext(), bookingList);
                                    recyclerView.setAdapter(myBookingAdapter);
                                    myBookingAdapter.setOnItemClickCallback(new MyBookingAdapter.OnItemClickCallback() {
                                        @Override
                                        public void onItemClicked(Booking data) {
                                            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MyBookingActivity.this,
                                                    R.style.BottomSheetDialogTheme);
                                            View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                                                    R.layout.layout_bottom_sheet_booking, (LinearLayout) findViewById(R.id.bottomSheetContainer)

                                            );

                                            uid_booking = data.getId_booking();
                                            hotel_boking_id = data.getId_booking();

                                            final EditText et_bookLamaHari = bottomSheetView.findViewById(R.id.et_payNamaRekening);
                                            final EditText et_bookLamaHaria = bottomSheetView.findViewById(R.id.et_payNomorRekening);

                                            bottomSheetView.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    nama_rekening = et_bookLamaHari.getText().toString().trim();
                                                    no_rekening = et_bookLamaHaria.getText().toString().trim();
                                                    //Request
                                                    inputPay();
                                                    updateBooking();
                                                    bottomSheetDialog.dismiss();
                                                    finish();
                                                    startActivity(getIntent());

                                                }
                                            });
                                            bottomSheetDialog.setContentView(bottomSheetView);
                                            bottomSheetDialog.show();
                                        }
                                    });
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyBookingActivity.this, "Data Booking Tidak Ada!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyBookingActivity.this, "Data Booking Tidak Ada!", Toast.LENGTH_SHORT).show();
            }
        })
        {
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MyBookingActivity.this);
        requestQueue.add(stringRequest);
    }

    private void updateBooking() {
//        final String sid_booking = uid_booking;
        final String utanggal_sekarang;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        utanggal_sekarang = dateFormat.format(cal.getTime());
        final String tgl_sekarang = utanggal_sekarang;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATEKAMAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
//                                Toast.makeText(MyBookingActivity.this, "Update Booking Sukses", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(MyBookingActivity.this, "Update Booking Gagal ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MyBookingActivity.this, "Update Kamar Gagal ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", uid_booking);
                params.put("tanggal_sekarang",tgl_sekarang);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void inputPay() {
        final String user_id = id_user;
        final String hotel_booking_id = hotel_boking_id;
        final String unama_rekening = nama_rekening;
        final String uno_rekening = no_rekening;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INPUTPAY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(MyBookingActivity.this, "Pembayaran Berhasil ", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyBookingActivity.this, "Pembayaran Gagal ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyBookingActivity.this, "Pembayaran Gagal ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hotel_boking_id", hotel_booking_id);
                params.put("nama_rekening", unama_rekening);
                params.put("no_rekening", uno_rekening);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
