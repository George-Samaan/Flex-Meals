package com.iti.flex_meals.mealDetailedActivity.presenter;

import com.iti.flex_meals.db.localData.OnMealExistsCallback;

public interface MealDetailPresenter {
    void getMealDetail(String id);

    void saveMealToFavorites(String id);

    void removeMealFromFavorites(String id);

    void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback);

    boolean checkingCredentialOfUser();

    String getUserUid();
}
