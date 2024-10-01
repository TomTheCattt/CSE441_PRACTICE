package com.example.ex08;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnSendSms, btnCall, btnCamera;

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

        btnCall = findViewById(R.id.btnCall);
        btnSendSms = findViewById(R.id.btnSendSms);
        btnCamera = findViewById(R.id.btnCamera);

        btnCall.setOnClickListener(v->{
            Intent intent = new Intent(this, CellPhoneActivity.class);
            startActivity(intent);
        });

        btnSendSms.setOnClickListener(v->{
            Intent intent = new Intent(this, SendSMSActivity.class);
            startActivity(intent);
        });

        btnCamera.setOnClickListener(v->{
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        });
    }
}