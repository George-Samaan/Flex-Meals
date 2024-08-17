package com.iti.flex_meals.db.retrofit.networkCallBack;

import com.iti.flex_meals.db.retrofit.pojo.ingredients.IngredientItem;

import java.util.List;

public interface OnIngredientNetworkCallBack {
    void onSuccess(List<IngredientItem> ingredientList);

    void onError(String message);
}
