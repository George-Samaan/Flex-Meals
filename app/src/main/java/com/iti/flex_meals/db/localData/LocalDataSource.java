package com.iti.flex_meals.db.localData;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;

import java.util.List;

public interface LocalDataSource {
    void insertMeal(MealsItem meal);

    void deleteMeal(final String mealId);

    LiveData<List<MealsItem>> getAllFavoriteMeals(String uid);

    void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback);

}
