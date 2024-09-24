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

public class MainActivity extends AppCompatActivity {

    Button btnClear, btnC, btnF, btnMove;
    EditText edtC, edtF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        DecimalFormat dcf = new DecimalFormat("#.00");

        btnClear = findViewById(R.id.btnClear);
        btnF = findViewById(R.id.btnFahrenheit);
        btnC = findViewById(R.id.btnCelsius);
        edtC = findViewById(R.id.edtC);
        edtF = findViewById(R.id.edtF);
        btnMove = findViewById(R.id.btnMove);

        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double c = Double.parseDouble(edtC.getText().toString());
                double rs = fahrenheitToCelsius(c);
                edtF.setText("" + dcf.format(rs));
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double f = Double.parseDouble(edtF.getText().toString());
                double rs = celsiusToFahrenheit(f);
                edtC.setText("" + dcf.format(rs));
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtF.setText("");
                edtC.setText("");
            }
        });

        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

    }

    private double celsiusToFahrenheit(double Celsius) {
        return Celsius * (9.0/5.0) + 32.0;
    };

    private double fahrenheitToCelsius(double Fahrenheit) {
        return (Fahrenheit - 32.0) * (5.0/9.0);
    }
}