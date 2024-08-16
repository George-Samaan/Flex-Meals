package com.iti.flex_meals.db.repository;

import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCountriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;

public interface Repository {
    void saveLoginAuth(String token);

    void saveEmail(String email);

    String getEmail();

    String getLoginAuth();

    void clearAuthData();

    // Remote Data Source
    void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack);

    void getCategories(OnCategoriesMealNetworkCallBack onCategoriesMealNetworkCallBack);

    void getAllCountries(OnCountriesMealNetworkCallBack onCountriesMealNetworkCallBack);

    void getCategoriesList(String category, OnCategoriesListCallBack onCategoriesListCallBack);
}
