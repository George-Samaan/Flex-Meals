package com.iti.flex_meals.mealDetailedActivity.view;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.flex_meals.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {


    private List<Pair<String, String>> ingredientsAndMeasurements;

    public IngredientAdapter(List<Pair<String, String>> ingredientsAndMeasurements) {
        this.ingredientsAndMeasurements = ingredientsAndMeasurements;
    }

    public void setIngredientsAndMeasurements(List<Pair<String, String>> ingredientsAndMeasurements) {
        this.ingredientsAndMeasurements = ingredientsAndMeasurements;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_measurment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        Pair<String, String> ingredientAndMeasurement = ingredientsAndMeasurements.get(position);
        holder.ingredientName.setText(ingredientAndMeasurement.first);
        holder.ingredientMeasurement.setText(ingredientAndMeasurement.second);

        String imageUrl = "https://www.themealdb.com/images/ingredients/" + ingredientAndMeasurement.first + ".png";
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.ingredientImage);
    }

    @Override
    public int getItemCount() {
        return ingredientsAndMeasurements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        CircleImageView ingredientImage;
        TextView ingredientMeasurement;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientImage = itemView.findViewById(R.id.ingredientImage);
            ingredientMeasurement = itemView.findViewById(R.id.ingredientMeasure);
        }
    }
}