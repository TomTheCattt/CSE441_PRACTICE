package com.example.ex04;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity2 extends AppCompatActivity {

    EditText edtName, edtHeight, edtWeight, edtBMI, edtDiagnose;
    Button btnCal, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCal = findViewById(R.id.btnCal);
        btnBack = findViewById(R.id.btnBack);
        edtName = findViewById(R.id.edtName);
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        edtBMI = findViewById(R.id.edtBMI);
        edtDiagnose = findViewById(R.id.edtDiagnose);

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double height = Double.parseDouble(edtHeight.getText().toString());
                double weight = Double.parseDouble(edtWeight.getText().toString());
                double bmi = calBMI(weight, height);
                String diagnose = "";
                if(bmi < 18.0) {
                    diagnose = "Skinny";
                } else if(bmi > 18.0 && bmi < 24.9) {
                    diagnose = "Normal";
                } else if(bmi > 25 && bmi < 29.9) {
                    diagnose = "Fat level I";
                } else if(bmi > 30 && bmi < 34.9) {
                    diagnose = "Fat level II";
                } else {
                    diagnose = "Fat level III";
                }
                DecimalFormat dcf = new DecimalFormat("#.0");
                edtBMI.setText("" + dcf.format(bmi));
                edtDiagnose.setText(diagnose);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private double calBMI(double weight, double height) {
        return weight/Math.pow(height,2);
    }
}