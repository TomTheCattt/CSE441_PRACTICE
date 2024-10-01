package com.example.ex08;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CellPhoneActivity extends AppCompatActivity {

    EditText edtCall;
    Button btnCall, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cell_phone);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtCall = findViewById(R.id.edtCall);
        btnCall = findViewById(R.id.btnCall);
        btnBack = findViewById(R.id.btnBack1);

        btnCall.setOnClickListener(v->{
            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + edtCall.getText().toString()));
            if(ActivityCompat.checkSelfPermission(CellPhoneActivity.this, Manifest.permission.CALL_PHONE) !=
            PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CellPhoneActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;
            }
            startActivity(callIntent);
        });

        btnCall.setOnClickListener(v->{
            finish();
        });
    }
}