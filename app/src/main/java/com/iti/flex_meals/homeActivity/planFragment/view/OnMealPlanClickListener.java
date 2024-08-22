package com.iti.flex_meals.homeActivity.planFragment.view;

import com.iti.flex_meals.homeActivity.favouriteFragment.view.FavouritesAdapter;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

public interface OnMealPlanClickListener {
    void onMealPlanLongClick(MealPlan mealPlan, FavouritesAdapter.ViewHolder holder);

    void onMealPlanShortClick(MealPlan mealPlan, FavouritesAdapter.ViewHolder holder);

    void onMealPlanDeleteClick(MealPlan mealPlan, FavouritesAdapter.ViewHolder holder);
}
