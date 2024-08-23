package com.iti.flex_meals.network.retrofit.networkCallBack;

import com.iti.flex_meals.model.pojo.categories.CategoryListItem;

import java.util.List;

public interface OnCategoriesMealNetworkCallBack {
    void onSuccess(List<CategoryListItem> categories);

    void onError(String errorMssg);
}
