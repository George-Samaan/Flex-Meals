package com.iti.flex_meals.registerFragment.view;

public interface RegisterView {
    void showLoading();
    void hideLoading();
    void showError(String message);
    void showSuccess();

    boolean validateInput(String email, String password, String confirmPassword);
    boolean isEmailValid(String email);
    boolean isValidPassword(String password);
}
