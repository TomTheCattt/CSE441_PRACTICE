package com.example.prac13;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    GridView gridView2;
    Button btnBackToSection2, btnSection4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView2 = findViewById(R.id.gridView2);

        int arr[] = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4};
        ImageAdapter adapter = new ImageAdapter(MainActivity3.this, arr);

        gridView2.setAdapter(adapter);

        gridView2.setOnItemClickListener((parent, view, position, id) -> {
            //Toast.makeText(this, "" + arr[position], Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
            intent.putExtra("image", arr[position]);
            startActivity(intent);
        });

        btnBackToSection2 = findViewById(R.id.btnBackToSection2);
        btnBackToSection2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
            startActivity(intent);
        });

    }
}

