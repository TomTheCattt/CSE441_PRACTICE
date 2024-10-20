package com.example.ex24;

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

public class MyArrayAdapter extends ArrayAdapter<TyGia> {
    Activity context;
    int resource;
    ArrayList<TyGia> objects;

    public MyArrayAdapter(Activity context, int resource, ArrayList<TyGia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(resource, null);
        }

        // Get current TyGia object
        TyGia tyGia = objects.get(position);

        // Bind data to the views
        ImageView imgHinh = convertView.findViewById(R.id.imgHinh);
        TextView txtMuaCk = convertView.findViewById(R.id.txtMuaCk);
        TextView txtMuaTm = convertView.findViewById(R.id.txtMuaTm);
        TextView txtBanTm = convertView.findViewById(R.id.txtBanTm);
        TextView txtType = convertView.findViewById(R.id.txtType);
        TextView txtBanCk = convertView.findViewById(R.id.txtBanCk);

        // Set the data
        if (tyGia.getBitmap() != null) {
            imgHinh.setImageBitmap(tyGia.getBitmap());
        } else {
            imgHinh.setImageResource(R.drawable.ic_launcher_background); // default image if bitmap is null
        }
        txtMuaTm.setText(tyGia.getMuatienmat());
        txtBanTm.setText(tyGia.getBantienmat());
        txtType.setText(tyGia.getType());
        txtBanCk.setText(tyGia.getBanck());
        txtMuaCk.setText(tyGia.getMuack());


        return convertView;
    }
}
