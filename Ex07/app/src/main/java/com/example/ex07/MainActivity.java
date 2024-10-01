package com.example.ex07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnMove, btnEx, btnOtherEx;

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

        btnMove = findViewById(R.id.btnMove);
        btnMove.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ChildActivity.class);
            startActivity(intent);
        });

        btnEx = findViewById(R.id.btnEx);
        btnEx.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, MainExActivity.class);
            startActivity(intent);
        });

        btnOtherEx = findViewById(R.id.btnOtherEx);
        btnOtherEx.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, OtherMainExActivity.class);
            startActivity(intent);
        });
    }
}