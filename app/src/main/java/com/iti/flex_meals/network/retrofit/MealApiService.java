package com.iti.flex_meals.network.retrofit;

import com.iti.flex_meals.model.pojo.categories.CategoryMealResponse;
import com.iti.flex_meals.model.pojo.categoriesList.CategoriesListResponse;
import com.iti.flex_meals.model.pojo.countries.AllCountryResponse;
import com.iti.flex_meals.model.pojo.ingredients.IngredientsResponse;
import com.iti.flex_meals.model.pojo.mealDetails.MealDetailsResponse;
import com.iti.flex_meals.model.pojo.randomMeal.RandomMealResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealApiService {

    @GET("random.php")
    Call<RandomMealResponse> getRandomResponseMeal();

    @GET("categories.php")
    Call<CategoryMealResponse> getCategories();

    @GET("list.php?a=list")
    Call<AllCountryResponse> getAllCountries();

    @GET("list.php?i=list")
    Call<IngredientsResponse> getAllIngredients();

    @GET("filter.php")
    Call<CategoriesListResponse> getCategoriesList(@Query("c") String category);

    @GET("filter.php")
    Call<CategoriesListResponse> getCountriesList(@Query("a") String country);

    @GET("filter.php")
    Call<CategoriesListResponse> getIngredientsList(@Query("i") String ingredient);

    @GET("lookup.php")
    Call<MealDetailsResponse> getMealDetails(@Query("i") String id);
}
