package com.example.prac13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private int[] mImage;

    public ImageAdapter(Context c, int[] image) {
        mContext = c;
        mImage = image;
    }

    @Override
    public int getCount() {
        return mImage.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView imageView;
        if(convertView == null) {
            imageView = (ImageView) layoutInflater.inflate(R.layout.image_item_grid, null);
            imageView.setImageResource(mImage[position]);
        } else {
            imageView = (ImageView) convertView;
        }
        return imageView;
    }
}
