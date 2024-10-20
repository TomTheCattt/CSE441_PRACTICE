package Adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ex18.MainActivity;
import com.example.ex18.R;
import com.example.ex18.SubActivity;

import java.util.ArrayList;

import Model.Item;

public class myarrayAdapter extends ArrayAdapter<Item> {
    Activity context;
    ArrayList<Item> myArray;
    int layoutId;

    public myarrayAdapter(Activity context, int layoutId, ArrayList<Item> arr) {
        super(context, layoutId, arr);
        this.context = context;
        this.layoutId = layoutId;
        this.myArray = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(layoutId, parent, false);

            holder = new ViewHolder();
            holder.txtId = convertView.findViewById(R.id.txtId);
            holder.txtTitle = convertView.findViewById(R.id.txtTitle);
            holder.favoriteToggle = convertView.findViewById(R.id.favoriteToggle);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Item item = myArray.get(position);

        holder.txtId.setText(item.getId());
        holder.txtTitle.setText(item.getTitle());

        updateFavoriteButton(holder.favoriteToggle, item.getLike() == 1);

        holder.favoriteToggle.setOnClickListener(v -> toggleFavorite(item, holder.favoriteToggle));

        holder.txtTitle.setOnClickListener(v -> openSubActivity(item));

        return convertView;
    }

    private void updateFavoriteButton(ImageButton button, boolean isFavorite) {
        button.setImageResource(isFavorite ? R.drawable.like : R.drawable.unlike);
    }

    private void toggleFavorite(Item item, ImageButton button) {
        boolean newFavoriteState = item.getLike() != 1;
        ContentValues values = new ContentValues();
        values.put("like", newFavoriteState ? 1 : 0);
        MainActivity.db.update("song", values, "id=?", new String[]{item.getId()});
        item.setLike(newFavoriteState ? 1 : 0);
        updateFavoriteButton(button, newFavoriteState);
    }

    private void openSubActivity(Item item) {
        Intent intent = new Intent(context, SubActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", item.getId());
        bundle.putString("title", item.getTitle());
        intent.putExtra("package", bundle);
        context.startActivity(intent);
    }

    static class ViewHolder {
        TextView txtId;
        TextView txtTitle;
        ImageButton favoriteToggle;
    }
}