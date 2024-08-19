package com.iti.flex_meals.db.localData;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;

import java.util.List;

public interface LocalDataSource {
    void insertMeal(MealsItem meal);

    void deleteMeal(MealsItem meal);

    LiveData<List<MealsItem>> getAllFavoriteMeals();
}
