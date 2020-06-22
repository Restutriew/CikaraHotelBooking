package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cikarastudio.cikarahotelbooking.Manager.SessionManager;
import com.cikarastudio.cikarahotelbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText et_loginEmail, et_loginPassword;
    Button btnloginLogin;
    private static String URL_LOGIN = "http://ngoding.chatomz.com/cikara_hotel/login_user.php";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        et_loginEmail = findViewById(R.id.et_loginEmail);
        et_loginPassword = findViewById(R.id.et_loginPassword);
        btnloginLogin = findViewById(R.id.btn_loginLogin);


        btnloginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Semail = et_loginEmail.getText().toString();
                String Spassword = et_loginPassword.getText().toString();
                if (Semail.equals("")) {
                    et_loginEmail.setError("Silahkan masukkan email!");
                    et_loginEmail.requestFocus();
                } else if (Spassword.equals("")) {
                    et_loginPassword.setError("Silahkan masukkan password!");
                    et_loginPassword.requestFocus();
                } else {
                    login();
                }
            }
        });

        TextView tvloginRegister = findViewById(R.id.tv_loginRegister);
        tvloginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tvloginRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(tvloginRegister);
            }
        });

    }

    private void login() {
        final String email = this.et_loginEmail.getText().toString().trim();
        final String password = this.et_loginPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            if (success.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id").trim();
                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    sessionManager.createSession(name, email, id);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Gagal: Password Salah!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Login Gagal : Email Salah!", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Login Gagal : ", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
