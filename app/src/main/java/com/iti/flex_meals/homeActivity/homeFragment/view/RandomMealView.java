package com.iti.flex_meals.homeActivity.homeFragment.view;

import com.iti.flex_meals.db.retrofit.pojo.categories.CategoryListItem;
import com.iti.flex_meals.db.retrofit.pojo.countries.CountryItem;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;

import java.util.List;

public interface RandomMealView {
    void showRandoMeal(RandomMealItem item);

    void showErrorMessage(String message);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showMealCategories(List<CategoryListItem> categories);

    void showCountriesList(List<CountryItem> countries);

}
