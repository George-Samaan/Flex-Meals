package com.iti.flex_meals.db.repository;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.localData.OnMealExistsCallback;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCountriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnIngredientNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnMealDetailsNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

public interface Repository {

    // Shared Preferences
    void saveLoginAuth(String token);

    void saveEmail(String email);

    public void saveUserUid(String uid);

    String getEmail();

    String getLoginAuth();

    String getUserUid();

    void clearAuthData();


    // Remote Data Source
    void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack);

    void getCategories(OnCategoriesMealNetworkCallBack onCategoriesMealNetworkCallBack);

    void getAllCountries(OnCountriesMealNetworkCallBack onCountriesMealNetworkCallBack);

    void getCategoriesList(String category, OnCategoriesListCallBack onCategoriesListCallBack);

    void getCountriesList(String country, OnCategoriesListCallBack onCategoriesListCallBack);

    void getIngredientsDetailed(String ingredient, OnCategoriesListCallBack onCategoriesListCallBack);

    void getIngredients(OnIngredientNetworkCallBack onIngredientNetworkCallBack);

    void getMealById(String id, OnMealDetailsNetworkCallBack onMealDetailsNetworkCallBack);



    // Local Data Source (Favourites)
    void addMealToFavourites(MealsItem meal);

    void removeMealFromFavourites(String mealId);

    LiveData<List<MealsItem>> getAllFavoriteMeals(String uid);

    LiveData<MealsItem> getFavoriteMealById(String mealId);

    void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback);

    void addMealToMealPlan(MealPlan mealPlan);

    void removeMealFromMealPlan(String mealId);

    LiveData<List<MealPlan>> getBreakfastMeals(String uid);

    LiveData<List<MealPlan>> getLunchMealsForUid(String uid);

    LiveData<List<MealPlan>> getDinnerMealsForUid(String uid);


}
