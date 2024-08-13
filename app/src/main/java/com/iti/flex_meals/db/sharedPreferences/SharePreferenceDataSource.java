package com.iti.flex_meals.db.sharedPreferences;

public interface SharePreferenceDataSource {
    void saveLoginAuth(String token);

    void saveEmail(String email);

    String getEmail();

    String getLoginAuth();

    void clearAuthData();

}
