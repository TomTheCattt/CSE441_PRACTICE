package com.example.prac09;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ImageButton imageBtnPlay, imageBtnStop;
    Boolean flag = true;

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

        imageView = findViewById(R.id.imageView);
        imageBtnPlay = findViewById(R.id.imageBtnPlay);
        imageBtnStop = findViewById(R.id.imageBtnStop);

        imageBtnPlay.setImageResource(R.drawable.play_image);
        imageBtnStop.setImageResource(R.drawable.stop_image);
        imageView.setImageResource(R.drawable.image);

        imageBtnPlay.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
            if(flag == true) {
                imageBtnPlay.setImageResource(R.drawable.stop_image);
                flag = false;
            } else {
                imageBtnPlay.setImageResource(R.drawable.play_image);
                flag = true;
            }
        });

        imageBtnStop.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
            imageBtnPlay.setImageResource(R.drawable.play_image);
            flag = true;
        });
    }
}