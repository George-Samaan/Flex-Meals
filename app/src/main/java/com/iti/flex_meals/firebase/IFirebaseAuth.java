package com.iti.flex_meals.firebase;

public interface IFirebaseAuth {
    void signInWithGoogle(String idToken, AuthResultCallback callback);

    interface AuthResultCallback {
        void onSuccess(String userId);
        void onFailure(String errorMessage);
    }
}

