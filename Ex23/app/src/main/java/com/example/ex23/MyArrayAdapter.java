package com.example.ex23;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyArrayAdapter extends ArrayAdapter<List> {

    private Activity context;
    private ArrayList<List> arr;
    private int layoutId;

    public MyArrayAdapter(Activity context, int layoutId, ArrayList<List> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.arr = arr;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        List list = arr.get(position);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        imageView.setImageBitmap(list.getImg());
        TextView title = convertView.findViewById(R.id.txtTitle);
        title.setText(list.getTitle());
        TextView info = convertView.findViewById(R.id.txtInfo);
        info.setText(list.getInfo());
        MainActivity.lv.setOnItemClickListener((parent1, view, position1, id) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(list.getLink()));
            context.startActivity(intent);
        });
        return convertView;
    }
}
