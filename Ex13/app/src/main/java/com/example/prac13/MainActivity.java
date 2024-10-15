package com.example.prac13;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView edtAuto;
    MultiAutoCompleteTextView multiAuto;
    TextView selection;
    String arr[] = {"Ha Noi", "Hue", "Ha Giang", "Sai Gon", "Hoi An", "Kien Giang", "Lam Dong", "Long Khanh"};
    Button btnSection2;

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

        edtAuto = findViewById(R.id.edtAuto);
        multiAuto = findViewById(R.id.multiAuto);
        selection = findViewById(R.id.selection);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, arr);
        edtAuto.setAdapter(adapter);
        multiAuto.setAdapter(adapter);
        multiAuto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        edtAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selection.setText(selection.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        multiAuto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                selection.setText(selection.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSection2 = findViewById(R.id.btnSection2);
        btnSection2.setOnClickListener(v-> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });
    }
}