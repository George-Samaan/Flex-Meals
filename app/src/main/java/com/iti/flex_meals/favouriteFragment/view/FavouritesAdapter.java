package com.iti.flex_meals.favouriteFragment.view;

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
import com.iti.flex_meals.ViewerListActivity.view.OnMealClick;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.planFragment.model.MealPlan;
import com.iti.flex_meals.planFragment.view.OnMealPlanInteraction;

import java.util.ArrayList;
import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {
    List<MealsItem> favourites;
    List<MealPlan> breakfastPlan;
    List<MealPlan> lunchPlan;
    List<MealPlan> dinnerPlan;
    private OnMealClick onMealClick;
    private OnMealPlanInteraction onMealPlanInteraction;

    public FavouritesAdapter(OnMealClick onMealClick, OnMealPlanInteraction onMealPlanInteraction) {
        this.favourites = new ArrayList<>();
        this.breakfastPlan = new ArrayList<>();
        this.lunchPlan = new ArrayList<>();
        this.dinnerPlan = new ArrayList<>();
        this.onMealClick = onMealClick;
        this.onMealPlanInteraction = onMealPlanInteraction;
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
            setupMealPlanViewHolder(holder, mealPlan);

        } else if (lunchPlan.size() + breakfastPlan.size() + favourites.size() > position) {
            MealPlan mealPlan = lunchPlan.get(position - breakfastPlan.size() - favourites.size());
            setupMealPlanViewHolder(holder, mealPlan);

        } else {
            MealPlan mealPlan = dinnerPlan.get(position - breakfastPlan.size() - lunchPlan.size() - favourites.size());
            setupMealPlanViewHolder(holder, mealPlan);
        }
    }

    private void setupMealPlanViewHolder(@NonNull ViewHolder holder, MealPlan mealPlan) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_in_animation);
        holder.itemView.startAnimation(animation);
        holder.mealName.setText(mealPlan.getStrMeal());
        Glide.with(holder.itemView.getContext()).load(mealPlan.getStrMealThumb()).into(holder.mealImage);
        onMealPlanLonClickAndShortClick(holder, mealPlan);
    }

    private void onMealPlanLonClickAndShortClick(@NonNull ViewHolder holder, MealPlan mealPlan) {
        holder.itemView.setOnLongClickListener(v -> {
            onMealPlanInteraction.onMealPlanLongClick(mealPlan, holder);
            return true;
        });
        holder.itemView.setOnClickListener(v -> onMealPlanInteraction.onMealPlanShortClick(mealPlan, holder));
        holder.deleteButton.setOnClickListener(v -> {
            onMealPlanInteraction.onMealPlanDeleteClick(mealPlan, holder);
            Log.d("Delete mealPlan", mealPlan.getIdMeal());
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
        public ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.category_name);
            mealImage = itemView.findViewById(R.id.category_image);
            deleteButton = itemView.findViewById(R.id.delete_button_image);
        }
    }
}