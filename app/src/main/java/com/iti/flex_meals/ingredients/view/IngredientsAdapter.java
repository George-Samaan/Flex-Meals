package com.iti.flex_meals.ingredients.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.flex_meals.R;
import com.iti.flex_meals.db.retrofit.pojo.ingredients.IngredientItem;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    List<IngredientItem> ingredients;
    List<IngredientItem> filteredCategories;
    OnIngredientClickListener onClick;
    private boolean isExpanded;

    public IngredientsAdapter(boolean isExpanded, OnIngredientClickListener onClick) {
        this.ingredients = new ArrayList<>();
        this.isExpanded = isExpanded;
        this.filteredCategories = new ArrayList<>();
        this.onClick = onClick;
    }

    public void setIngredients(List<IngredientItem> ingredients) {
        this.ingredients = ingredients;
        this.filteredCategories.clear(); // Clear filtered list if needed
        this.filteredCategories.addAll(ingredients); // Add all ingredients to filtered list
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

            IngredientItem ingredient = filteredCategories.get(position);
            holder.ingredientName.setText(ingredient.getStrIngredient());
            Glide.with(holder.itemView.getContext()).load(ingredient.getImageUrl()).into(holder.imv_ingredient);

        holder.imv_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.onIngredientClick(ingredient.getStrIngredient());
                Log.d("ingredient", ingredient.getStrIngredient());
            }
        });

    }


    @Override
    public int getItemCount() {
        if (!isExpanded) {
            return Math.min(filteredCategories.size(), 25); // Limit to 25 items if not expanded
        }
        return filteredCategories.size(); // Return full size if expanded
    }


    public void filter(String text) {
        List<IngredientItem> filteredList = new ArrayList<>();
        if (text.isEmpty()) {
            filteredList.addAll(ingredients);
        } else {
            text = text.toLowerCase();
            for (IngredientItem ingredientItem : ingredients) {
                if (ingredientItem.getStrIngredient().toLowerCase().contains(text)) {
                    filteredList.add(ingredientItem);
                }
            }
        }
        filteredCategories.clear();
        filteredCategories.addAll(filteredList);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        ImageView imv_ingredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tv_ingredient);
            imv_ingredient = itemView.findViewById(R.id.ingredient_image);
        }
    }
}
