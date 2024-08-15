package com.iti.flex_meals.db.retrofit.networkCallBack;

import com.iti.flex_meals.db.retrofit.pojo.categories.CategoriesItem;

import java.util.List;

public interface OnCategoriesMealNetworkCallBack {
    void onSuccess(List<CategoriesItem> categories);

    void onError(String errorMssg);
}
