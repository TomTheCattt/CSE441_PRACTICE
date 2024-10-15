package com.example.test;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.test.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.test.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = binding.toolbar;

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.studentListFragment) {
                toolbar.getMenu().clear(); // Clear previous menu
                getMenuInflater().inflate(R.menu.menu_main, toolbar.getMenu());
                toolbar.setTitle("Student List");
            } else if (destination.getId() == R.id.studentDetailFragment) {
                toolbar.getMenu().clear(); // Clear previous menu
                getMenuInflater().inflate(R.menu.student_detail_menu, toolbar.getMenu()); // Load Student Detail menu
                toolbar.setTitle("Student Details");
            } else if (destination.getId() == R.id.addStudentFragment) {
                toolbar.getMenu().clear(); // Clear previous menu
                getMenuInflater().inflate(R.menu.add_student_menu, toolbar.getMenu()); // Load Add Student menu
                toolbar.setTitle("Add Student");
            } else if (destination.getId() == R.id.editStudentFragment) {
                toolbar.getMenu().clear(); // Clear previous menu
                getMenuInflater().inflate(R.menu.edit_student_menu, toolbar.getMenu()); // Load Edit Student menu
                toolbar.setTitle("Edit Student");
            }
        });

        binding.fab.setOnClickListener(view -> navController.navigate(R.id.action_studentListFragment_to_addStudentFragment));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(this, "Search clicked", Toast.LENGTH_SHORT).show();
            // Handle search action
            return true;
        } else if (id == R.id.action_sort_by_alpha) {
            // Handle sort action
            Toast.makeText(this, "Sort clicked", Toast.LENGTH_SHORT).show();
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
            if (navHostFragment != null) {
                List<Fragment> fragments = navHostFragment.getChildFragmentManager().getFragments();
                if (!fragments.isEmpty()) {
                    Fragment currentFragment = fragments.get(0);  // Get the current fragment
                    if (currentFragment instanceof StudentListFragment) {
                        // Cast to StudentListFragment and perform sorting
                        StudentListFragment studentListFragment = (StudentListFragment) currentFragment;
                        studentListFragment.sortStudents();
                    }
                }
            }
            return true;
        } else if (id == R.id.action_edit) {
            Toast.makeText(this, "Edit clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.action_delete) {
            Toast.makeText(this, "Delete clicked", Toast.LENGTH_SHORT).show();
            // Handle delete action
            return true;
        } else {
            return super.onOptionsItemSelected(item);// Handle unrecognized actions if needed
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}