package com.example.prac04_ex01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import Adapter.StudentAdapter;
import Model.Student;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private StudentAdapter adapter;
    private List<Student> studentList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
        Button buttonAddStudent = findViewById(R.id.buttonAddStudent);

        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        adapter = new StudentAdapter(studentList, new StudentAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Student student = studentList.get(position);
                Intent intent = new Intent(MainActivity.this, AddEditStudentActivity.class);
                intent.putExtra("MSSV", student.getMssv());
                intent.putExtra("NAME", student.getName());
                intent.putExtra("CLASS", student.getClassName());
                intent.putExtra("GPA", String.valueOf(student.getGpa()));
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                String mssv = studentList.get(position).getMssv();
                deleteStudent(mssv);
            }
        });

        recyclerViewStudents.setAdapter(adapter);

        loadStudents();

        buttonAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditStudentActivity.class);
            startActivity(intent);
        });
    }

    private void loadStudents() {
        db.collection("sinhvien")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        studentList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Student student = document.toObject(Student.class);
                            studentList.add(student);
                        }
                        adapter.notifyDataSetChanged();

                        if (studentList.isEmpty()) {
                            Log.d(TAG, "No data available in Firestore");
                            Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                            initializeSampleData();
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                        Toast.makeText(MainActivity.this, "Failed to load data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void deleteStudent(String mssv) {
        db.collection("sinhvien").document(mssv)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Student deleted successfully");
                    Toast.makeText(MainActivity.this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
                    loadStudents(); // Reload the list after deletion
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error deleting student", e);
                    Toast.makeText(MainActivity.this, "Failed to delete student: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void initializeSampleData() {
        List<Student> sampleStudents = new ArrayList<>();
        sampleStudents.add(new Student("mssv1", "Nguyễn Văn A", "K65CA", 8.5));
        sampleStudents.add(new Student("mssv2", "Trần Thị B", "K64CB", 7.0));
        sampleStudents.add(new Student("mssv3", "Lê Văn C", "K66CC", 9.0));

        for (Student student : sampleStudents) {
            db.collection("sinhvien").document(student.getMssv())
                    .set(student)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Sample student added successfully"))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding sample student", e));
        }

        Toast.makeText(MainActivity.this, "Sample data initialized", Toast.LENGTH_SHORT).show();
        loadStudents(); // Reload the list after initialization
    }
}