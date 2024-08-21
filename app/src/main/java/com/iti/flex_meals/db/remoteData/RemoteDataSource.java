package com.iti.flex_meals.db.remoteData;

import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCountriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnIngredientNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnMealDetailsNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;

public interface RemoteDataSource {
    void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack);

    void getCategories(OnCategoriesMealNetworkCallBack onCategoriesMealNetworkCallBack);

    void getAllCountries(OnCountriesMealNetworkCallBack onCountriesMealNetworkCallBack);

    void getCategoriesList(String category, OnCategoriesListCallBack onCategoriesListCallBack);

    void getCountriesList(String country, OnCategoriesListCallBack onCategoriesListCallBack);

    void getIngredients(OnIngredientNetworkCallBack onIngredientNetworkCallBack);

    void getIngredientsList(String ingredient, OnCategoriesListCallBack onCategoriesListCallBack);

    void getMealDetailById(String id, OnMealDetailsNetworkCallBack onMealDetailsNetworkCallBack);

}

