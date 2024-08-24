package com.iti.flex_meals.network.retrofit.networkCallBack;

import com.iti.flex_meals.model.pojo.ingredients.IngredientItem;

import java.util.List;

public interface OnIngredientNetworkCallBack {
    void onSuccess(List<IngredientItem> ingredientList);

    void onError(String message);
}
