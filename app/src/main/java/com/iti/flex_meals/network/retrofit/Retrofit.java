package com.iti.flex_meals.network.retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static retrofit2.Retrofit retroFit = null;


    public static retrofit2.Retrofit getRetrofit() {
        if (retroFit == null) {
            retroFit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://www.themealdb.com/api/json/v1/1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retroFit;
    }
}