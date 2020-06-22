package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
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
import com.cikarastudio.cikarahotelbooking.Adapter.TipeKamarAdapter;
import com.cikarastudio.cikarahotelbooking.Manager.SessionManager;
import com.cikarastudio.cikarahotelbooking.Model.TipeKamar;
import com.cikarastudio.cikarahotelbooking.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class TipeKamarListActivity extends AppCompatActivity {

    private Timer timer = new Timer();
    private final long DELAY = 1000; // in ms
    String id_tipe_hotel, id_user, checkin_tgl, hari_lama, harga_tipe, id_kamar,tanggal_sekarang;
    SessionManager sessionManager;
    private RecyclerView recyclerView;
    private TipeKamarAdapter tipeKamarAdapter;
    private ArrayList<TipeKamar> tipeKamarList;
    private RequestQueue requestQueue;
    private static String URL_INPUTBOOKING = "http://ngoding.chatomz.com/cikara_hotel/book_hotel.php";
    private static String URL_READKAMAR = "http://ngoding.chatomz.com/cikara_hotel/read_kamar.php";
    private static String URL_UPDATEKAMAR = "http://ngoding.chatomz.com/cikara_hotel/update_kamar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipe_kamar_list);

        getSupportActionBar().setTitle("Tipe Kamar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Session + getID
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        id_user = user.get(sessionManager.ID);

        //Recyclerview
        recyclerView = findViewById(R.id.rv_roomList);
        recyclerView.setHasFixedSize(true);

        tipeKamarList = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        tanggal_sekarang = dateFormat.format(cal.getTime());

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

                                tipeKamarList.add(new TipeKamar(tipe_id, nama_tipe, harga, kapasitas, deskripsi, gambar));
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            tipeKamarAdapter = new TipeKamarAdapter(getApplicationContext(), tipeKamarList);
                            recyclerView.setAdapter(tipeKamarAdapter);
                            tipeKamarAdapter.setOnItemClickCallback(new TipeKamarAdapter.OnItemClickCallback() {
                                @Override
                                public void onItemClicked(TipeKamar data) {
                                    final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TipeKamarListActivity.this,
                                            R.style.BottomSheetDialogTheme);
                                    View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(
                                            R.layout.layout_bottom_sheet, (LinearLayout) findViewById(R.id.bottomSheetContainer)
                                    );

                                    id_tipe_hotel = data.getTipe_id();
                                    harga_tipe = data.getHarga();

                                    final TextView book_nomorKamar = bottomSheetView.findViewById(R.id.book_nomorKamar);

                                    //READ KAMAR
                                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READKAMAR,
                                            new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {

                                                    try {
                                                        JSONObject jsonObject = new JSONObject(response);
                                                        String success = jsonObject.getString("success");
                                                        JSONArray jsonArray = jsonObject.getJSONArray("read");

                                                        if (success.equals("1")){
                                                            for (int i=0; i<jsonArray.length();i++){

                                                                JSONObject object = jsonArray.getJSONObject(i);

                                                                String id_kamarget = object.getString("id").trim();
                                                                String nomor_kamar = object.getString("nomor_kamar").trim();

                                                                String nomor_kamarget= "Nomor Kamar : "+nomor_kamar;
                                                                book_nomorKamar.setText(nomor_kamarget);
                                                                id_kamar = id_kamarget;

                                                            }
                                                        }


                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                        Toast.makeText(TipeKamarListActivity.this, "Gagal Mengambil Data!", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            }
                                            , new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(TipeKamarListActivity.this, "Gagal Mengambi Data!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> params = new HashMap<>();
                                            params.put("hotel_tipe_id", id_tipe_hotel);
                                            return params;
                                        }
                                    };

                                    RequestQueue requestQueue = Volley.newRequestQueue(TipeKamarListActivity.this);
                                    requestQueue.add(stringRequest);

                                    //Set Judul
                                    TextView Judul = bottomSheetView.findViewById(R.id.book_namaTipe);
                                    Judul.setText(convertUpperCase(data.getNama_tipe()));

                                    final EditText et_bookLamaHari = bottomSheetView.findViewById(R.id.et_bookLamaHari);
                                    final TextView book_harga = bottomSheetView.findViewById(R.id.book_harga);



                                    //TextWatcher
                                    TextWatcher textWatcher =  new TextWatcher() {
                                        @Override
                                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                            if (!et_bookLamaHari.getText().toString().equals("")){
                                                int templama_hari = Integer.parseInt(et_bookLamaHari.getText().toString());
                                                int tempharga = Integer.parseInt(harga_tipe);
                                                int total_harga = templama_hari*tempharga;
                                                String stotal_harga = ""+total_harga;
                                                String book_hargaa = ""+convertText(stotal_harga);
                                                book_harga.setText("RP. "+book_hargaa);

                                            }

                                        }

                                        @Override
                                        public void afterTextChanged(Editable editable) {

                                        }
                                    };
                                    et_bookLamaHari.addTextChangedListener(textWatcher);



                                    //Set Tanggal
                                    final EditText et_bookTanggal = bottomSheetView.findViewById(R.id.et_bookTanggal);
                                    et_bookTanggal.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Calendar calendar = Calendar.getInstance();
                                            int currentDate = calendar.get(Calendar.DATE);
                                            int currentMonth = calendar.get(Calendar.MONTH);
                                            int currentYear = calendar.get(Calendar.YEAR);

                                            DatePickerDialog datePickerDialog = new DatePickerDialog(TipeKamarListActivity.this, new DatePickerDialog.OnDateSetListener() {
                                                @Override
                                                public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                                                    int monthplus = month + 1;
                                                    String monthString = String.valueOf(monthplus);
                                                    String dateString = String.valueOf(date);
                                                    if (monthplus < 10) {
                                                        monthString = "0" + monthplus;
                                                    }
                                                    if (date < 10) {

                                                        dateString = "0" + date;
                                                    }
                                                    String dateAllString = year + "-" + monthString + "-" + dateString;
                                                    et_bookTanggal.setText(dateAllString);
                                                }
                                            }, currentYear, currentMonth, currentDate);
                                            datePickerDialog.show();
                                        }
                                    });

                                    //SET HARGA

                                    //Set Button Booking
                                    bottomSheetView.findViewById(R.id.btn_book).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            checkin_tgl = et_bookTanggal.getText().toString().trim();
                                            hari_lama = et_bookLamaHari.getText().toString().trim();
                                            //Request
                                            updateKamar();
                                            inputBook();
                                            bottomSheetDialog.dismiss();

                                        }
                                    });
                                    //Show BSD
                                    bottomSheetDialog.setContentView(bottomSheetView);
                                    bottomSheetDialog.show();
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

    private void updateKamar() {
        String utanggal_sekarang;
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
//                                Toast.makeText(TipeKamarListActivity.this, "Booking Sukses", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(TipeKamarListActivity.this, "Booking Gagal ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(TipeKamarListActivity.this, "Update Kamar Gagal ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id_kamar);
                params.put("tanggal_sekarang",tgl_sekarang);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void inputBook() {
        final String user_id = id_user;
        final String hotel_tipe_id = id_tipe_hotel;
        final String tgl_checkin = checkin_tgl;
        final String lama_hari = hari_lama;
        final String tgl_sekarang = tanggal_sekarang;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_INPUTBOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(TipeKamarListActivity.this, "Booking Sukses ", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TipeKamarListActivity.this, "Booking Gagal ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TipeKamarListActivity.this, "Booking Gagal : ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("hotel_tipe_id", hotel_tipe_id);
                params.put("tgl_checkin", tgl_checkin);
                params.put("lama_hari", lama_hari);
                params.put("tanggal_sekarang", tgl_sekarang);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //convertext
    private String convertText(String text) {
        StringBuilder stringBuilder = new StringBuilder(text);
        for (int i = stringBuilder.length() - 3; i > 0; i -= 3) {
            stringBuilder.insert(i, ".");
        }
        return stringBuilder.toString();
    }

    //CONVERTUPPERCASE
    private String convertUpperCase(String text){
        String output = "";

        String[] textArray = text.trim().split("\\s");
        for (int i= 0; i<textArray.length;i++){
            textArray[i] = textArray[i].substring(0,1).toUpperCase() + textArray[i].substring(1);
        }

        for (int i=0; i<textArray.length;i++){
            output = output+textArray[i]+" ";
        }

        return output.trim();
    }

}
