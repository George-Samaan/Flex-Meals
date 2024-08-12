package com.iti.flex_meals.authActivity.splashFragment.presenter;

import com.iti.flex_meals.authActivity.splashFragment.view.SplashView;

public class SplashPresenterIpml implements Splash {

    private SplashView view;

    public SplashPresenterIpml(SplashView view) {
        this.view = view;
    }

    @Override
    public void nextPage() {

        view.navigateToLogin();
    }
}
