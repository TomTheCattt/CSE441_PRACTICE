package com.example.ex07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class ChildExActivity extends AppCompatActivity {

    TextView txtResult;
    Button btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_child_ex);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        txtResult = findViewById(R.id.txtResult);
        btnReturn = findViewById(R.id.btnReturn);
        assert bundle != null;
        int numA = bundle.getInt("numA");
        int numB = bundle.getInt("numB");
        String rs = "";
        if(numA == 0 && numB == 0) {
            rs = "Unlimited result";
        } else if(numA == 0) {
            rs = "Null";
        } else {
            DecimalFormat dcf = new DecimalFormat("0.##");
            rs = dcf.format(-numB * 1.0/numA);
        }
        txtResult.setText(rs);
        btnReturn.setOnClickListener(v->{
            finish();
        });
    }
}