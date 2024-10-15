package com.example.prac13_02;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class customAdapter extends ArrayAdapter<Info> {
    Activity context;
    int layoutId;
    ArrayList<Info> myList;

    public customAdapter(Activity context, int layoutId, ArrayList<Info> myList) {
        super(context, layoutId, myList);
        this.context = context;
        this.layoutId = layoutId;
        this.myList = myList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        Info info = myList.get(position);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtPhone = convertView.findViewById(R.id.txtPhone);
        txtName.setText(info.getName());
        txtPhone.setText(String.valueOf(info.getPhone()));
        return convertView;
    }
}
