package com.example.ex18;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import Adapter.myarrayAdapter;
import Model.Item;

public class MainActivity extends AppCompatActivity {

    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DB_NAME = "song.sqlite";
    public static SQLiteDatabase db = null;

    private EditText edtSearch;
    private ListView lv1, lv2, lv3;
    public static myarrayAdapter adapter1, adapter2, adapter3;
    private ArrayList<Item> list1, list2, list3;
    private TabHost tabHost;
    private ImageButton btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processCopy();
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        initializeViews();
        setupSearch();
        setupTabHost();
    }

    private void initializeViews() {
        btnDelete = findViewById(R.id.btnDelete);
        tabHost = findViewById(R.id.tabHost);
        edtSearch = findViewById(R.id.edtSearch);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        adapter1 = new myarrayAdapter(this, R.layout.listitem, list1);
        adapter2 = new myarrayAdapter(this, R.layout.listitem, list2);
        adapter3 = new myarrayAdapter(this, R.layout.listitem, list3);
        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);
        lv3.setAdapter(adapter3);

        btnDelete.setOnClickListener(v -> edtSearch.setText(""));
    }

    private void setupTabHost() {
        tabHost.setup();

        addTab("tab1", R.id.tab1, R.drawable.search);
        addTab("tab2", R.id.tab2, R.drawable.list);
        addTab("tab3", R.id.tab3, R.drawable.favourite);

        tabHost.setOnTabChangedListener(tabId -> {
            if ("tab2".equals(tabId)) {
                loadAllSongs();
            } else if ("tab3".equals(tabId)) {
                loadFavoriteSongs();
            }
        });
    }

    private void addTab(String tag, int contentId, int indicatorDrawableId) {
        TabHost.TabSpec tab = tabHost.newTabSpec(tag);
        tab.setContent(contentId);
        tab.setIndicator("", getResources().getDrawable(indicatorDrawableId));
        tabHost.addTab(tab);
    }

    private void setupSearch() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchSongs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void searchSongs(String query) {
        adapter1.clear();
        if (!query.isEmpty()) {
            try (Cursor c = db.rawQuery("SELECT * FROM song WHERE title LIKE ?", new String[]{"%" + query + "%"})) {
                while (c.moveToNext()) {
                    Item item = new Item(c.getString(0), c.getString(2), c.getInt(6));
                    adapter1.add(item);
                }
            }
        }
        adapter1.notifyDataSetChanged();
    }

    private void loadAllSongs() {
        adapter2.clear();
        try (Cursor c = db.rawQuery("SELECT * FROM song", null)) {
            while (c.moveToNext()) {
                Item item = new Item(c.getString(0), c.getString(2), c.getInt(6));
                adapter2.add(item);
            }
        }
        adapter2.notifyDataSetChanged();
    }

    private void loadFavoriteSongs() {
        adapter3.clear();
        try (Cursor c = db.rawQuery("SELECT * FROM song WHERE favourite = 1", null)) {
            while (c.moveToNext()) {
                Item item = new Item(c.getString(0), c.getString(2), c.getInt(6));
                adapter3.add(item);
            }
        }
        adapter3.notifyDataSetChanged();
    }

    private void processCopy() {
        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) {
            try {
                copyDatabaseFromAsset();
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately, e.g., show an error message to the user
            }
        }
    }

    private void copyDatabaseFromAsset() throws IOException {
        InputStream myInput = getAssets().open(DB_NAME);
        String outFileName = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DB_NAME;
        File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists()) {
            f.mkdirs();
        }

        try (OutputStream myOutput = new FileOutputStream(outFileName)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
        } finally {
            myInput.close();
        }
    }
}