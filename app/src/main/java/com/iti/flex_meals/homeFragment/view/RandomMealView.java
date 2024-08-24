package com.iti.flex_meals.homeFragment.view;

import com.iti.flex_meals.model.pojo.categories.CategoryListItem;
import com.iti.flex_meals.model.pojo.countries.CountryItem;
import com.iti.flex_meals.model.pojo.ingredients.IngredientItem;
import com.iti.flex_meals.model.pojo.randomMeal.RandomMealItem;

import java.util.List;

public interface RandomMealView {
    void showRandoMeal(RandomMealItem item);

    void showErrorMessage(String message);

    void showLoadingIndicator();

    void hideLoadingIndicator();

    void showMealCategories(List<CategoryListItem> categories);

    void showCountriesList(List<CountryItem> countries);

    void showIngredients(List<IngredientItem> ingredients);
}
