package com.example.prac03;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import Model.Country;

public class MainActivity extends AppCompatActivity {

    RecyclerView myRecyclerView;
    CountryAdapter adapter;

    private List<Country> countries;

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

        myRecyclerView = findViewById(R.id.myRecyclerView);

        countries = new ArrayList<>();
        countries.add(new Country("Viet Nam","Ha Noi","100", "331,212", "301", "1,25", R.drawable.vn_flag));
        countries.add(new Country("England","London","67", "243,610", "275", "0.84", R.drawable.uk_flag));
        countries.add(new Country("United States","Washington DC","336", "9,833,517", "36", "4,25", R.drawable.us_flag));

        adapter = new CountryAdapter(countries);
        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}