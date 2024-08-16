package com.iti.flex_meals.db.retrofit.networkCallBack;

import com.iti.flex_meals.db.retrofit.pojo.categoriesList.CategoryListDetailed;

import java.util.List;

public interface OnCategoriesListCallBack {
    void onSuccess(List<CategoryListDetailed> categoriesList);

    void onError(String message);
}
