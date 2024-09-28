package com.example.prac03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    TextView txtCountryNameDetail,
             txtCountryCapitalDetail,
             txtCountryPopulationDetail,
             txtCountryAreaDetail,
             txtCountryDensityDetail,
             txtWorldShareDetail;
    ImageView nationalFlag;
    Button btnBack;

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

        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");
        String countryCapital = intent.getStringExtra("countryCapital");
        String countryPopulation = intent.getStringExtra("countryPopulation");
        String countryArea = intent.getStringExtra("countryArea");
        String countryDensity = intent.getStringExtra("countryDensity");
        String countryWorldShare = intent.getStringExtra("countryWorldShare");
        int countryFlag = intent.getIntExtra("countryFlag", 1);

        txtCountryNameDetail = findViewById(R.id.txtCountryNameDetail);
        txtCountryCapitalDetail = findViewById(R.id.txtCountryCapitalDetail);
        txtCountryPopulationDetail = findViewById(R.id.txtCountryPopulationDetail);
        txtCountryAreaDetail = findViewById(R.id.txtCountryAreaDetail);
        txtCountryDensityDetail = findViewById(R.id.txtCountryDensityDetail);
        txtWorldShareDetail = findViewById(R.id.txtWorldShareDetail);
        nationalFlag = findViewById(R.id.nationalFlag);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v-> {
            Intent i = new Intent(MainActivity2.this, MainActivity.class);
            startActivity(i);
        });

        txtCountryNameDetail.setText("Nation: " + countryName);
        txtCountryCapitalDetail.setText("Capital: " + countryCapital);
        txtCountryAreaDetail.setText("Area: " + countryArea + " Km2");
        txtCountryDensityDetail.setText("Density: " + countryDensity + " people/Km2");
        txtCountryPopulationDetail.setText("Population: " + countryPopulation + " million people");
        txtWorldShareDetail.setText("World share: " + countryWorldShare + " %");
        nationalFlag.setImageResource(countryFlag);
    }
}