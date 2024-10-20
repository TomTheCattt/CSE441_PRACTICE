package com.example.prac04_ex01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import Model.Student;

public class AddEditStudentActivity extends AppCompatActivity {

    private static final String TAG = "AddEditStudentActivity";
    private EditText editTextName, editTextMSSV, editTextClass, editTextGPA;
    private FirebaseFirestore db;
    private String currentMSSV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_student);

        editTextName = findViewById(R.id.editTextName);
        editTextMSSV = findViewById(R.id.editTextMSSV);
        editTextClass = findViewById(R.id.editTextClass);
        editTextGPA = findViewById(R.id.editTextGPA);
        Button buttonSave = findViewById(R.id.buttonSave);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        if (intent.hasExtra("MSSV")) {
            currentMSSV = intent.getStringExtra("MSSV");
            editTextName.setText(intent.getStringExtra("NAME"));
            editTextMSSV.setText(currentMSSV);
            editTextClass.setText(intent.getStringExtra("CLASS"));
            editTextGPA.setText(intent.getStringExtra("GPA"));
            buttonSave.setText("Sửa");
            editTextMSSV.setEnabled(false);
        } else {
            buttonSave.setText("Thêm");
        }

        buttonSave.setOnClickListener(v -> saveStudent());
    }

    private void saveStudent() {
        String name = editTextName.getText().toString().trim();
        String mssv = editTextMSSV.getText().toString().trim();
        String className = editTextClass.getText().toString().trim();
        String gpaString = editTextGPA.getText().toString().trim();

        if (name.isEmpty() || mssv.isEmpty() || className.isEmpty() || gpaString.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double gpa = Double.parseDouble(gpaString);

            Student student = new Student(mssv, name, className, gpa);

            if (currentMSSV != null) {
                db.collection("sinhvien").document(currentMSSV)
                        .set(student)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "Student updated successfully");
                            Toast.makeText(AddEditStudentActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(TAG, "Error updating student", e);
                            Toast.makeText(AddEditStudentActivity.this, "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            } else {
                db.collection("sinhvien").document(mssv)
                        .set(student)
                        .addOnSuccessListener(aVoid -> {
                            Log.d(TAG, "Student added successfully");
                            Toast.makeText(AddEditStudentActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Log.w(TAG, "Error adding student", e);
                            Toast.makeText(AddEditStudentActivity.this, "Lỗi thêm: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        } catch (NumberFormatException e) {
            Log.w(TAG, "Invalid GPA input", e);
            Toast.makeText(this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }
}