package com.iti.flex_meals.categoriesMealsActivity.presenter;

import com.iti.flex_meals.categoriesMealsActivity.view.CategoriesMealsActivity;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnIngredientNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.categoriesList.CategoryListDetailed;
import com.iti.flex_meals.db.retrofit.pojo.ingredients.IngredientItem;

import java.util.List;

public class CategoriesListImpl implements CategoriesList {
    CategoriesMealsActivity view;
    Repository repository;

    public CategoriesListImpl(CategoriesMealsActivity view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }


    @Override
    public void showCategoriesList(String category) {
        repository.getCategoriesList(category, new OnCategoriesListCallBack() {
            @Override
            public void onSuccess(List<CategoryListDetailed> categoriesList) {
                view.showCategoriesList(categoriesList);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void showCountriesList(String country) {
        repository.getCountriesList(country, new OnCategoriesListCallBack() {
            @Override
            public void onSuccess(List<CategoryListDetailed> categoriesList) {
                view.showCountriesList(categoriesList);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });

    }

    @Override
    public void showIngredients() {
        repository.getIngredients(new OnIngredientNetworkCallBack() {
            @Override
            public void onSuccess(List<IngredientItem> ingredients) {
                view.showIngredients(ingredients);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void showIngredientsDetails(String ingredient) {
        repository.getIngredientsDetailed(ingredient, new OnCategoriesListCallBack() {
            @Override
            public void onSuccess(List<CategoryListDetailed> categoriesList) {
                view.showIngredientsDetails(categoriesList);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }
}
