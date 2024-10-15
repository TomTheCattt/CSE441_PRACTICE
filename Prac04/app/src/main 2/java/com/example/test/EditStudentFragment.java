package com.example.test;

import android.app.AlertDialog;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.test.databinding.EditStudentFragmentBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import Model.Student;

public class EditStudentFragment extends Fragment {

    EditStudentFragmentBinding binding;
    Spinner citySpinner, majorSpinner, yearSpinner;
    EditText edtID, edtFullName, edtBirthDate, edtEmail, edtGpa;
    RadioButton male, female, other;
    RadioGroup genderGroup;
    Button done;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EditStudentFragmentBinding.inflate(inflater, container, false);
        bindViews();
        setUpSpinner();
        setUpView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        done.setOnClickListener(view1 -> {
            if (validateInput()) {
                saveUpdatedStudent();
            }
        });
    }

    private void saveUpdatedStudent() {
        try {
            Student updatedStudent = getStudentNewInfo();
            JsonUtils.updateStudentInJson(requireContext(), updatedStudent);
            showSuccessAlert();
        } catch (Exception e) {
            showErrorAlert("Error updating student: " + e.getMessage());
        }
    }

    private void showSuccessAlert() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Success")
                .setMessage("Student has been updated successfully!")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    NavHostFragment.findNavController(EditStudentFragment.this)
                            .navigateUp();
                })
                .show();
    }

    private void showErrorAlert(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
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

    private void setUpSpinner() {
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

    private void bindViews() {
        edtID = binding.editStudent.findViewById(R.id.edtEditID);
        edtFullName = binding.editStudent.findViewById(R.id.edtEditName);
        edtBirthDate = binding.editStudent.findViewById(R.id.edtEditBirthDate);
        citySpinner = binding.editStudent.findViewById(R.id.editCitySpinner);
        genderGroup = binding.editStudent.findViewById(R.id.editGenderGroupBtn);
        edtEmail = binding.editStudent.findViewById(R.id.edtEditEmail);
        majorSpinner = binding.editStudent.findViewById(R.id.edtEditMajorSpinner);
        edtGpa = binding.editStudent.findViewById(R.id.edtEditGPA);
        yearSpinner = binding.editStudent.findViewById(R.id.edtEditYearSpinner);
        male = binding.editStudent.findViewById(R.id.editMaleButton);
        female = binding.editStudent.findViewById(R.id.editFemaleButton);
        other = binding.editStudent.findViewById(R.id.editOtherButton);
        done = binding.editStudent.findViewById(R.id.btnDone);
    }

    private void setUpView() {
        Bundle bundle = getArguments();

        if (bundle != null) {
            Student student = bundle.getParcelable("student");

            // Use the student object, for example, to display the details
            if (student != null) {
                edtID.setText("" + student.getId());
                edtFullName.setText("" + student.getFull_name().getFirst() + " " + student.getFull_name().getMidd() + " " + student.getFull_name().getLast());
                edtBirthDate.setText("" + student.getBirth_date());

// Set citySpinner selection
                ArrayAdapter<String> cityAdapter = (ArrayAdapter<String>) citySpinner.getAdapter();
                if (cityAdapter != null) {
                    int cityPosition = cityAdapter.getPosition(student.getAddress());
                    citySpinner.setSelection(cityPosition);
                }

// Set gender selection for genderGroup
                String gender = student.getGender();
                if (gender.equals("Male")) {
                    genderGroup.check(R.id.editMaleButton);
                } else if (gender.equals("Female")) {
                    genderGroup.check(R.id.editFemaleButton);
                } else if (gender.equals("Other")) {
                    genderGroup.check(R.id.editOtherButton);
                } else {
                    genderGroup.clearCheck(); // If gender is not set or unknown, you can clear the selection
                }

                edtEmail.setText("" + student.getEmail());

// Set majorSpinner selection
                ArrayAdapter<String> majorAdapter = (ArrayAdapter<String>) majorSpinner.getAdapter();
                if (majorAdapter != null) {
                    int majorPosition = majorAdapter.getPosition(student.getMajor());
                    majorSpinner.setSelection(majorPosition);
                }

// Set GPA
                edtGpa.setText("" + student.getGpa());

// Set yearSpinner selection
                ArrayAdapter<String> yearAdapter = (ArrayAdapter<String>) yearSpinner.getAdapter();
                if (yearAdapter != null) {
                    int yearPosition = yearAdapter.getPosition(String.valueOf(student.getYear()));
                    yearSpinner.setSelection(yearPosition);
                }

            }
        }
    }

    private Student getStudentNewInfo() {
        String id = edtID.getText().toString();
        String fullName = edtFullName.getText().toString();
        String birthDate = edtBirthDate.getText().toString();
        String email = edtEmail.getText().toString();
        String gpa = edtGpa.getText().toString();
        String city = citySpinner.getSelectedItem().toString();
        String major = majorSpinner.getSelectedItem().toString();
        String year = yearSpinner.getSelectedItem().toString();
        String gender = "";
        if (male.isChecked()) {
            gender = "Male";
        } else if (female.isChecked()) {
            gender = "Female";
        }
        if (other.isChecked()) {
            gender = "Other";
        }
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

        Student std = new Student(id, stdFullName, birthDate, email, gpa, city, major, Integer.parseInt(year), Integer.parseInt(gender));
        return std;
    }
}
