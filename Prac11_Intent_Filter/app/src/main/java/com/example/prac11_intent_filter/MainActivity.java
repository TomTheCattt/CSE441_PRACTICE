package com.example.prac11_intent_filter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Uri data = intent.getData();

        try {
            WebView webView = findViewById(R.id.webView);
            webView.loadUrl(data.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}