package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.cikarastudio.cikarahotelbooking.Manager.SessionManager;
import com.cikarastudio.cikarahotelbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyAccountActivity extends AppCompatActivity {

    EditText et_updateName, et_updateEmail, et_updateAlamat, et_updateTelp;
    String SEid;
    Button btn_update,btn_logout;
    SessionManager sessionManager;
    private static String URL_READ = "http://ngoding.chatomz.com/cikara_hotel/read_user.php";
    private static String URL_UPDATE = "http://ngoding.chatomz.com/cikara_hotel/update_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        getSupportActionBar().setTitle("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_updateName = findViewById(R.id.et_updateName);
        et_updateEmail = findViewById(R.id.et_updateEmail);
        et_updateAlamat = findViewById(R.id.et_updateAlamat);
        et_updateTelp = findViewById(R.id.et_updateTelp);
        btn_update = findViewById(R.id.btn_update);
        btn_logout = findViewById(R.id.btn_logout);

        sessionManager = new SessionManager(this);
        HashMap<String ,String > user = sessionManager.getUserDetail();
        SEid = user.get(sessionManager.ID);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDetail();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });

    }

    private void updateDetail() {
        final String id = SEid;
        final String name = et_updateName.getText().toString().trim();
        final String email = et_updateEmail.getText().toString().trim();
        final String alamat = et_updateAlamat.getText().toString().trim();
        final String telp = et_updateTelp.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")){
                                Toast.makeText(MyAccountActivity.this, "Update Berhasil! ", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(name,email,id);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyAccountActivity.this, "Update Gagal ", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyAccountActivity.this, "Update Gagal ", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email",email);
                params.put("alamat",alamat);
                params.put("telp",telp);
                params.put("id",id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    //tampil data di edittext
    private void getUserDetail(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
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

                                    String strname = object.getString("name").trim();
                                    String stremail = object.getString("email").trim();
                                    String stralamat = object.getString("alamat").trim();
                                    String strtelp = object.getString("telp").trim();

                                    et_updateName.setText(strname);
                                    et_updateEmail.setText(stremail);
                                    et_updateAlamat.setText(stralamat);
                                    et_updateTelp.setText(strtelp);

                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MyAccountActivity.this, "Gagal Mengambil Data!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyAccountActivity.this, "Gagal Mengambi Data!", Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", SEid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }
}
