package com.iti.flex_meals.db.localData;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

public interface LocalDataSource {
    void insertMeal(MealsItem meal);

    void deleteMeal(final String mealId);

    MealPlan getMealById(String id);

    LiveData<List<MealsItem>> getAllFavoriteMeals(String uid);

    void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback);

    LiveData<MealsItem> getFavoriteMeal(String mealId);


    void insertMeal(MealPlan mealPlan);

    void deletePlanMeal(final String mealId);

    LiveData<List<MealPlan>> getBreakfastMeals(String uid);

    LiveData<List<MealPlan>> getLunchMealsForUid(String uid);

    LiveData<List<MealPlan>> getDinnerMealsForUid(String uid);


    LiveData<List<MealPlan>> getBreakfastMealsByUidAndDate(String uid, long date);

    LiveData<List<MealPlan>> getLunchMealsByUidAndDate(String uid, long date);

    LiveData<List<MealPlan>> getDinnerMealsByUidAndDate(String uid, long date);
}
