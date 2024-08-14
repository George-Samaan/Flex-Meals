package com.iti.flex_meals.homeActivity.homeFragment.view;

import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;

public interface RandomMealView {
    void showRandoMeal(RandomMealItem item);

    void showErrorMessage(String message);

    void showLoadingIndicator();

    void hideLoadingIndicator();
}
