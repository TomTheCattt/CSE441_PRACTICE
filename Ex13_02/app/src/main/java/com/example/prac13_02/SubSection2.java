package com.example.prac13_02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SubSection2 extends AppCompatActivity {
    private Bundle bundle;
    TextView textView3;
    ImageView imageView3;
    Button btnBackToMain2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_section2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gridView3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textView3 = findViewById(R.id.textView3);
        imageView3 = findViewById(R.id.imageView3);
        btnBackToMain2 = findViewById(R.id.btnBackToMain2);
        bundle = getIntent().getExtras();
        int position = bundle.getInt("image");
        textView3.setText(Section2.name[position]);
        imageView3.setImageResource(Section2.image[position]);
        btnBackToMain2.setOnClickListener(v -> {
            Intent intent = new Intent(this, Section2.class);
            startActivity(intent);
        });

    }
}