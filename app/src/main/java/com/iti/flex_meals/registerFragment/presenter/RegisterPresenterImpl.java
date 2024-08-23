package com.iti.flex_meals.registerFragment.presenter;

import com.iti.flex_meals.network.firebase.IFirebaseAuth;
import com.iti.flex_meals.registerFragment.view.RegisterView;

public class RegisterPresenterImpl implements RegisterPresenter {
    private RegisterView view;
    private IFirebaseAuth firebaseAuth;

    public RegisterPresenterImpl(RegisterView view, IFirebaseAuth firebaseAuth) {
        this.view = view;
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void performRegisterFireBase(String email, String password) {
        view.showLoading();
        firebaseAuth.signUnWithEmailAndPassword(email, password, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String userId) {
                view.hideLoading();
                view.showSuccess();
            }
            @Override
            public void onFailure(String errorMessage) {
                view.hideLoading();
                view.showError(errorMessage);

            }
        });
    }
}
