package com.iti.flex_meals.firebase;

import com.google.android.gms.tasks.Task;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

public interface IFirebaseAuth {
    void signInWithGoogle(String idToken, AuthResultCallback callback);
    void signInWithEmailAndPassword(String email, String password, AuthResultCallback callback);
    void signUnWithEmailAndPassword(String email, String password, AuthResultCallback callback);
    void getAuthToken(AuthTokenCallback callback);
    void getUserUid(AuthUserUidCallBack callback);

    void uploadFavouriteItems(List<MealsItem> mealsItemList, OnCompleteListener<Void> onCompleteListener);

    void uploadMealPlanItems(List<MealPlan> mealsItemList, OnCompleteListener<Void> onCompleteListener);

    void getFavouriteItems(String userId, OnCompleteListener<List<MealsItem>> onCompleteListener);





    interface AuthResultCallback {
        void onSuccess(String userId);
        void onFailure(String errorMessage);
    }

    interface AuthTokenCallback {
        void onSuccess(String token);

        void onFailure(String errorMessage);
    }

    interface AuthUserUidCallBack {
        void onSuccess(String uid);
        void onFailure(String errorMessage);
    }

    interface OnCompleteListener<T> {
        void onComplete(Task<T> task);
    }
}

