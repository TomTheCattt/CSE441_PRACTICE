package com.example.ex14_02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ex14_02.Models.Item;

import java.util.ArrayList;

public class myarrayAdapter extends ArrayAdapter<Item> {

    Activity content = null;
    ArrayList<Item> arrayList = null;
    int layoutId;

    public myarrayAdapter(Activity content, int layoutId, ArrayList<Item> arrayList) {
        super(content, layoutId, arrayList);
        this.content = content;
        this.layoutId = layoutId;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = content.getLayoutInflater();
        convertView = inflater.inflate(layoutId, null);
        Item item = arrayList.get(position);
        ImageView btnlike = convertView.findViewById(R.id.imageButton);
        int thich = item.getThich();
        if (thich == 1) {
            btnlike.setImageResource(R.drawable.like);
        } else {
            btnlike.setImageResource(R.drawable.unlike);
        }
        TextView tieude = convertView.findViewById(R.id.txtTieuDe);
        tieude.setText(item.getTieude());
        TextView maso = convertView.findViewById(R.id.txtMaso);
        maso.setText(item.getMaso());
        return convertView;
    }
}
