package com.iti.flex_meals.network.retrofit.networkCallBack;

import com.iti.flex_meals.model.pojo.randomMeal.RandomMealItem;

public interface OnRandomMealNetworkCallBack {
    void onSuccess(RandomMealItem randomMealItem);

    void onError(String errorMssg);
}

