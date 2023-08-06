package com.example.blogandroidnetworking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blogandroidnetworking.MainActivity;
import com.example.blogandroidnetworking.R;
import com.example.blogandroidnetworking.utils.ApiUrl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

public class LoginActivity extends AppCompatActivity {
    String TAG = "LoginActivity";
    private RequestQueue requestQueue;
    private EditText edtUsername, edtPassword;
    private Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        edtUsername = findViewById(R.id.et_username);
        edtPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            Toast.makeText(this, "feature is developing", Toast.LENGTH_SHORT).show();
        });
        btnLogin.setOnClickListener(v -> {
            if (edtUsername.getText().toString().isEmpty()
                    || edtPassword.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                return;
            } else if (edtUsername.getText().toString().length() < 6
                    && edtPassword.getText().toString().length() < 6) {
                Toast.makeText(this, "Username and password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }
            handleLogin();
        });

    }

    private void handleLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ApiUrl.BASE_URL+"users", response -> {
            JsonArray jsonArray = new Gson().fromJson(response, JsonArray.class);
            for (int i = 0; i < jsonArray.size(); i++) {
                String username = jsonArray.get(i).getAsJsonObject().get("username").getAsString();
                String password = jsonArray.get(i).getAsJsonObject().get("password").getAsString();
                Log.d(TAG, "handleLogin: " + username + " " + password);
                if (username.equals(edtUsername.getText().toString())
                        && password.equals(edtPassword.getText().toString())) {
                    Log.d(TAG, "handleLogin: login success");
                    String id = jsonArray.get(i).getAsJsonObject().get("id").getAsString();
                    String name = jsonArray.get(i).getAsJsonObject().get("username").getAsString();
                    String avatar = jsonArray.get(i).getAsJsonObject().get("avatar").getAsString();
                    String type = jsonArray.get(i).getAsJsonObject().get("type").getAsString();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("username", name);
                    intent.putExtra("avatar", avatar);
                    intent.putExtra("type", type);
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            }
        }, error -> {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        });
        requestQueue.add(stringRequest);
    }
}