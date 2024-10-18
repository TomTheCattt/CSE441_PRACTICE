package com.example.ex15;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText edtMaLop, edtTenLop, edtSiSo;
    Button btnInsert, btnDelete, btnUpdate, btnQuerry;
    ListView lv;
    ArrayList<String> myList;
    ArrayAdapter<String> adapter;
    SQLiteDatabase myDB;

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
        edtMaLop = findViewById(R.id.edtMaLop);
        edtTenLop = findViewById(R.id.edtTenLop);
        edtSiSo = findViewById(R.id.edtSiSo);
        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnQuerry = findViewById(R.id.btnQuerry);
        lv = findViewById(R.id.lv);

        myList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, myList);
        lv.setAdapter(adapter);

        myDB = openOrCreateDatabase("qlsinhvien.db", MODE_PRIVATE, null);

        try {
            String sql = "CREATE TABLE IF NOT EXISTS tblop (MaLop TEXT PRIMARY KEY, TenLop TEXT, SiSo INTEGER)";
            myDB.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error", Objects.requireNonNull(e.getMessage()));
        }

        btnInsert.setOnClickListener(v-> {
            String maLop = edtMaLop.getText().toString();
            String tenLop = edtTenLop.getText().toString();
            int siSo = Integer.parseInt(edtSiSo.getText().toString());
            ContentValues values = new ContentValues();
            values.put("MaLop", maLop);
            values.put("TenLop", tenLop);
            values.put("SiSo", siSo);
            String msg = "";
            if(myDB.insert("tblop", null, values) == -1) {
                msg = "Insert Failed";
            } else {
                msg = "Insert Success";
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        btnDelete.setOnClickListener(v-> {
            String maLop = edtMaLop.getText().toString();
            int n = myDB.delete("tblop", "MaLop=?", new String[]{maLop});
            String msg = "";
            if(n == 0) {
                msg = "Delete Failed";
            } else {
                msg = "Delete Success";
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        btnUpdate.setOnClickListener(v-> {
            int siSo = Integer.parseInt(edtSiSo.getText().toString());
            String maLop = edtMaLop.getText().toString();
            ContentValues values = new ContentValues();
            values.put("SiSo", siSo);
            int n = myDB.update("tblop", values, "MaLop=?", new String[]{maLop});
            String msg = "";
            if(n == 0) {
                msg = "Update Failed";
            } else {
                msg = "Update Success";
            }
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        btnQuerry.setOnClickListener(v->{
            myList.clear();
            String sql = "SELECT * FROM tblop";
            Cursor c = myDB.rawQuery(sql, null);
            c.moveToNext();
            String data;
            while(!c.isAfterLast()) {
                data = c.getString(0) + " " + c.getString(1) + " " + c.getInt(2);
                c.moveToNext();
                myList.add(data);
            }
            c.close();
            adapter.notifyDataSetChanged();
        });
    }
}