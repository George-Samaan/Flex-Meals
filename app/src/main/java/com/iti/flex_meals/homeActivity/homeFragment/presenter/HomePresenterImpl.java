package com.iti.flex_meals.homeActivity.homeFragment.presenter;

import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.categories.CategoriesItem;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;
import com.iti.flex_meals.homeActivity.homeFragment.view.HomeFragment;

import java.util.List;

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

    @Override
    public void showMealCategories() {
        view.showLoadingIndicator();
        repository.getCategories(new OnCategoriesMealNetworkCallBack() {
            @Override
            public void onSuccess(List<CategoriesItem> categories) {
                view.hideLoadingIndicator();
                view.showMealCategories(categories);
            }

            @Override
            public void onError(String errorMssg) {
                view.hideLoadingIndicator();
                view.showErrorMessage(errorMssg);
            }
        });
    }
}
