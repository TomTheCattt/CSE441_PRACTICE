package com.example.prac04_ex01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.StudentAdapter;
import Model.Student;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private StudentAdapter adapter;
    private List<Student> studentList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewStudents = findViewById(R.id.recyclerViewStudents);
        Button buttonAddStudent = findViewById(R.id.buttonAddStudent);

        recyclerViewStudents.setLayoutManager(new LinearLayoutManager(this));
        studentList = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://android-ex-01-default-rtdb.asia-southeast1.firebasedatabase.app");
        databaseReference = database.getReference("sinhvien");

        checkFirebaseConnection(database);

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

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Student student = dataSnapshot.getValue(Student.class);
                        if (student != null) {
                            studentList.add(student);
                        }
                    }
                } else {
                    Log.d(TAG, "No data available in Firebase");
                    Toast.makeText(MainActivity.this, "No data available", Toast.LENGTH_SHORT).show();
                    initializeSampleData();
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(MainActivity.this, "Failed to load data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        buttonAddStudent.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditStudentActivity.class);
            startActivity(intent);
        });
    }

    private void checkFirebaseConnection(FirebaseDatabase database) {
        DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = Boolean.TRUE.equals(snapshot.getValue(Boolean.class));
                if (connected) {
                    Log.d(TAG, "Connected to Firebase Realtime Database");
                } else {
                    Log.d(TAG, "Disconnected from Firebase Realtime Database");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Listener was cancelled", error.toException());
            }
        });
    }

    private void deleteStudent(String mssv) {
        databaseReference.child(mssv).removeValue()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Student deleted successfully");
                    Toast.makeText(MainActivity.this, "Student deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Failed to delete student", e);
                    Toast.makeText(MainActivity.this, "Failed to delete student: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void initializeSampleData() {
        Map<String, Object> students = new HashMap<>();

        students.put("mssv1", new Student("mssv1", "Nguyễn Văn A", "K65CA", 8.5));
        students.put("mssv2", new Student("mssv2", "Trần Thị B", "K64CB", 7.0));
        students.put("mssv3", new Student("mssv3", "Lê Văn C", "K66CC", 9.0));

        databaseReference.setValue(students)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Sample data initialized successfully");
                    Toast.makeText(MainActivity.this, "Sample data initialized", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Failed to initialize sample data", e);
                    Toast.makeText(MainActivity.this, "Failed to initialize sample data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}


