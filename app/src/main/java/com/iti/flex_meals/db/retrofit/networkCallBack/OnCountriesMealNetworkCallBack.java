package com.iti.flex_meals.db.retrofit.networkCallBack;

import com.iti.flex_meals.db.retrofit.pojo.countries.CountryItem;

import java.util.List;

public interface OnCountriesMealNetworkCallBack {
    void onSuccess(List<CountryItem> countries);

    void onError(String message);
}
