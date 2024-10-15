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

public class arrayAdapter extends ArrayAdapter<Image> {
    Activity context;
    ArrayList<Image> myList;
    int layoutId;

    public arrayAdapter(Activity context, int layoutId, ArrayList<Image> myList) {
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
        Image image = myList.get(position);
        TextView textView = convertView.findViewById(R.id.textView2);
        ImageView imageView = convertView.findViewById(R.id.imageView2);
        textView.setText(image.getName());
        imageView.setImageResource(image.getImage());
        return convertView;
    }
}
