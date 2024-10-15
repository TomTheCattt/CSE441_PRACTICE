package com.example.prac12;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class Section2 extends AppCompatActivity {

    EditText edtInput, edtMin, edtHour;
    Button btnAddWork, btnBackToSection1, btnSection3;
    ArrayList<String> arrayWork;
    ArrayAdapter<String> arrayAdapter;
    ListView listView2;
    TextView txtRs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_section2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtInput = findViewById(R.id.edtInput);
        edtMin = findViewById(R.id.edtMin);
        edtHour = findViewById(R.id.edtHour);
        btnAddWork = findViewById(R.id.btnAddWork);
        btnBackToSection1 = findViewById(R.id.btnBackToSection1);
        btnSection3 = findViewById(R.id.btnSection3);
        listView2 = findViewById(R.id.listView2);
        txtRs = findViewById(R.id.txtRs);

        SharedPreferences sharedPReferences = Section2.this.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPReferences.edit();
        Map<String, ?> allEntries = sharedPReferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            arrayWork.add(entry.getValue().toString());
        }

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayWork);
        listView2.setAdapter(arrayAdapter);

        getCurrentDate();

        btnAddWork.setOnClickListener(v-> {
            if(edtInput.getText().toString().equals("") || edtHour.getText().toString().equals("") || edtMin.getText().toString().equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Info Missing");
                builder.setMessage("Please fill all the fields");
                builder.setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            } else {
                String str = edtInput.getText().toString() + " - " + edtHour.getText().toString() + ":" + edtMin.getText().toString();
                arrayWork.add(str);
                arrayAdapter.notifyDataSetChanged();
                edtInput.setText("");
                edtHour.setText("");
                edtMin.setText("");
                editor.putString("string", edtInput.getText().toString() + " - " + edtHour.getText().toString() + ":" + edtMin.getText().toString());
                editor.apply();
            }
        });
        //update function to delete item when user click on item
        listView2.setOnItemClickListener((parent, view, position, id) -> {
            arrayWork.remove(position);
            arrayAdapter.notifyDataSetChanged();
        });

        btnBackToSection1.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        btnSection3.setOnClickListener(v -> {
            Intent intent = new Intent(this, Section3.class);
            startActivity(intent);
        });
    }

    private void getCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");
        txtRs.setText("Today: " + df.format(currentDate));
    }
}