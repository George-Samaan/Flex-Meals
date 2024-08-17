package com.iti.flex_meals.authActivity.loginFragment.presenter;

public interface LoginPresenter {
    void performGoogleLogin(String idToken, String email);

    void performFirebaseLogin(String email, String password);

    void handleGuestLogin();

}
