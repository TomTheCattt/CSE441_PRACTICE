package com.example.ex05;

import android.content.Intent;
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

import java.text.DecimalFormat;

public class MainActivity2 extends AppCompatActivity {

    EditText edtInputA, edtInputB, edtInputC;
    Button btnContinue, btnCalculate, btnExit, btnBack;
    TextView txtCalculateResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtInputA = findViewById(R.id.edtInputA);
        edtInputB = findViewById(R.id.edtInputB);
        edtInputC = findViewById(R.id.edtInputC);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnContinue = findViewById(R.id.btnContinue);
        btnExit = findViewById(R.id.btnExit);
        txtCalculateResult = findViewById(R.id.txtCalculateResult);

        btnCalculate.setOnClickListener(v -> {
            double numA = Double.parseDouble(edtInputA.getText().toString());
            double numB = Double.parseDouble(edtInputB.getText().toString());
            double numC = Double.parseDouble(edtInputC.getText().toString());
            String rs = calculate(numA, numB, numC);
            txtCalculateResult.setText(rs);
        });
        btnContinue.setOnClickListener(v -> {
            txtCalculateResult.setText("");
            edtInputC.setText("");
            edtInputB.setText("");
            edtInputA.setText("");
            edtInputA.requestFocus();
        });

        btnExit.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private String calculate(double numA, double numB, double numC) {
        DecimalFormat dcf = new DecimalFormat("#.00");
        if(numA == 0) {
            if(numB == 0) {
                if(numC == 0) {
                    return "PT vô số nghiệm";
                } else {
                    return "PT vô nghiệm";
                }
            } else {
                return "PT có 1 nghiệm là x = " + dcf.format(-numC/numB);
            }
        } else {
            double delta = Math.pow(numB,2) - 4.0 * numA * numC;
            if(delta < 0) return "PT vô nghiệm";
            else if(delta == 0) return "" + dcf.format(-numB/(2.0*numA));
            else return "PT có 2 nghiệm x1 = "
                        + dcf.format((-numB+Math.sqrt(delta))/(2.0*numA))
                        + "; x2 = "
                        + dcf.format((numB+Math.sqrt(delta))/(2.0*numA));
        }
    }
}