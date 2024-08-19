package com.iti.flex_meals.mealDetailedActivity.presenter;

public interface MealDetailPresenter {
    void getMealDetail(String id);

    void saveMealToFavorites(String id);

    void removeMealFromFavorites(String id);

}
