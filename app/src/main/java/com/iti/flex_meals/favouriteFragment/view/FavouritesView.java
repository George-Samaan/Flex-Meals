package com.iti.flex_meals.favouriteFragment.view;

import androidx.lifecycle.LifecycleOwner;

import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;

import java.util.List;

public interface FavouritesView {
    LifecycleOwner getLifecycleOwner(); // For observing LiveData
    void showFavouriteMeals(List<MealsItem> mealsItems);
}
