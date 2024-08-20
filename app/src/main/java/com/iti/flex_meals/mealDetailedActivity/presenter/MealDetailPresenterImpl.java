package com.iti.flex_meals.mealDetailedActivity.presenter;

import com.iti.flex_meals.db.localData.OnMealExistsCallback;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnMealDetailsNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;

public class MealDetailPresenterImpl implements MealDetailPresenter {

    private MealDetailedActivity view;
    private Repository repository;

    public MealDetailPresenterImpl(MealDetailedActivity view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getMealDetail(String id) {
        repository.getMealById(id, new OnMealDetailsNetworkCallBack() {
            @Override
            public void onSuccess(MealsItem mealDetails) {
                view.showMealDetails(mealDetails);
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void saveMealToFavorites(String id) {
        repository.getMealById(id, new OnMealDetailsNetworkCallBack() {
            @Override
            public void onSuccess(MealsItem mealDetails) {
                if (mealDetails.isFavorite()) {
                    view.showMessage("Meal already in favorites");
                } else {
                    String userUid = repository.getUserUid();
                    mealDetails.setUID(userUid);
                    repository.addMealToFavourites(mealDetails);
                    mealDetails.setFavorite(true);
                    view.onMealSaved();
                }
            }
            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }

    @Override
    public void removeMealFromFavorites(String id) {
        repository.getMealById(id, new OnMealDetailsNetworkCallBack() {
            @Override
            public void onSuccess(MealsItem mealDetails) {
                repository.removeMealFromFavourites(mealDetails);
                mealDetails.setFavorite(false);
                view.onMealRemoved();
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });

    }

    @Override
    public void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback) {
        String userUid = repository.getUserUid();  // Retrieve the current user's UID
        repository.isMealExistsInFavourite(mealId, userUid, new OnMealExistsCallback() {
            @Override
            public void onResult(boolean exists) {
                callback.onResult(exists);
            }
        });
    }


    @Override
    public boolean checkingCredentialOfUser() {
        String userUid = repository.getUserUid();
        return userUid != null && !userUid.isEmpty();
    }

    @Override
    public String getUserUid() {
        return repository.getUserUid();
    }

}
