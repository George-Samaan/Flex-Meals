package com.iti.flex_meals.ViewerListActivity.view;

import com.iti.flex_meals.model.pojo.categoriesList.CategoryListDetailed;
import com.iti.flex_meals.model.pojo.ingredients.IngredientItem;

import java.util.List;

public interface CategoriesMealView {
    void showCategoriesList(List<CategoryListDetailed> categoryListItemList);

    void showError(String message);

    void showCountriesList(List<CategoryListDetailed> countriesList);

    void showIngredients(List<IngredientItem> ingredients);

    void showIngredientsDetails(List<CategoryListDetailed> ingredients);
}
