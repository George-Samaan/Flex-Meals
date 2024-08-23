package com.iti.flex_meals.network.retrofit.networkCallBack;

import com.iti.flex_meals.model.pojo.categoriesList.CategoryListDetailed;

import java.util.List;

public interface OnCategoriesListCallBack {
    void onSuccess(List<CategoryListDetailed> categoriesList);

    void onError(String message);
}
