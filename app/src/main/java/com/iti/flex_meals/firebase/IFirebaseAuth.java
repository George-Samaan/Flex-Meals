package com.iti.flex_meals.firebase;

public interface IFirebaseAuth {
    void signInWithGoogle(String idToken, AuthResultCallback callback);
    void signInWithEmailAndPassword(String email, String password, AuthResultCallback callback);
    void signUnWithEmailAndPassword(String email, String password, AuthResultCallback callback);


    interface AuthResultCallback {
        void onSuccess(String userId);
        void onFailure(String errorMessage);
    }
}

