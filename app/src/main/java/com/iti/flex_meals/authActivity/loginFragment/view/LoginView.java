package com.iti.flex_meals.authActivity.loginFragment.view;

public interface LoginView {
    void showLoadingIndicator();
    void hideLoadingIndicator();
    void onLoginSuccess(String userId, String email);
    void onLoginFailure(String message);
    boolean validateInput(String email, String password);
    boolean isValidEmail(String email);

    void onGoogleLoginSuccess(String userID,String email);
    void onGoogleLoginError(String message);
}
