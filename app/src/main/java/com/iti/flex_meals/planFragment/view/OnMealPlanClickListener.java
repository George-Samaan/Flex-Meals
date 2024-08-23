package com.iti.flex_meals.planFragment.view;

import com.iti.flex_meals.favouriteFragment.view.FavouritesAdapter;
import com.iti.flex_meals.planFragment.model.MealPlan;

public interface OnMealPlanClickListener {
    void onMealPlanLongClick(MealPlan mealPlan, FavouritesAdapter.ViewHolder holder);

    void onMealPlanShortClick(MealPlan mealPlan, FavouritesAdapter.ViewHolder holder);

    void onMealPlanDeleteClick(MealPlan mealPlan, FavouritesAdapter.ViewHolder holder);
}
