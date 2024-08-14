package com.iti.flex_meals.homeActivity.homeFragment.presenter;

import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;
import com.iti.flex_meals.homeActivity.homeFragment.view.HomeFragment;

public class HomePresenterImpl implements HomePresenter {
    private HomeFragment view;
    private RepositoryImpl repository;

    public HomePresenterImpl(HomeFragment view, RepositoryImpl repository) {
        this.view = view;
        this.repository = repository;
    }


    @Override
    public void showRandomMeal() {
        view.showLoadingIndicator();
        repository.getRandomMeal(new OnRandomMealNetworkCallBack() {
            @Override
            public void onSuccess(RandomMealItem randomMealItem) {
                view.hideLoadingIndicator();
                view.showRandoMeal(randomMealItem);
            }

            @Override
            public void onError(String errorMssg) {
                view.hideLoadingIndicator();
                view.showErrorMessage(errorMssg);
            }
        });
    }
}
