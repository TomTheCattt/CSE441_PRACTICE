package com.example.prac13_02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<phone> {

    Activity Context;
    int idLayout;
    ArrayList<phone> myList;

    public MyArrayAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<phone> objects) {
        super(context, resource, objects);
        Context = context;
        idLayout = resource;
        myList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = Context.getLayoutInflater();
        convertView = inflater.inflate(idLayout, null);
        phone myPhone = myList.get(position);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageResource(myPhone.getImage());
        TextView textView = convertView.findViewById(R.id.textView);
        textView.setText(myPhone.getName());
        return convertView;
    }


}
