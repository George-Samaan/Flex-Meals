package com.iti.flex_meals.authActivity.loginFragment.presenter;

import android.content.Context;

import com.iti.flex_meals.authActivity.loginFragment.view.LoginView;
import com.iti.flex_meals.firebase.IFirebaseAuth;

public class LoginPresenterImpl implements LoginPresenter {
    LoginView view;
    private IFirebaseAuth firebaseAuth;
    private Context context;

    public LoginPresenterImpl(LoginView view, IFirebaseAuth firebaseAuth , Context context) {
        this.view = view;
        this.firebaseAuth = firebaseAuth;
        this.context = context;

    }


    @Override
    public void performGoogleLogin(String idToken, String email) {
        firebaseAuth.signInWithGoogle(idToken, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String userId) {
                view.onGoogleLoginSuccess(userId, email);

            }

            @Override
            public void onFailure(String errorMessage) {
                view.onGoogleLoginError(errorMessage);
            }
        });

    }
}
