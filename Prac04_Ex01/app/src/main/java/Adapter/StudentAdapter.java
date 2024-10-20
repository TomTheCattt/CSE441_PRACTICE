package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prac04_ex01.R;

import java.util.List;

import Model.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> studentList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public StudentAdapter(List<Student> studentList, OnItemClickListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.nameTextView.setText(student.getName());
        holder.mssvTextView.setText(student.getMssv());
        holder.classTextView.setText(student.getClassName());
        holder.gpaTextView.setText(String.valueOf(student.getGpa()));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, mssvTextView, classTextView, gpaTextView;
        Button editButton, deleteButton;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewName);
            mssvTextView = itemView.findViewById(R.id.textViewMSSV);
            classTextView = itemView.findViewById(R.id.textViewClass);
            gpaTextView = itemView.findViewById(R.id.textViewGPA);
            editButton = itemView.findViewById(R.id.buttonEdit);
            deleteButton = itemView.findViewById(R.id.buttonDelete);

            editButton.setOnClickListener(v -> listener.onEditClick(getAdapterPosition()));
            deleteButton.setOnClickListener(v -> listener.onDeleteClick(getAdapterPosition()));
        }
    }
}

