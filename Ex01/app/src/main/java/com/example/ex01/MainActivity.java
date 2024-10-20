package com.example.ex01;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnGetSum;
    EditText edtInputA;
    EditText edtInputB;
    TextView txtResult;

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

        btnGetSum = findViewById(R.id.btnGetSum);
        edtInputA = findViewById(R.id.edtInputA);
        edtInputB = findViewById(R.id.edtInputB);
        txtResult = findViewById(R.id.txtResult);

        btnGetSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numA = Integer.parseInt(edtInputA.getText().toString());
                int numB = Integer.parseInt(edtInputB.getText().toString());
                txtResult.setText("" + getSum(numA, numB));
            }
        });
    }

    private int getSum(int numA, int numB) {
        return numA + numB;
    }
}