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

public class Section2 extends AppCompatActivity {

    public static String[] name = {"Iphone", "Samsung", "Xiaomi", "Oppo", "Vivo", "Nokia"};
    public static int[] image = {R.drawable.iphone, R.drawable.samsung, R.drawable.xiaomi, R.drawable.oppo, R.drawable.vivo, R.drawable.nokia};
    GridView gridView;
    arrayAdapter adapter;
    ArrayList<Image> myList;
    Button btnBackToMain, btnSection3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_section2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gridView3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.gridView);
        myList = new ArrayList<Image>();
        adapter = new arrayAdapter(this, R.layout.listitem, myList);
        for (int i = 0; i < name.length; i++) {
            myList.add(new Image(name[i], image[i]));
        }
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, SubSection2.class);
            Bundle bundle = new Bundle();
            bundle.putInt("image", position);
            intent.putExtras(bundle);
            startActivity(intent);
        });

        btnSection3 = findViewById(R.id.btnSection3);
        btnBackToMain = findViewById(R.id.btnBackToMain);
        btnSection3.setOnClickListener(v -> {
            Intent intent = new Intent(this, Section3.class);
            startActivity(intent);
        });
        btnBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


    }
}