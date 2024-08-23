package com.iti.flex_meals.homeFragment.presenter;

public interface HomePresenter {
    void showRandomMeal();

    void showMealCategories();

    void showCountriesList();

    void showIngredients();

    void fetchDataFromFirebase();
}
