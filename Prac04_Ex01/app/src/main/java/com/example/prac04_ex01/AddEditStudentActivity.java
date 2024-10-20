package com.example.prac04_ex01;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.UUID;

import Model.Student;

public class AddEditStudentActivity extends AppCompatActivity {

    private static final String TAG = "AddEditStudentActivity";
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextName, editTextMSSV, editTextClass, editTextGPA;
    private ImageView imageViewAvatar;
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private String currentMSSV;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_add_student);

        editTextName = findViewById(R.id.editTextName);
        editTextMSSV = findViewById(R.id.editTextMSSV);
        editTextClass = findViewById(R.id.editTextClass);
        editTextGPA = findViewById(R.id.editTextGPA);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonChooseImage = findViewById(R.id.buttonChooseImage);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        Intent intent = getIntent();
        if (intent.hasExtra("MSSV")) {
            currentMSSV = intent.getStringExtra("MSSV");
            editTextName.setText(intent.getStringExtra("NAME"));
            editTextMSSV.setText(currentMSSV);
            editTextClass.setText(intent.getStringExtra("CLASS"));
            editTextGPA.setText(intent.getStringExtra("GPA"));
            String avatarUrl = intent.getStringExtra("AVATAR");
            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                Glide.with(this).load(avatarUrl).into(imageViewAvatar);
            }
            buttonSave.setText("Sửa");
            editTextMSSV.setEnabled(false);
        } else {
            buttonSave.setText("Thêm");
        }

        buttonChooseImage.setOnClickListener(v -> openFileChooser());
        buttonSave.setOnClickListener(v -> saveStudent());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(imageViewAvatar);
        }
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

            if (imageUri != null) {
                uploadImage(mssv, name, className, gpa);
            } else {
                saveStudentToFirestore(new Student(mssv, name, className, gpa, null));
            }
        } catch (NumberFormatException e) {
            Log.w(TAG, "Invalid GPA input", e);
            Toast.makeText(this, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImage(String mssv, String name, String className, double gpa) {
        String imageName = UUID.randomUUID().toString();
        StorageReference imageRef = storageRef.child("avatars/" + imageName);

        imageRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            String imageUrl = uri.toString();
                            Student student = new Student(mssv, name, className, gpa, imageUrl);
                            saveStudentToFirestore(student);
                        }))
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error uploading image", e);
                    Toast.makeText(AddEditStudentActivity.this, "Lỗi tải ảnh lên: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveStudentToFirestore(Student student) {
        db.collection("students").document(student.getMssv())
                .set(student)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Student saved successfully");
                    Toast.makeText(AddEditStudentActivity.this, "Lưu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Log.w(TAG, "Error saving student", e);
                    Toast.makeText(AddEditStudentActivity.this, "Lỗi lưu: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}