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

public class OtherChildExActivity extends AppCompatActivity {

    EditText edtNumAReceived, edtNumBReceived;
    Button btnReturnSum, btnReturnMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_child_ex);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtNumAReceived = findViewById(R.id.edtNumAReceived);
        edtNumBReceived = findViewById(R.id.edtNumBReceived);
        btnReturnMinus = findViewById(R.id.btnReturnMinus);
        btnReturnSum = findViewById(R.id.btnReturnSum);

        Intent intent = getIntent();
        double numA = intent.getDoubleExtra("numA", 0);
        double numB = intent.getDoubleExtra("numB", 0);
        edtNumAReceived.setText("" + numA);
        edtNumBReceived.setText("" + numB);

        btnReturnSum.setOnClickListener(v->{
            Intent intent1 = new Intent(this, OtherMainExActivity.class);
            intent1.putExtra("rs", calSum(numA, numB));
            setResult(RESULT_OK, intent1);
            finish();
        });

        btnReturnMinus.setOnClickListener(v->{
            Intent intent1 = new Intent(this, OtherMainExActivity.class);
            intent1.putExtra("rs", calMinus(numA, numB));
            setResult(RESULT_OK, intent1);
            finish();
        });
    }

    private double calSum(double numA, double numB) {
        return numA + numB;
    }

    private double calMinus(double numA, double numB) {
        return numA - numB;
    }
}