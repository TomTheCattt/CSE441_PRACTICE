package com.example.ex08;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SendSMSActivity extends AppCompatActivity {

    EditText edtSendSms;
    Button btnSendSms, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_send_smsactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSendSms = findViewById(R.id.edtSendSms);
        btnSendSms = findViewById(R.id.btnSendSms);
        btnBack = findViewById(R.id.btnBack2);

        btnSendSms.setOnClickListener(v->{
            Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + edtSendSms.getText().toString()));
            startActivity(sendIntent);
        });
        btnBack.setOnClickListener(v->{
            finish();
        });
    }
}