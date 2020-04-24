package com.cikarastudio.cikarahotelbooking.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cikarastudio.cikarahotelbooking.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnloginLogin = findViewById(R.id.btn_loginLogin);
        btnloginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnloginLogin = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(btnloginLogin);
            }
        });

        TextView tvloginRegister = findViewById(R.id.tv_loginRegister);
        tvloginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tvloginRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(tvloginRegister);
            }
        });

    }
}
