package com.iti.flex_meals.homeActivity.presenter;

import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.homeActivity.view.HomeActivity;

public class HomePresenterImpl implements HomePresenter {
    private final HomeActivity view;
    private final Repository repository;

    public HomePresenterImpl(HomeActivity view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void checkNetworkConnection(boolean noConnection) {
        if (noConnection) {
            view.showNetworkErrorAlert();
        } else {
            view.dismissNetworkErrorAlert();
        }
    }

    @Override
    public String getLoginToken(String token) {
        return repository.getLoginAuth();
    }

    @Override
    public void clearAuthData() {
        repository.clearAuthData();
    }


    private boolean isGuestUser() {
        String authToken = repository.getLoginAuth();
        return authToken == null || authToken.isEmpty();
    }

}
