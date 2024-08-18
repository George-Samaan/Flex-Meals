package com.iti.flex_meals.categoriesMealsActivity.view;

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
import com.iti.flex_meals.db.retrofit.pojo.categoriesList.CategoryListDetailed;

import java.util.ArrayList;
import java.util.List;

public class CategoriesDetailedAdapter extends RecyclerView.Adapter<CategoriesDetailedAdapter.ViewHolder> {
    private List<CategoryListDetailed> categories;
    private List<CategoryListDetailed> filteredCategories;

    private OnMealClick listener;

    public CategoriesDetailedAdapter(OnMealClick listener) {
        categories = new ArrayList<>();
        this.listener = listener;
        filteredCategories = new ArrayList<>(categories);
    }

    public void setCategories(List<CategoryListDetailed> categories) {
        if (categories != null) {
            this.categories = categories;
            this.filteredCategories = new ArrayList<>(categories);
        } else {
            this.categories = new ArrayList<>();
            this.filteredCategories = new ArrayList<>();
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesDetailedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reusable_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesDetailedAdapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

        CategoryListDetailed category = filteredCategories.get(position);
        holder.tvCategoryName.setText(category.getStrMeal());
        Glide.with(holder.itemView.getContext()).load(category.getStrMealThumb()).into(holder.ivCategory);

        holder.ivCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealClick(category.getIdMeal());
                Log.d("TAG", "onClick: " + category.getIdMeal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredCategories.size();
    }

    public void filter(String text) {
        filteredCategories.clear();
        if (text.isEmpty()) {
            filteredCategories.addAll(categories);
        } else {
            text = text.toLowerCase();
            for (CategoryListDetailed category : categories) {
                if (category.getStrMeal().toLowerCase().contains(text)) {
                    filteredCategories.add(category);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void clear() {
        filteredCategories.clear();
        categories.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView ivCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.category_name);
            ivCategory = itemView.findViewById(R.id.category_image);
        }
    }
}
