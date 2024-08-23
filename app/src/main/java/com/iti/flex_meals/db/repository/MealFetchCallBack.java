package com.iti.flex_meals.db.repository;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.planFragment.model.MealPlan;

import java.util.List;

public interface MealFetchCallBack {
    void onBreakfastMealsFetched(LiveData<List<MealPlan>> breakfastMeals);

    void onLunchMealsFetched(LiveData<List<MealPlan>> lunchMeals);

    void onDinnerMealsFetched(LiveData<List<MealPlan>> dinnerMeals);
}
