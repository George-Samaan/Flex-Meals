package com.iti.flex_meals.categoriesMealsActivity.presenter;

import com.iti.flex_meals.categoriesMealsActivity.view.CategoriesMealsActivity;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.db.retrofit.pojo.categoriesList.CategoryListDetailed;

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
}
