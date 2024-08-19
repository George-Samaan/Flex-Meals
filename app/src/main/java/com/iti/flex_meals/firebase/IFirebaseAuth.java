package com.iti.flex_meals.firebase;

public interface IFirebaseAuth {
    void signInWithGoogle(String idToken, AuthResultCallback callback);
    void signInWithEmailAndPassword(String email, String password, AuthResultCallback callback);
    void signUnWithEmailAndPassword(String email, String password, AuthResultCallback callback);
    void getAuthToken(AuthTokenCallback callback);

    void getUserUid(AuthUserUidCallBack callback);


    interface AuthResultCallback {
        void onSuccess(String userId);
        void onFailure(String errorMessage);
    }

    interface AuthTokenCallback {
        void onSuccess(String token);

        void onFailure(String errorMessage);
    }

    interface AuthUserUidCallBack {
        void onSuccess(String uid);
        void onFailure(String errorMessage);
    }
}

