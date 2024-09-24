package com.example.ex06;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtID, edtMultiLine;
    Button btnSubmit;
    RadioButton radioBtnIntermediate, radioBtnCollege, radioBtnUniversity;
    CheckBox checkBoxNews, checkBoxBooks, checkBoxCode;
    TextView txtError;
    RadioGroup radioGroup;


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

        edtName = findViewById(R.id.edtName);
        edtID = findViewById(R.id.edtID);
        edtMultiLine = findViewById(R.id.multiLineText);
        btnSubmit = findViewById(R.id.btnSubmit);
        checkBoxNews = findViewById(R.id.checkBoxNews);
        checkBoxBooks = findViewById(R.id.checkBoxBooks);
        checkBoxCode = findViewById(R.id.checkBoxCode);
        radioGroup = findViewById(R.id.radioGroup);

        btnSubmit.setOnClickListener(v -> {
            showInformation();
        });
    }

    private void showInformation() {
        String name = edtName.getText().toString();
        if(name.isEmpty()) {
            edtName.requestFocus();
            Toast.makeText(this, "Name field must not empty.", Toast.LENGTH_SHORT).show();
            return;
        } else if(name.trim().length() < 3) {
            edtName.requestFocus();
            edtName.selectAll();
            Toast.makeText(this, "Name need to >=3 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = edtID.getText().toString();
        if(id.length() != 9) {
            edtID.requestFocus();
            edtID.selectAll();
            Toast.makeText(this,"ID have to have 9 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        int degreeIndex = radioGroup.getCheckedRadioButtonId();
        if(degreeIndex == -1) {
            Toast.makeText(this, "Degree can't be none", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioButton = findViewById(degreeIndex);
        String degree = radioButton.getText().toString();
        String hobby = "";
        if(checkBoxCode.isChecked()) {
            hobby += checkBoxCode.getText().toString() + "\n";
        }
        if(checkBoxBooks.isChecked()) {
            hobby += checkBoxBooks.getText().toString() + "\n";
        }
        if(checkBoxNews.isChecked()) {
            hobby += checkBoxNews.getText().toString() + "\n";
        }

        if(hobby.isEmpty()) {
            Toast.makeText(this, "Aww, don't say that you don't have any hobby like this.", Toast.LENGTH_SHORT).show();
            return;
        }

        String additionalInformation = edtMultiLine.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Personal Information");
        builder.setPositiveButton("Close", (dialog, which) -> dialog.cancel());
        String msg = name + "\n";
        msg += id + "\n";
        msg += degree + "\n";
        msg += hobby;
        msg += "---------------\n";
        msg += additionalInformation + "\n";
        msg += "---------------";
        builder.setMessage(msg);
        builder.create().show();
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Question");
        builder.setMessage("Do you want to exit ?");
        builder.setPositiveButton("Yes", (dialog, which) -> finish());
        builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
        builder.create().show();
    }
}