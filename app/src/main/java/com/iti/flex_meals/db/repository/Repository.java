package com.iti.flex_meals.db.repository;

public interface Repository {
    void saveLoginAuth(String token);

    void saveEmail(String email);

    String getEmail();

    String getLoginAuth();

    void clearAuthData();
}
