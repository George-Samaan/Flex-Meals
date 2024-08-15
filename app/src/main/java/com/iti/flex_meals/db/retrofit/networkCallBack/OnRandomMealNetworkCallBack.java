package com.iti.flex_meals.db.retrofit.networkCallBack;

import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;

public interface OnRandomMealNetworkCallBack {
    void onSuccess(RandomMealItem randomMealItem);

    void onError(String errorMssg);
}

