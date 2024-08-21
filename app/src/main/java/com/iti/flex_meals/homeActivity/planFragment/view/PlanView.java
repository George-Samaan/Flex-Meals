package com.iti.flex_meals.homeActivity.planFragment.view;

import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

public interface PlanView {
    void showBreakfastMeals(List<MealPlan> breakfastMeals);

    void showLunchMeals(List<MealPlan> lunchMeals);

    void showDinnerMeals(List<MealPlan> dinnerMeals);

    void OnMealDeleted();
}
