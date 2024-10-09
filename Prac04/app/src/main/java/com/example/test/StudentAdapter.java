package com.example.test;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Model.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> students;
    private OnStudentClickListener listener;

    public interface OnStudentClickListener {
        void onOptionItemSelected(MenuItem item);

        void onStudentClick(Student student);
    }

    public StudentAdapter(List<Student> students, OnStudentClickListener listener) {
        this.students = students;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student, listener);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {

        private TextView studentId;
        private TextView studentName;
        private TextView studentGpa;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentId = itemView.findViewById(R.id.txtStudentId);
            studentName = itemView.findViewById(R.id.txtFullName);
            studentGpa = itemView.findViewById(R.id.txtStudentGPA);
        }

        public void bind(Student student, OnStudentClickListener listener) {
            studentId.setText(student.getId());
            studentName.setText(student.getFull_name().getFirst() + " " + student.getFull_name().getMidd() + " " + student.getFull_name().getLast());
            studentGpa.setText(String.valueOf(student.getGpa()));
            itemView.setOnClickListener(v -> listener.onStudentClick(student));
        }
    }
}
