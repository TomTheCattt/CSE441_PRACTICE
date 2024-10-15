package com.example.prac13;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    TextView txtLabel;
    GridView gridView;
    Button btnBackToSection1, btnSection3;

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

        String arr[] = {"Iphone", "Samsung", "Xiaomi", "Oppo", "Vivo", "Nokia"};

        txtLabel = findViewById(R.id.txtLabel);
        gridView = findViewById(R.id.gridView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arr);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            txtLabel.setText(arr[position]);
        });

        btnBackToSection1 = findViewById(R.id.btnBackToSection1);
        btnSection3 = findViewById(R.id.btnSection3);
        btnBackToSection1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });

        btnSection3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
        });
    }
}