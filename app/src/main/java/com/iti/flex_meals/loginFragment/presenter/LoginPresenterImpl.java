package com.iti.flex_meals.loginFragment.presenter;

import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.loginFragment.view.LoginView;
import com.iti.flex_meals.network.firebase.IFirebaseAuth;

public class LoginPresenterImpl implements LoginPresenter {
    LoginView view;
    private IFirebaseAuth firebaseAuth;

    private Repository repository;

    public LoginPresenterImpl(LoginView view, IFirebaseAuth firebaseAuth, Repository repository) {
        this.view = view;
        this.firebaseAuth = firebaseAuth;
        this.repository = repository;
    }

    @Override
    public void performGoogleLogin(String idToken, String email) {
        view.showLoadingIndicator();
        firebaseAuth.signInWithGoogle(idToken, new IFirebaseAuth.AuthResultCallback() {
            @Override
            public void onSuccess(String userId) {
                firebaseAuth.getUserUid(new IFirebaseAuth.AuthUserUidCallBack() {
                    @Override
                    public void onSuccess(String uid) {
                        view.showLoadingIndicator();
                        repository.saveUserUid(uid);
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        view.showLoadingIndicator();
                        view.onGoogleLoginError(errorMessage);
                    }
                });
                view.hideLoadingIndicator();
                repository.saveLoginAuth(idToken);
                repository.saveEmail(email);
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
                firebaseAuth.getAuthToken(new IFirebaseAuth.AuthTokenCallback() {
                    @Override
                    public void onSuccess(String token) {
                        firebaseAuth.getUserUid(new IFirebaseAuth.AuthUserUidCallBack() {
                            @Override
                            public void onSuccess(String uid) {
                                view.showLoadingIndicator();
                                repository.saveUserUid(uid);
                            }
                            @Override
                            public void onFailure(String errorMessage) {
                                view.showLoadingIndicator();
                                view.onLoginFailure(errorMessage);
                            }
                        });
                        view.hideLoadingIndicator();
                        repository.saveLoginAuth(token);
                        repository.saveEmail(email);
                        view.onLoginSuccess(userId, email);
                    }
                    @Override
                    public void onFailure(String errorMessage) {
                        view.hideLoadingIndicator();
                        view.onLoginFailure(errorMessage);
                    }
                });
            }
            @Override
            public void onFailure(String errorMessage) {
                view.hideLoadingIndicator();
                view.onLoginFailure(errorMessage);
            }
        });
    }

    @Override
    public void handleGuestLogin() {
        view.showGuestDialog();
    }

}

