package com.iti.flex_meals.db.retrofit;

import com.iti.flex_meals.db.retrofit.pojo.categories.CategoryMealResponse;
import com.iti.flex_meals.db.retrofit.pojo.countries.AllCountryResponse;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealApiService {

    @GET("random.php")
    Call<RandomMealResponse> getRandomResponseMeal();

    @GET("categories.php")
    Call<CategoryMealResponse> getCategories();

    @GET("list.php?a=list")
    Call<AllCountryResponse> getAllCountries();
}
