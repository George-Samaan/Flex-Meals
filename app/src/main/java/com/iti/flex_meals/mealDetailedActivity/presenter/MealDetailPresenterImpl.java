package com.iti.flex_meals.mealDetailedActivity.presenter;

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
                repository.addMealToFavourites(mealDetails);
                view.onMealSaved();
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
                view.onMealRemoved();
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });

    }



}
