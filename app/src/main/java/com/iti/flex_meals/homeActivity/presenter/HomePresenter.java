package com.iti.flex_meals.homeActivity.presenter;

public interface HomePresenter {
    void checkNetworkConnection(boolean noConnection);
    String getLoginToken(String token);
    void clearAuthData();
}
