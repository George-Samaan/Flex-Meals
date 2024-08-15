package com.iti.flex_meals.homeActivity.homeFragment.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.iti.flex_meals.R;
import com.iti.flex_meals.db.retrofit.pojo.countries.CountryItem;

import java.util.ArrayList;
import java.util.List;

public class CountriesListAdapter extends RecyclerView.Adapter<CountriesListAdapter.ViewHolder> {
    private List<CountryItem> countries;

    public CountriesListAdapter() {
        countries = new ArrayList<>();
    }

    public void setCountries(List<CountryItem> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountriesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new CountriesListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesListAdapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

        CountryItem country = countries.get(position);
        holder.chip.setText(country.getStrArea());
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Chip chip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            chip = itemView.findViewById(R.id.county_name);
        }
    }
}

