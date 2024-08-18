package com.iti.flex_meals.mealDetailedActivity.view;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;

public interface MealDetailedView {

    void showMealDetails(MealsItem meal);

    void showError(String errorMssg);
}
