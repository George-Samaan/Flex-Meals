package com.iti.flex_meals.ViewerListActivity.presenter;

import com.iti.flex_meals.ViewerListActivity.view.ViewerListCategoriesActivity;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.model.pojo.categoriesList.CategoryListDetailed;
import com.iti.flex_meals.model.pojo.ingredients.IngredientItem;
import com.iti.flex_meals.network.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.network.retrofit.networkCallBack.OnIngredientNetworkCallBack;

import java.util.List;

public class CategoriesListImpl implements CategoriesList {
    ViewerListCategoriesActivity view;
    Repository repository;

    public CategoriesListImpl(ViewerListCategoriesActivity view, Repository repository) {
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
