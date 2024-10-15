package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.test.databinding.StudentDetailFragmentBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Objects;

import Model.Student;

public class StudentDetailFragment extends Fragment {

    StudentDetailFragmentBinding binding;

    MaterialToolbar toolbar;

    private Student student;

    MaterialToolbar setToolbar(StudentDetailFragmentBinding binding, MaterialToolbar toolbar) {
        this.binding = binding;
        this.toolbar = toolbar;
        return toolbar;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = StudentDetailFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView();
        MaterialToolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if(id == R.id.action_edit) {
                navigateToEditStudent();
                return true;
            } else if(id == R.id.action_delete) {
                deleteStudent();
                return true;
            }
            return  false;
        });
    }

    private void setUpView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            student = bundle.getParcelable("student");

            // Use the student object, for example, to display the details
            if (student != null) {
                TextView studentID = binding.nestedScrollView.findViewById(R.id.txtStudentID);
                TextView studentName = binding.nestedScrollView.findViewById(R.id.txtFullName);
                TextView studentBirthDate = binding.nestedScrollView.findViewById(R.id.txtBirthDate);
                TextView studentAddress = binding.nestedScrollView.findViewById(R.id.txtAddress);
                TextView studentGender = binding.nestedScrollView.findViewById(R.id.txtGender);
                TextView studentEmail = binding.nestedScrollView.findViewById(R.id.txtEmail);
                TextView studentMajor = binding.nestedScrollView.findViewById(R.id.txtMajor);
                TextView studentGPA = binding.nestedScrollView.findViewById(R.id.txtGpa);
                TextView studentYear = binding.nestedScrollView.findViewById(R.id.txtYear);
                studentID.setText(student.getId());
                studentName.setText(student.getFull_name().getFirst() + " " + student.getFull_name().getMidd() + " " + student.getFull_name().getLast());
                studentBirthDate.setText(student.getBirth_date());
                studentAddress.setText(student.getAddress());
                studentGender.setText(student.getGender());
                studentEmail.setText(student.getEmail());
                studentMajor.setText(student.getMajor());
                studentGPA.setText(String.valueOf(student.getGpa()));
                studentYear.setText(String.valueOf(student.getYear()));
            }
        }
    }

    public Student getStudent() {
        return student;
    }

    private void navigateToEditStudent() {
        if (student != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("student", getStudent());  // Pass the student object to the next fragment
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_studentDetailFragment_to_editStudentFragment, bundle);
        }
    }

    private void deleteStudent() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (JsonUtils.deleteStudentFromJson(requireContext(), student.getId())) {
                        showDeleteResultDialog(true);
                    } else {
                        showDeleteResultDialog(false);
                    }
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showDeleteResultDialog(boolean success) {
        AlertDialog.Builder resultDialog = new AlertDialog.Builder(requireContext());
        resultDialog.setTitle(success ? "Delete Successful" : "Delete Failed")
                .setMessage(success ? "The student has been deleted successfully."
                        : "An error occurred while trying to delete the student.")
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.dismiss();
                    if (success) {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                        navController.navigateUp();
                    }
                })
                .show();
    }

}
