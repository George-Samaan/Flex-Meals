package com.iti.flex_meals.db.repository;

import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;

public class RepositoryImpl implements Repository {

    private final SharedPreferencesDataSourceImpl sharedPreferencesDataSource;

    public RepositoryImpl(SharedPreferencesDataSourceImpl sharedPreferencesDataSource) {
        this.sharedPreferencesDataSource = sharedPreferencesDataSource;
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
}
