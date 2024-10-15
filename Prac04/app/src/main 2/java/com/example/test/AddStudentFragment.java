package com.example.test;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.test.databinding.AddStudentFragmentBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import Model.Student;

public class AddStudentFragment extends Fragment {

    AddStudentFragmentBinding binding;
    Spinner citySpinner, majorSpinner, yearSpinner;
    EditText edtID, edtFullName, edtBirthDate, edtEmail, edtGpa;
    RadioButton male, female, other;
    Button add;
    RadioGroup genderGroup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AddStudentFragmentBinding.inflate(inflater, container, false);
        bindViews();
        setSpinnerAdapter();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    saveStudent();
                }
            }
        });
    }

    private boolean validateInput() {
        // Validate ID
        String id = edtID.getText().toString().trim();
        if (id.isEmpty()) {
            showErrorAlert("ID cannot be empty");
            return false;
        }

        // Validate Full Name
        String fullName = edtFullName.getText().toString().trim();
        if (fullName.isEmpty()) {
            showErrorAlert("Full name cannot be empty");
            return false;
        }

        // Validate Email
        String email = edtEmail.getText().toString().trim();
        if (email.isEmpty()) {
            showErrorAlert("Email cannot be empty");
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorAlert("Invalid email format");
            return false;
        }

        // Validate GPA
        String gpaStr = edtGpa.getText().toString().trim();
        try {
            double gpa = Double.parseDouble(gpaStr);
            if (gpa < 0 || gpa > 4.0) {
                showErrorAlert("GPA must be between 0.0 and 4.0");
                return false;
            }
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid GPA format");
            return false;
        }

        // Validate Birth Date
        String birthDate = edtBirthDate.getText().toString().trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(birthDate);
        } catch (ParseException e) {
            showErrorAlert("Invalid birth date format. Use dd/MM/yyyy");
            return false;
        }

        return true;
    }

    private void saveStudent() {
        try {
            Student student = createStudentObject();
            JsonUtils.saveStudentToJson(requireContext(), student);
            showSuccessAlert();
        } catch (Exception e) {
            showErrorAlert("Error saving student: " + e.getMessage());
        }
    }

    private Student createStudentObject() {
        String id = edtID.getText().toString().trim();
        String fullName = edtFullName.getText().toString().trim();
        String birthDate = edtBirthDate.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        double gpa = Double.parseDouble(edtGpa.getText().toString().trim());
        String city = citySpinner.getSelectedItem().toString();
        String major = majorSpinner.getSelectedItem().toString();
        int year = Integer.parseInt(yearSpinner.getSelectedItem().toString());

        String gender = "";
        if (male.isChecked()) gender = "Male";
        else if (female.isChecked()) gender = "Female";
        else if (other.isChecked()) gender = "Other";

        String[] nameParts = fullName.split("\\s+");
        String first = "";
        String mid = "";
        String last = "";
        if (nameParts.length > 0) {
            first = nameParts[0];  // The first word is the first name
        }

        if (nameParts.length > 1) {
            last = nameParts[nameParts.length - 1];  // The last word is the last name
        }

        if (nameParts.length > 2) {
            // Everything between the first and last word is the middle name(s)
            StringBuilder midBuilder = new StringBuilder();
            for (int i = 1; i < nameParts.length - 1; i++) {
                midBuilder.append(nameParts[i]);
                if (i < nameParts.length - 2) {
                    midBuilder.append(" ");  // Add space between middle names
                }
            }
            mid = midBuilder.toString();
        }

        Student.FullName stdFullName = new Student.FullName(first, mid, last);
        return new Student(id, stdFullName, gender, birthDate, email, city, major, gpa, year);
    }

    private void showErrorAlert(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showSuccessAlert() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Success")
                .setMessage("Student has been added successfully!")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    // Navigate back to parent fragment
                    requireActivity().getSupportFragmentManager().popBackStack();
                })
                .show();
    }

    private void bindViews() {
        citySpinner = binding.citySpinner;
        majorSpinner = binding.majorSpinner;
        yearSpinner = binding.yearSpinner;
        edtID = binding.edtID;
        edtFullName = binding.edtFullName;
        edtBirthDate = binding.edtBirthDate;
        edtEmail = binding.edtEmail;
        edtGpa = binding.edtGpa;
        male = binding.radioButton;
        female = binding.radioButton2;
        other = binding.radioButton3;
        add = binding.button;
        genderGroup = binding.genderGroup;
    }

    private void setSpinnerAdapter() {
        ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.city_array,
                android.R.layout.simple_spinner_item
        );
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);
        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.major_array,
                android.R.layout.simple_spinner_item
        );
        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        majorSpinner.setAdapter(majorAdapter);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.year_array,
                android.R.layout.simple_spinner_item
        );
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
    }

    private void resetFields() {
        edtID.setText("");
        edtFullName.setText("");
        edtBirthDate.setText("");
        edtEmail.setText("");
        edtGpa.setText("");

        // Reset spinners to the first position
        citySpinner.setSelection(0);
        majorSpinner.setSelection(0);
        yearSpinner.setSelection(0);

        // Reset radio group
        genderGroup.clearCheck();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
