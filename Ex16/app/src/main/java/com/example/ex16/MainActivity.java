package com.example.ex16;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtInputA, edtInputB, edtRs;
    Button btnSum, btnClear;
    TextView txtHistory;
    String history = "";

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

        edtInputA = findViewById(R.id.edtInputA);
        edtInputB = findViewById(R.id.edtInputB);
        edtRs = findViewById(R.id.edtRs);
        btnSum = findViewById(R.id.btnSum);
        btnClear = findViewById(R.id.btnClear);
        txtHistory = findViewById(R.id.txtHistory);

        SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);
        history = sharedPreferences.getString("history", "");
        txtHistory.setText(history);

        btnSum.setOnClickListener(v-> {
            int a = Integer.parseInt(edtInputA.getText().toString());
            int b = Integer.parseInt(edtInputB.getText().toString());
            int rs = a + b;
            edtRs.setText(String.valueOf(rs));
            history += a + " + " + b + " = " + rs;
            txtHistory.setText(history);
            history += "\n";
        });

        btnClear.setOnClickListener(v-> {
            history = "";
            txtHistory.setText(history);
        });

    }
}