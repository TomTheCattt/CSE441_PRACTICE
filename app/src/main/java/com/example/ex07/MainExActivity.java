package com.example.ex07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainExActivity extends AppCompatActivity {

    EditText edtInputA, edtInputB;
    Button btnCalculate, btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_ex);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtInputA = findViewById(R.id.edtInputA);
        edtInputB = findViewById(R.id.edtInputB);

        btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(v-> {
            int numA = Integer.parseInt(edtInputA.getText().toString());
            int numB = Integer.parseInt(edtInputB.getText().toString());
            Intent intent = new Intent(this, ChildExActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("numA", numA);
            bundle.putInt("numB", numB);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        });

        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(v->{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}