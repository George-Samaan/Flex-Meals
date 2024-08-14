package com.iti.flex_meals.db.repository;


import com.iti.flex_meals.db.retrofit.MealApiService;
import com.iti.flex_meals.db.retrofit.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.Retrofit;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealResponse;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RepositoryImpl implements Repository {

    private final SharedPreferencesDataSourceImpl sharedPreferencesDataSource;
    private MealApiService mealApiService;

    public RepositoryImpl(SharedPreferencesDataSourceImpl sharedPreferencesDataSource) {
        this.sharedPreferencesDataSource = sharedPreferencesDataSource;
        mealApiService = Retrofit.getRetrofit().create(MealApiService.class);

    }

    @Override
    public void saveLoginAuth(String token) {
        sharedPreferencesDataSource.saveLoginAuth(token);
    }
    @Override
    public void saveEmail(String email) {
        sharedPreferencesDataSource.saveEmail(email);
    }
    @Override
    public String getEmail() {
        return sharedPreferencesDataSource.getEmail();
    }
    @Override
    public String getLoginAuth() {
        return sharedPreferencesDataSource.getLoginAuth();
    }
    @Override
    public void clearAuthData() {
        sharedPreferencesDataSource.clearAuthData();
    }


    // Remote Data Source
    @Override
    public void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack) {
        Call<RandomMealResponse> call = mealApiService.getRandomResponseMeal();
        call.enqueue(new Callback<RandomMealResponse>() {
            @Override
            public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                if (response.isSuccessful()) {
                    onRandomMealNetworkCallBack.onSuccess(response.body().getMeals().get(0));
                } else onRandomMealNetworkCallBack.onError(response.message().toString());
            }

            @Override
            public void onFailure(Call<RandomMealResponse> call, Throwable throwable) {
                onRandomMealNetworkCallBack.onError(throwable.getMessage().toString());
            }
        });
    }
}
