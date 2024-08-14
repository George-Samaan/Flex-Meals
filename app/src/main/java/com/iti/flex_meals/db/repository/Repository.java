package com.iti.flex_meals.db.repository;

import com.iti.flex_meals.db.retrofit.OnRandomMealNetworkCallBack;

public interface Repository {
    void saveLoginAuth(String token);

    void saveEmail(String email);

    String getEmail();

    String getLoginAuth();

    void clearAuthData();

    // Remote Data Source
    void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack);
}
