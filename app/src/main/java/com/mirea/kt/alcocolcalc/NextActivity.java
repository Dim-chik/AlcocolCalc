package com.mirea.kt.alcocolcalc;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

public class NextActivity extends AppCompatActivity {
    EditText editTextWeight, editTextStrength, editTextVolume, editTextTime;
    Button btnCalculate;
    TextView textViewResult;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switchGender;
    private double gender;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_activity);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextStrength = findViewById(R.id.editTextStrength);
        editTextVolume = findViewById(R.id.editTextVolume);
        editTextTime = findViewById(R.id.editTextTime);
        btnCalculate = findViewById(R.id.btnCalculate);
        textViewResult = findViewById(R.id.textViewResult);
        switchGender = findViewById(R.id.switchGender);

        btnCalculate.setOnClickListener(v -> {
            double weight = Double.parseDouble(editTextWeight.getText().toString());
            if (!(switchGender.isChecked())) {
                gender = 0.7;
            }
            if(switchGender.isChecked()){
                gender = 0.6;
            }
            double strength = Double.parseDouble(editTextStrength.getText().toString());
            double volume = Double.parseDouble(editTextVolume.getText().toString());
            double time = Double.parseDouble(editTextTime.getText().toString());

            double result = calculateAlcoholLevel(weight, gender, strength, volume, time);
            
            if(result < 0) result = 0;
            if(result < 0.3) textViewResult.setTextColor(Color.parseColor("green"));
            if(result >= 0.3) textViewResult.setTextColor(Color.parseColor("red"));

            @SuppressLint("DefaultLocale") String res = String.format("%.3f",result);

            textViewResult.setText("Результат:" + res);
        });
    }

    private double calculateAlcoholLevel(double weight, double gender, double strength, double volume, double time) {
        // формула для расчета уровня алкоголя в крови
        return ((volume * (strength/100) * 0.7893) / (weight * gender)) - (0.015 * time);
    }
}