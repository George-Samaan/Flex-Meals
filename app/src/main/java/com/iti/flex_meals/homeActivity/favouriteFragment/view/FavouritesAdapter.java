package com.iti.flex_meals.homeActivity.favouriteFragment.view;

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
import com.iti.flex_meals.categoriesMealsActivity.view.OnMealClick;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;
import com.iti.flex_meals.homeActivity.planFragment.view.OnMealPlanDelete;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {


    List<MealsItem> favourites;
    List<MealPlan> breakfastPlan;
    List<MealPlan> lunchPlan;
    List<MealPlan> dinnerPlan;
    private OnMealClick onMealClick;
    private OnMealPlanDelete onMealPlanDelete;

    public FavouritesAdapter(OnMealClick onMealClick, OnMealPlanDelete onMealPlanDelete) {
        this.favourites = new ArrayList<>();
        this.breakfastPlan = new ArrayList<>();
        this.lunchPlan = new ArrayList<>();
        this.dinnerPlan = new ArrayList<>();
        this.onMealClick = onMealClick;
        this.onMealPlanDelete = onMealPlanDelete;
    }

    @NonNull
    @Override
    public FavouritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reusable_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesAdapter.ViewHolder holder, int position) {
        if (favourites.size() > position) {
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
            holder.itemView.startAnimation(animation);
            MealsItem meal = favourites.get(position);
            holder.mealName.setText(meal.getStrMeal());
            Glide.with(holder.itemView.getContext()).load(meal.getStrMealThumb()).into(holder.mealImage);
            holder.itemView.setOnClickListener(v -> onMealClick.onMealClick(meal.getIdMeal()));
        } else if (breakfastPlan.size() + favourites.size() > position) {
            MealPlan mealPlan = breakfastPlan.get(position - favourites.size());
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
            holder.itemView.startAnimation(animation);
            holder.mealName.setText(mealPlan.getStrMeal());
            Log.d("mealImage", mealPlan.getStrMealThumb());
            Glide.with(holder.itemView.getContext()).load(mealPlan.getStrMealThumb()).into(holder.mealImage);
            onMealPlanLonClickAndShortClick(holder, mealPlan);
        } else if (lunchPlan.size() + breakfastPlan.size() + favourites.size() > position) {
            MealPlan mealPlan = lunchPlan.get(position - breakfastPlan.size() - favourites.size());
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
            holder.itemView.startAnimation(animation);
            holder.mealName.setText(mealPlan.getStrMeal());
            Glide.with(holder.itemView.getContext()).load(mealPlan.getStrMealThumb()).into(holder.mealImage);
            onMealPlanLonClickAndShortClick(holder, mealPlan);
        } else {
            MealPlan mealPlan = dinnerPlan.get(position - breakfastPlan.size() - lunchPlan.size() - favourites.size());
            Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
            holder.itemView.startAnimation(animation);
            holder.mealName.setText(mealPlan.getStrMeal());
            Glide.with(holder.itemView.getContext()).load(mealPlan.getStrMealThumb()).into(holder.mealImage);
            onMealPlanLonClickAndShortClick(holder, mealPlan);
        }
    }

    private void onMealPlanLonClickAndShortClick(@NonNull ViewHolder holder, MealPlan mealPlan) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Animation shake = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.shake_anim);
                holder.deleteButton.startAnimation(shake);
                holder.deleteButton.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hide the delete button and reset its state
                holder.deleteButton.clearAnimation(); // Clear any ongoing animation
                holder.deleteButton.setVisibility(View.GONE); // Hide the delete button
                onMealClick.onMealClick(mealPlan.getIdMeal());
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMealPlanDelete != null) {
                    onMealPlanDelete.onMealDelete(mealPlan.getIdMeal());
                }
                holder.deleteButton.setVisibility(View.GONE);
                holder.deleteButton.clearAnimation();
                Log.d("Delete emealPlan", mealPlan.getIdMeal());
            }
        });
    }


    @Override
    public int getItemCount() {
        return favourites.size() + breakfastPlan.size() + lunchPlan.size() + dinnerPlan.size();
    }

    public void setFavourites(List<MealsItem> favourites) {
        this.favourites = favourites;
        notifyDataSetChanged();
    }

    public void setBreakfastPlan(List<MealPlan> breakfastPlan) {
        this.breakfastPlan = breakfastPlan;
        notifyDataSetChanged();
    }

    public void setLunchPlan(List<MealPlan> lunchPlan) {
        this.lunchPlan = lunchPlan;
        notifyDataSetChanged();
    }

    public void setDinnerPlan(List<MealPlan> dinnerPlan) {
        this.dinnerPlan = dinnerPlan;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.category_name);
            mealImage = itemView.findViewById(R.id.category_image);
            deleteButton = itemView.findViewById(R.id.delete_button_image);

        }
    }
}