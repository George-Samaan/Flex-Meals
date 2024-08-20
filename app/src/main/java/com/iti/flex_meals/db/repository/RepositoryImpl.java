package com.iti.flex_meals.db.repository;


import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.localData.LocalDataSource;
import com.iti.flex_meals.db.localData.OnMealExistsCallback;
import com.iti.flex_meals.db.remoteData.RemoteDataSource;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCountriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnIngredientNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnMealDetailsNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;

import java.util.List;


public class RepositoryImpl implements Repository {

    private final SharedPreferencesDataSourceImpl sharedPreferencesDataSource;
    private final RemoteDataSource remoteDataSource;
    private final LocalDataSource localDataSource;

    public RepositoryImpl(SharedPreferencesDataSourceImpl sharedPreferencesDataSource, RemoteDataSource remoteDataSource, LocalDataSource localDataSource) {
        this.sharedPreferencesDataSource = sharedPreferencesDataSource;
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    //Shared Preferences
    @Override
    public void saveLoginAuth(String token) {
        sharedPreferencesDataSource.saveLoginAuth(token);
    }

    @Override
    public void saveEmail(String email) {
        sharedPreferencesDataSource.saveEmail(email);
    }

    @Override
    public void saveUserUid(String uid) {
        sharedPreferencesDataSource.saveUserUid(uid);
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
    public String getUserUid() {
        return sharedPreferencesDataSource.getUserUid();
    }



    @Override
    public void clearAuthData() {
        sharedPreferencesDataSource.clearAuthData();
    }



    // Remote Data Source
    @Override
    public void getRandomMeal(OnRandomMealNetworkCallBack onRandomMealNetworkCallBack) {
        remoteDataSource.getRandomMeal(onRandomMealNetworkCallBack);
    }

    @Override
    public void getCategories(OnCategoriesMealNetworkCallBack onCategoriesMealNetworkCallBack) {
        remoteDataSource.getCategories(onCategoriesMealNetworkCallBack);
    }

    @Override
    public void getAllCountries(OnCountriesMealNetworkCallBack onCountriesMealNetworkCallBack) {
        remoteDataSource.getAllCountries(onCountriesMealNetworkCallBack);
    }

    @Override
    public void getCategoriesList(String category, OnCategoriesListCallBack onCategoriesMealNetworkCallBack) {
        remoteDataSource.getCategoriesList(category, onCategoriesMealNetworkCallBack);
    }

    @Override
    public void getCountriesList(String country, OnCategoriesListCallBack onCategoriesListCallBack) {
        remoteDataSource.getCountriesList(country, onCategoriesListCallBack);
    }

    @Override
    public void getIngredientsDetailed(String ingredient, OnCategoriesListCallBack onCategoriesListCallBack) {
        remoteDataSource.getIngredientsList(ingredient, onCategoriesListCallBack);
    }

    @Override
    public void getIngredients(OnIngredientNetworkCallBack onIngredientNetworkCallBack) {
        remoteDataSource.getIngredients(onIngredientNetworkCallBack);
    }

    @Override
    public void getMealById(String id, OnMealDetailsNetworkCallBack onMealDetailsNetworkCallBack) {
        remoteDataSource.getMealDetailById(id, onMealDetailsNetworkCallBack);
    }


    // Local Data Source
    @Override
    public void addMealToFavourites(MealsItem meal) {
        localDataSource.insertMeal(meal);
    }

    @Override
    public void removeMealFromFavourites(String mealId) {
        localDataSource.deleteMeal(mealId);
    }

    @Override
    public LiveData<List<MealsItem>> getAllFavoriteMeals(String uid) {
        return localDataSource.getAllFavoriteMeals(uid);
    }

    @Override
    public void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback) {
        localDataSource.isMealExistsInFavourite(mealId, uid, callback);
    }
}
