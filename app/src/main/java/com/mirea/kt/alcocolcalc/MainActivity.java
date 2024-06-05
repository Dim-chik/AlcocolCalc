package com.mirea.kt.alcocolcalc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textErr = findViewById(R.id.textErr);

        String server = "https://android-for-students.ru";
        String serverPath = "/coursework/login.php";

        buttonLogin.setOnClickListener(v -> {
            String lgn = editTextLogin.getText().toString();
            String pwd = editTextPassword.getText().toString();
            String g = "RIBO-01-22";

            HashMap<String, String> map = new HashMap<>();
            map.put("lgn", lgn);
            map.put("pwd", pwd);
            map.put("g", g);
            HTTPRunnable httpRunnable = new HTTPRunnable(server + serverPath, map);
            Thread th = new Thread(httpRunnable);
            th.start();
            try {
                th.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                JSONObject jSONObject;
                int result;
                try {
                    jSONObject = new JSONObject(httpRunnable.getResponseBody());
                    result = jSONObject.getInt("result_code");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                if (result == 1) {// Создаем Intent для открытия NextActivity
                    Intent intent = new Intent(MainActivity.this, NextActivity.class);

                    // Запускаем новую активность
                    startActivity(intent);
                } else {
                    textErr.setText("Неверно введены логин или пароль!");
                }
            }

        });

    }
}


