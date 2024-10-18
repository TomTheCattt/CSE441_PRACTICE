package com.example.ex14;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtInputA, edtInputB;
    Button btnPlus;
    ListView lv;
    ArrayList<String> list;
    ArrayAdapter<String> myArray;

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
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnPlus.setOnClickListener(v -> {
            int a = Integer.parseInt(edtInputA.getText().toString());
            int b = Integer.parseInt(edtInputB.getText().toString());
            String c = a + " " + "+" + " " + b + " " + "=" + " " + (a + b);
            list.add(c);
            myArray.notifyDataSetChanged();
            edtInputA.setText("");
            edtInputB.setText("");
        });
    }

    private void addControl(){
        edtInputA = findViewById(R.id.edtInputA);
        edtInputB = findViewById(R.id.edtInputB);
        btnPlus = findViewById(R.id.btnPlus);
        lv = findViewById(R.id.lv);
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Calculator");
        tabHost.addTab(tab1);
        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("History");
        tabHost.addTab(tab2);
        list = new ArrayList<>();
        myArray = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(myArray);
    }
}