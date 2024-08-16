package com.iti.flex_meals.db.repository;


import com.iti.flex_meals.db.RemoteData.RemoteDataSource;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesListCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCountriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;


public class RepositoryImpl implements Repository {

    private final SharedPreferencesDataSourceImpl sharedPreferencesDataSource;
    private final RemoteDataSource remoteDataSource;

    public RepositoryImpl(SharedPreferencesDataSourceImpl sharedPreferencesDataSource, RemoteDataSource remoteDataSource) {
        this.sharedPreferencesDataSource = sharedPreferencesDataSource;
        this.remoteDataSource = remoteDataSource;
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


}
