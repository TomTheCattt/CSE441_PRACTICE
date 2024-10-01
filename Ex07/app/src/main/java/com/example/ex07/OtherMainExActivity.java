package com.example.ex07;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OtherMainExActivity extends AppCompatActivity {

    EditText edtSendNumA, edtSendNumB, edtReceiveResult;
    Button btnAskForCalculate, btnReturnToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_other_main_ex);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtSendNumA = findViewById(R.id.edtSendNumA);
        edtSendNumB = findViewById(R.id.edtSendNumB);
        edtReceiveResult = findViewById(R.id.edtReceiveResult);

        ActivityResultLauncher<Intent> startActivityIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        double rs = intent.getDoubleExtra("rs", 0);
                        edtReceiveResult.setText("" + rs);
                    }
                }
        );

        btnAskForCalculate = findViewById(R.id.btnAskForCalculate);
        btnAskForCalculate.setOnClickListener(v->{
            Intent intent = new Intent(OtherMainExActivity.this, OtherChildExActivity.class);
            double numA = Double.parseDouble(edtSendNumA.getText().toString());
            double numB = Double.parseDouble(edtSendNumB.getText().toString());
            if(edtSendNumA.getText().toString().isEmpty()) {
                numA = 0;
            }
            if(edtSendNumB.getText().toString().isEmpty()) {
                numB = 0;
            }
            intent.putExtra("numA", numA);
            intent.putExtra("numB", numB);
            startActivityIntent.launch(intent);
        });

        btnReturnToMain = findViewById(R.id.btnReturnToMain);
        btnReturnToMain.setOnClickListener(v->{
            Intent intent = new Intent(OtherMainExActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}