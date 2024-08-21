package com.iti.flex_meals.mealDetailedActivity.presenter;

import com.iti.flex_meals.db.localData.OnMealExistsCallback;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

public interface MealDetailPresenter {
    void getMealDetail(String id);

    void saveMealToFavorites(String id);

    void saveMealToMealPlan(MealPlan mealPlan);

    void removeMealFromFavorites(String id);

    void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback);

    boolean checkingCredentialOfUser();

    String getUserUid();

    void getFavoriteMealDetail(String mealId);

}
