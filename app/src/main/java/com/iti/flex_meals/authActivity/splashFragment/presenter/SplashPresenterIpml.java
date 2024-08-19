package com.iti.flex_meals.authActivity.splashFragment.presenter;

import android.util.Log;

import com.iti.flex_meals.authActivity.splashFragment.view.SplashView;
import com.iti.flex_meals.db.repository.Repository;

public class SplashPresenterIpml implements Splash {

    private final SplashView view;
    private final Repository repository;

    public SplashPresenterIpml(SplashView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void nextPage() {
        String token = repository.getLoginAuth();
        if (token != null && !token.isEmpty()) {
            view.navigateToHome();
            Log.d("Token", "token here " + token);
        } else {
            view.navigateToLogin();
            Log.d("Token", "token null  " + token);
        }
    }
}