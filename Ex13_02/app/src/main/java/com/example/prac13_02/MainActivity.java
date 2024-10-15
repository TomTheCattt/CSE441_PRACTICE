package com.example.prac13_02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String name[] = {"Iphone", "Samsung", "Xiaomi", "Oppo", "Vivo", "Nokia"};
    int image[] = {R.drawable.iphone, R.drawable.samsung, R.drawable.xiaomi, R.drawable.oppo, R.drawable.vivo, R.drawable.nokia};
    ArrayList<phone> myList;
    MyArrayAdapter adapter;
    ListView listView;
    Button btnSection2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.gridView3), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.lv);

        myList = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            myList.add(new phone(name[i], image[i]));
        }
        adapter = new MyArrayAdapter(this, R.layout.layout_listview, myList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, SubActivity.class);
            intent.putExtra("name", name[position]);
            startActivity(intent);
        });

        btnSection2 = findViewById(R.id.btnSection2);
        btnSection2.setOnClickListener(v -> {
            Intent intent = new Intent(this, Section2.class);
            startActivity(intent);
        });
    }
}