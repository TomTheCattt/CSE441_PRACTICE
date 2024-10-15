package com.example.prac13_02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Section3 extends AppCompatActivity {

    GridView gridView3;
    Button btnBackTo2;
    customAdapter adapter;
    ArrayList<Info> myList;
    private static String[] name = {"Nguyen Van A", "Nguyen Van B", "Nguyen Van C", "Nguyen Van D", "Nguyen Van E"};
    private static int[] phone = {123456789, 987654321, 123456789, 987654321, 123456789};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_section3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnBackTo2 = findViewById(R.id.btnBackTo2);
        gridView3 = findViewById(R.id.gridView3);
        myList = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            myList.add(new Info(name[i], phone[i]));
        }
        adapter = new customAdapter(this, R.layout.infolist, myList);
        gridView3.setAdapter(adapter);
        btnBackTo2.setOnClickListener(v -> {
            Intent intent = new Intent(this, Section2.class);
            startActivity(intent);
        });

    }
}