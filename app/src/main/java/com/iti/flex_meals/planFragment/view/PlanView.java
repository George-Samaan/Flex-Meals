package com.iti.flex_meals.planFragment.view;

import com.iti.flex_meals.planFragment.model.MealPlan;

import java.util.List;

public interface PlanView {
    void showBreakfastMeals(List<MealPlan> breakfastMeals);

    void showLunchMeals(List<MealPlan> lunchMeals);

    void showDinnerMeals(List<MealPlan> dinnerMeals);

    void OnMealDeleted();
}
