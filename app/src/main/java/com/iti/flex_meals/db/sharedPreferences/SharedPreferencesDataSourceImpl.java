package com.iti.flex_meals.db.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesDataSourceImpl implements SharePreferenceDataSource {
    private static final String KEY_TOKEN = "token";
    private static final String KEY_EMAIL = "email";
    private static final String PREFERENCE_NAME = "shared_preferences";
    private static final String KEY_USER_UID = "user_uid";
    private static SharedPreferencesDataSourceImpl instance;
    private SharedPreferences sharedPreferences;

    public SharedPreferencesDataSourceImpl(Context context) {
        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPreferencesDataSourceImpl getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesDataSourceImpl(context);
        }
        return instance;
    }

    @Override
    public void saveLoginAuth(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, token);
        editor.apply();
    }

    @Override
    public void saveUserUid(String uid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USER_UID, uid);
        editor.apply();
    }

    @Override
    public String getUserUid() {
        return sharedPreferences.getString(KEY_USER_UID, "");
    }

    @Override
    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    @Override
    public String getEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    @Override
    public String getLoginAuth() {
        return sharedPreferences.getString(KEY_TOKEN, "");
    }

    @Override
    public void clearAuthData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
