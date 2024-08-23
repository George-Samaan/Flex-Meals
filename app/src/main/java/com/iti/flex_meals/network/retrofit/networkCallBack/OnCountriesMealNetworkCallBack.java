package com.iti.flex_meals.network.retrofit.networkCallBack;

import com.iti.flex_meals.model.pojo.countries.CountryItem;

import java.util.List;

public interface OnCountriesMealNetworkCallBack {
    void onSuccess(List<CountryItem> countries);

    void onError(String message);
}
