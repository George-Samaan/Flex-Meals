package com.iti.flex_meals.network.retrofit.networkCallBack;

import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;

public interface OnMealDetailsNetworkCallBack {
    void onSuccess(MealsItem mealDetails);

    void onError(String message);
}
