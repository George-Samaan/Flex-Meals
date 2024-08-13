package com.iti.flex_meals.authActivity.loginFragment.presenter;

import android.content.Context;

import com.iti.flex_meals.authActivity.loginFragment.view.LoginView;
import com.iti.flex_meals.firebase.IFirebaseAuth;

public class LoginPresenterImpl implements LoginPresenter {
    LoginView view;
    private IFirebaseAuth firebaseAuth;
    private Context context;

    public LoginPresenterImpl(LoginView view, IFirebaseAuth firebaseAuth) {
        this.view = view;
        this.firebaseAuth = firebaseAuth;
        this.context = context;

    }


    @Override
    public void performGoogleLogin(String idToken, String email) {
        view.showLoadingIndicator();
        firebaseAuth.signInWithGoogle(idToken, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String userId) {
                view.hideLoadingIndicator();
                view.onGoogleLoginSuccess(userId, email);

            }

            @Override
            public void onFailure(String errorMessage) {
                view.hideLoadingIndicator();
                view.onGoogleLoginError(errorMessage);
            }
        });

    }
    @Override
    public void performFirebaseLogin(String email, String password) {
        view.showLoadingIndicator();
        firebaseAuth.signInWithEmailAndPassword(email, password, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String userId) {
                view.hideLoadingIndicator();
                view.onLoginSuccess(userId, email);
            }
            @Override
            public void onFailure(String errorMessage) {
                view.hideLoadingIndicator();
                view.onLoginFailure(errorMessage);
            }
        });

    }
}
