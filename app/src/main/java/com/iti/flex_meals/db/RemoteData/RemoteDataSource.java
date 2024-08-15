package com.iti.flex_meals.db.RemoteData;

import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;

public interface RemoteDataSource {
    void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack);

    void getCategories(OnCategoriesMealNetworkCallBack onCategoriesMealNetworkCallBack);

}

