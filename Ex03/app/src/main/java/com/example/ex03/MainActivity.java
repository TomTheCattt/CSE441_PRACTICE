package com.example.ex03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnSum, btnMinus, btnMulti, btnDivide;
    TextView txtResult;
    EditText edtInputA, edtInputB;

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

        btnSum = findViewById(R.id.btnSum);
        btnMinus = findViewById(R.id.btnMinus);
        btnMulti = findViewById(R.id.btnMulti);
        btnDivide = findViewById(R.id.btnDivide);
        edtInputA = findViewById(R.id.edtInputA);
        edtInputB = findViewById(R.id.edtInputB);
        txtResult = findViewById(R.id.txtResult);

        btnSum.setOnClickListener(v -> txtResult.setText("" + getSum()));
        btnMinus.setOnClickListener(v -> txtResult.setText("" + getMinus()));
        btnMulti.setOnClickListener(v -> txtResult.setText("" + getMulti()));
        btnDivide.setOnClickListener(v -> txtResult.setText("" + getDivide()));
    }

    double getSum() {
        double numA = Double.parseDouble(edtInputA.getText().toString());
        double numB = Double.parseDouble(edtInputB.getText().toString());
        return numA + numB;
    }

    double getMinus() {
        double numA = Double.parseDouble(edtInputA.getText().toString());
        double numB = Double.parseDouble(edtInputB.getText().toString());
        return numA - numB;
    }

    double getMulti() {
        double numA = Double.parseDouble(edtInputA.getText().toString());
        double numB = Double.parseDouble(edtInputB.getText().toString());
        return numA * numB;
    }

    double getDivide() {
        double numA = Double.parseDouble(edtInputA.getText().toString());
        double numB = Double.parseDouble(edtInputB.getText().toString());
        if (numB == 0) {
            Toast.makeText(this, "Cant divide zero", Toast.LENGTH_SHORT).show();
            return 0;
        } else {
            return numA / numB;
        }
    }
}