package com.example.prac02;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import Model.User;

public class MainActivity extends AppCompatActivity {

    TextView txtViewModel, txtStatus;
    EditText edtID, edtFullName, edtBirthDate, edtSalary;
    Button btnSubmit;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtViewModel = findViewById(R.id.txtViewModel);
        txtStatus = findViewById(R.id.txtStatus);
        edtID = findViewById(R.id.edtID);
        edtFullName = findViewById(R.id.edtFullName);
        edtBirthDate = findViewById(R.id.edtBirthDate);
        edtSalary = findViewById(R.id.edtSalary);
        btnSubmit = findViewById(R.id.btnSubmit);
        userList = new ArrayList<>();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkAllFieldsFilled()) {
                    txtStatus.setText("All field filled but haven't clicked submit yet.");
                } else {
                    txtStatus.setText("Haven't filled all field.");
                }
            }
        };

        edtID.addTextChangedListener(textWatcher);
        edtFullName.addTextChangedListener(textWatcher);
        edtBirthDate.addTextChangedListener(textWatcher);
        edtSalary.addTextChangedListener(textWatcher);

        btnSubmit.setOnClickListener(v -> {
            String ID = edtID.getText().toString();
            String fullName = edtFullName.getText().toString();
            String birthDate = edtBirthDate.getText().toString();
            String salary = edtSalary.getText().toString();

            if (checkAllFieldsFilled()) {
                userList.add(new User(ID, fullName, birthDate, salary));
                txtStatus.setText("Submit button clicked.");
            }

            StringBuilder userInfo = new StringBuilder();
            if (!userList.isEmpty()) {
                for (User user : userList) {
                    userInfo.append(user.getId()).append("-")
                            .append(user.getFullName()).append("-")
                            .append(user.getBirthDate()).append("-")
                            .append(user.getSalary()).append("\n");
                }
                txtViewModel.setText(userInfo.toString());
            }

            if (userList.size() > 1) {
                txtStatus.setText("After added a few users.");
            } else if (userList.size() == 1) {
                txtStatus.setText("Submit button clicked.");
            }

            edtID.setText("");
            edtFullName.setText("");
            edtBirthDate.setText("");
            edtSalary.setText("");
        });
    }

    private Boolean checkAllFieldsFilled() {
        return !edtID.getText().toString().isEmpty()
                && !edtFullName.getText().toString().isEmpty()
                && !edtBirthDate.getText().toString().isEmpty()
                && !edtSalary.getText().toString().isEmpty();
    }

}