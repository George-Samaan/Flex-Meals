package com.iti.flex_meals.authActivity.splashFragment.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.iti.flex_meals.R;
import com.iti.flex_meals.authActivity.splashFragment.presenter.Splash;
import com.iti.flex_meals.authActivity.splashFragment.presenter.SplashPresenterIpml;


public class SplashAuthFragment extends Fragment  implements SplashView {
    private static final int SPLASH_TIME_OUT = 3500;

    private Splash presenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenterIpml(this);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_auth, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.nextPage();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void navigateToLogin() {
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_splashAuthFragment_to_loginFragment);
    }
}