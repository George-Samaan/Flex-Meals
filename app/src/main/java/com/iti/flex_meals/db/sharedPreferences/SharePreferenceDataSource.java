package com.iti.flex_meals.db.sharedPreferences;

public interface SharePreferenceDataSource {
    void saveLoginAuth(String token);

    void saveUserUid(String uid);

    String getUserUid();

    void saveEmail(String email);

    String getEmail();

    String getLoginAuth();

    void clearAuthData();

}
