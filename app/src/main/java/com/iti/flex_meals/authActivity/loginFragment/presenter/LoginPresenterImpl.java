package com.iti.flex_meals.authActivity.loginFragment.presenter;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.iti.flex_meals.authActivity.loginFragment.view.LoginView;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.firebase.IFirebaseAuth;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

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
                        repository.saveLoginAuth(token); // Save the token
                        repository.saveEmail(email); // Save the email
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

    @Override
    public void fetchDataFromFirebase() {
        firebaseAuth.getFavouriteItems(repository.getUserUid(), new IFirebaseAuth.OnCompleteListener<List<MealsItem>>() {
            @Override
            public void onComplete(Task<List<MealsItem>> task) {
                Log.d("FIREBASE_DATA", "onComplete: " + task.getResult());
                for (MealsItem item : task.getResult()) {
                    repository.addMealToFavourites(item);
                }
            }
        });
        firebaseAuth.getMealPlanItems(repository.getUserUid(), new IFirebaseAuth.OnCompleteListener<List<MealPlan>>() {
            @Override
            public void onComplete(Task<List<MealPlan>> task) {
                Log.d("FIREBASE_DATA", "onComplete: " + task.getResult());
                for (MealPlan item : task.getResult()) {
                    repository.addMealToMealPlan(item);
                }
            }
        });
    }

}

