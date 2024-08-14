package com.iti.flex_meals.db.retrofit;

import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;

public interface OnRandomMealNetworkCallBack {
    void onSuccess(RandomMealItem randomMealItem);

    void onError(String errorMssg);
}
