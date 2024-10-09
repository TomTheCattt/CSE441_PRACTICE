package com.example.test;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Model.Student;

public class JsonUtils {
    private static final String FILE_NAME = "students.json";

    public static List<Student> readStudentsFromJson(Context context) {
        List<Student> studentList = new ArrayList<>();
        File file = new File(context.getFilesDir(), FILE_NAME);

        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                Gson gson = new Gson();
                Type studentListType = new TypeToken<List<Student>>() {}.getType();
                studentList = gson.fromJson(br, studentListType);

                // Handle case where json might be empty or null
                if (studentList == null) {
                    studentList = new ArrayList<>();
                }
            } catch (IOException e) {
                Log.e("JSON", "Error reading file", e);
            }
        }

        return studentList;
    }

    public static boolean deleteStudentFromJson(Context context, String studentId) {
        List<Student> studentList = readStudentsFromJson(context);
        boolean wasDeleted = false;

        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getId().equals(studentId)) {
                iterator.remove();
                wasDeleted = true;
                break;
            }
        }

        if (wasDeleted) {
            writeStudentListToJson(context, studentList);
        }

        return wasDeleted;
    }

    public static void saveStudentToJson(Context context, Student student) {
        List<Student> studentList = readStudentsFromJson(context);
        studentList.add(student);
        writeStudentListToJson(context, studentList);
    }

    public static void updateStudentInJson(Context context, Student updatedStudent) {
        List<Student> studentList = readStudentsFromJson(context);

        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId().equals(updatedStudent.getId())) {
                studentList.set(i, updatedStudent);
                break;
            }
        }

        writeStudentListToJson(context, studentList);
    }

    private static void writeStudentListToJson(Context context, List<Student> studentList) {
        File file = new File(context.getFilesDir(), FILE_NAME);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            Gson gson = new Gson();
            gson.toJson(studentList, bw);
        } catch (IOException e) {
            Log.e("JSON", "Error writing file", e);
        }
    }
}
