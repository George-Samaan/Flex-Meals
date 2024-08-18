package com.iti.flex_meals.db.retrofit.networkCallBack;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;

public interface OnMealDetailsNetworkCallBack {
    void onSuccess(MealsItem mealDetails);

    void onError(String message);
}
