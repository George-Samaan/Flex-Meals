package com.iti.flex_meals.homeActivity.homeFragment.view;

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
import com.iti.flex_meals.db.retrofit.pojo.categories.CategoriesItem;

import java.util.ArrayList;
import java.util.List;

public class CategoriesListAdapter extends RecyclerView.Adapter<CategoriesListAdapter.ViewHolder> {
    private List<CategoriesItem> categories;

    public CategoriesListAdapter() {
        categories = new ArrayList<>();
    }

    public void setCategories(List<CategoriesItem> categories) {
        this.categories = categories;
    }


    @NonNull
    @Override
    public CategoriesListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesListAdapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);

        CategoriesItem category = categories.get(position);
        holder.tvCategoryName.setText(category.getStrCategory());
        Glide.with(holder.itemView.getContext()).load(category.getStrCategoryThumb()).into(holder.ivCategory);


    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView ivCategory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tv_category);
            ivCategory = itemView.findViewById(R.id.category_image);
        }
    }
}
