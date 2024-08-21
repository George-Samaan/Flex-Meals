package com.iti.flex_meals.mealDetailedActivity.view;

import androidx.lifecycle.LifecycleOwner;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

public interface MealDetailedView {

    void showMealDetails(MealsItem meal);

    void showError(String errorMssg);

    void onMealSaved();

    void onMealRemoved();

    void showMessage(String message);

    LifecycleOwner getLifecycleOwner();

    void showMealPlanDetails(MealPlan mealPlan);

}
