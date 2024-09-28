package com.example.prac03;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Model.Country;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private List<Country> countries;

    public CountryAdapter(List<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.country_item, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country currentItem = countries.get(position);
        holder.countryName.setText(currentItem.getCountryName());
        holder.countryCapital.setText(currentItem.getCountryCapital());
        holder.flagImage.setImageResource(currentItem.getCountryFlag());
        holder.itemView.setOnClickListener(v-> {
            Context context = v.getContext();
            Intent i = new Intent(context, MainActivity2.class);
            i.putExtra("countryName", currentItem.getCountryName());
            i.putExtra("countryCapital", currentItem.getCountryCapital());
            i.putExtra("countryPopulation", currentItem.getCountryPopulation());
            i.putExtra("countryArea", currentItem.getCountryArea());
            i.putExtra("countryDensity", currentItem.getCountryDensity());
            i.putExtra("countryWorldShare", currentItem.getWorldShare());
            i.putExtra("countryFlag", currentItem.getCountryFlag());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        ImageView flagImage;
        TextView countryName, countryCapital;

        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            flagImage = itemView.findViewById(R.id.flagImage);
            countryName = itemView.findViewById(R.id.txtCountryName);
            countryCapital = itemView.findViewById(R.id.txtCountryCapital);
        }
    }
}
