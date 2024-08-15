package com.iti.flex_meals.db.RemoteData;

import com.iti.flex_meals.db.retrofit.MealApiService;
import com.iti.flex_meals.db.retrofit.Retrofit;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCountriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.categories.CategoryMealResponse;
import com.iti.flex_meals.db.retrofit.pojo.countries.AllCountryResponse;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSourceImpl implements RemoteDataSource {
    private final MealApiService mealApiService;

    public RemoteDataSourceImpl() {
        mealApiService = Retrofit.getRetrofit().create(MealApiService.class);
    }

    @Override
    public void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack) {
        Call<RandomMealResponse> call = mealApiService.getRandomResponseMeal();
        call.enqueue(new Callback<RandomMealResponse>() {
            @Override
            public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                if (response.isSuccessful()) {
                    onRandomMealNetworkCallBack.onSuccess(response.body().getMeals().get(0));
                } else {
                    onRandomMealNetworkCallBack.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<RandomMealResponse> call, Throwable throwable) {
                onRandomMealNetworkCallBack.onError(throwable.getMessage());
            }
        });
    }

    @Override
    public void getCategories(OnCategoriesMealNetworkCallBack onCategoriesNetworkCallBack) {
        Call<CategoryMealResponse> call = mealApiService.getCategories();
        call.enqueue(new Callback<CategoryMealResponse>() {
            @Override
            public void onResponse(Call<CategoryMealResponse> call, Response<CategoryMealResponse> response) {
                onCategoriesNetworkCallBack.onSuccess(response.body().getCategories());
            }

            @Override
            public void onFailure(Call<CategoryMealResponse> call, Throwable throwable) {

                onCategoriesNetworkCallBack.onError(throwable.getMessage());
            }
        });
    }

    @Override
    public void getAllCountries(OnCountriesMealNetworkCallBack onCountriesMealNetworkCallBack) {
        Call<AllCountryResponse> call = mealApiService.getAllCountries();
        call.enqueue(new Callback<AllCountryResponse>() {
            @Override
            public void onResponse(Call<AllCountryResponse> call, Response<AllCountryResponse> response) {
                onCountriesMealNetworkCallBack.onSuccess(response.body().getAllCountries());
            }

            @Override
            public void onFailure(Call<AllCountryResponse> call, Throwable throwable) {
                onCountriesMealNetworkCallBack.onError(throwable.getMessage());
            }
        });
    }
}
