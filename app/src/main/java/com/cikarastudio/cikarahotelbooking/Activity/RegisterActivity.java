package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cikarastudio.cikarahotelbooking.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText name, email, password, konfirmasi_password, alamat, telp;
    Button daftar;
    private static String URL_REGIST = "http://ngoding.chatomz.com/cikara_hotel/register_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        konfirmasi_password = findViewById(R.id.et_konfirmasiPassword);
        alamat = findViewById(R.id.et_alamat);
        telp = findViewById(R.id.et_telp);
        daftar = findViewById(R.id.btn_daftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Sname = name.getText().toString();
                String Semail = email.getText().toString();
                String Spassword = password.getText().toString();
                String Skonfirmasi_password = konfirmasi_password.getText().toString();
                String Salamat = alamat.getText().toString();
                String Stelp = telp.getText().toString();

                if (Sname.equals("")) {
                    name.setError("Silahkan masukkan nama anda!");
                    name.requestFocus();
                } else if (Semail.equals("")) {
                    email.setError("Silahkan masukkan email anda!");
                    email.requestFocus();
                } else if (Spassword.equals("")) {
                    password.setError("Silahkan masukkan pasword anda!");
                    password.requestFocus();
                } else if (Skonfirmasi_password.equals("")) {
                    konfirmasi_password.setError("Silahkan masukkan konfirmasi password!");
                    konfirmasi_password.requestFocus();
                } else if (Salamat.equals("")) {
                    alamat.setError("Silahkan masukkan alamat anda!");
                    alamat.requestFocus();
                } else if (Stelp.equals("")) {
                    alamat.setError("Silahkan masukkan no handphone anda!");
                    alamat.requestFocus();
                } else if (!Spassword.equals(Skonfirmasi_password)) {
                    konfirmasi_password.setError("Password tidak cocok!");
                    konfirmasi_password.requestFocus();
                } else {
                    fungsidaftar();
                }
            }
        });
    }

    private void fungsidaftar() {
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String alamat = this.alamat.getText().toString().trim();
        final String telp = this.telp.getText().toString().trim();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        final String tanggal = dateFormat.format(cal.getTime());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(RegisterActivity.this, "Register Sukses", Toast.LENGTH_SHORT).show();
                                Intent regIN = new Intent(RegisterActivity.this,LoginActivity.class);
                                startActivity(regIN);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register Gagal ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "Register Gagal ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("alamat", alamat);
                params.put("telp", telp);
                params.put("tanggal", tanggal);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
