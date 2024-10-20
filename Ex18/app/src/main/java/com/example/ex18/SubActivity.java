package com.example.ex18;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {
    private TextView txtMaso, txtSongName, txtLyric, txtArtist;
    private ImageButton favoriteToggle;
    private String songId;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        initializeViews();
        loadSongDetails();
        setupFavoriteToggle();
    }

    private void initializeViews() {
        txtMaso = findViewById(R.id.txtMaso);
        txtSongName = findViewById(R.id.txtSongName);
        txtLyric = findViewById(R.id.txtLyric);
        txtArtist = findViewById(R.id.txtArtist);
        favoriteToggle = findViewById(R.id.favoriteToggle);
    }

    private void loadSongDetails() {
        Bundle extras = getIntent().getBundleExtra("package");
        if (extras != null) {
            songId = extras.getString("id");
            txtMaso.setText(songId);

            try (Cursor c = MainActivity.db.rawQuery("SELECT * FROM song WHERE id=?", new String[]{songId})) {
                if (c.moveToFirst()) {
                    txtSongName.setText(c.getString(2));
                    txtLyric.setText(c.getString(3));
                    txtArtist.setText(c.getString(4));
                    isFavorite = c.getInt(6) == 1;
                    updateFavoriteButtonState();
                }
            }
        }
    }

    private void setupFavoriteToggle() {
        favoriteToggle.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            updateFavoriteStatus();
        });
    }

    private void updateFavoriteStatus() {
        ContentValues values = new ContentValues();
        values.put("like", isFavorite ? 1 : 0);
        MainActivity.db.update("song", values, "id=?", new String[]{songId});
        updateFavoriteButtonState();
    }

    private void updateFavoriteButtonState() {
        favoriteToggle.setImageResource(isFavorite ? R.drawable.like : R.drawable.unlike);
    }
}