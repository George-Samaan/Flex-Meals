package com.iti.flex_meals.profileFragment.view;

public interface ProfileView {

    void showEmail(String email);

    void hideLoading();
    void showLoading();

    void showLoadingBackup();
    void hideLoadingBackup();
}
