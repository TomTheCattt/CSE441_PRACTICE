package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.databinding.StudentListFragmentBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

import Model.Student;

public class StudentListFragment extends Fragment implements
        StudentAdapter.OnStudentClickListener {

    private StudentListFragmentBinding binding;
    private List<Student> allStudents;
    private List<Student> filteredStudents;
    private StudentAdapter adapter;
    private MaterialToolbar toolbar;
    private String currentSearchQuery = "";

    enum SortCriteria {
        NAME, ID, GPA
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = StudentListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
        setupToolbar();
        Log.d("Toolbar", "" + toolbar);
    }

    @Override
    public void onOptionItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sort_by_alpha) {
            sortStudents();
            Toast.makeText(getActivity(), "Tapped", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupToolbar() {
        toolbar = new MaterialToolbar(requireContext());
        toolbar.inflateMenu(R.menu.menu_main);

        MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                currentSearchQuery = newText;
                filterStudents();
                return true;
            }
        });

        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_sort_by_alpha) {
                Toast.makeText(requireContext(), "Sort clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }

    void sortStudents() {
        filteredStudents.sort((s1, s2) -> {
            switch (SortCriteria.NAME) {
                case NAME:
                    return s1.getFull_name().getLast().compareToIgnoreCase(s2.getFull_name().getLast());
                case ID:
                    return s1.getId().compareToIgnoreCase(s2.getId());
                case GPA:
                    return Double.compare(s2.getGpa(), s1.getGpa()); // Descending order for GPA
                default:
                    return 0;
            }
        });
        adapter.notifyDataSetChanged();
    }

    private void setupRecyclerView() {
        allStudents = new ArrayList<>();
        filteredStudents = new ArrayList<>();
        RecyclerView recyclerView = binding.recyclerView;

        adapter = new StudentAdapter(filteredStudents, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.addItemDecoration(
                new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        refreshStudentList();
    }

    private void refreshStudentList() {
        allStudents.clear();
        allStudents.addAll(JsonUtils.readStudentsFromJson(requireContext()));
        filterStudents();
        updateEmptyState();
    }

    private void filterStudents() {
        filteredStudents.clear();

        String currentSearchQuery = "";
        filteredStudents.addAll(allStudents);

        updateEmptyState();
        adapter.notifyDataSetChanged();
    }

    private void updateEmptyState() {
        if (filteredStudents.isEmpty()) {
            binding.emptyStateTextView.setVisibility(View.VISIBLE);
            binding.recyclerView.setVisibility(View.GONE);
        } else {
            binding.emptyStateTextView.setVisibility(View.GONE);
            binding.recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStudentClick(Student student) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("student", student);
        NavHostFragment.findNavController(StudentListFragment.this)
                .navigate(R.id.action_studentListFragment_to_studentDetailFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}