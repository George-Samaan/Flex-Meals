package com.iti.flex_meals.homeActivity.homeFragment.presenter;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCategoriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnCountriesMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnIngredientNetworkCallBack;
import com.iti.flex_meals.db.retrofit.networkCallBack.OnRandomMealNetworkCallBack;
import com.iti.flex_meals.db.retrofit.pojo.categories.CategoryListItem;
import com.iti.flex_meals.db.retrofit.pojo.countries.CountryItem;
import com.iti.flex_meals.db.retrofit.pojo.ingredients.IngredientItem;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;
import com.iti.flex_meals.firebase.FireBaseAuthImpl;
import com.iti.flex_meals.firebase.IFirebaseAuth;
import com.iti.flex_meals.homeActivity.homeFragment.view.HomeFragment;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

public class HomePresenterImpl implements HomePresenter {
    private HomeFragment view;
    private RepositoryImpl repository;
    FireBaseAuthImpl fireBaseAuth = new FireBaseAuthImpl();


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
            public void onSuccess(List<CategoryListItem> categories) {
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

    @Override
    public void showCountriesList() {
        repository.getAllCountries(new OnCountriesMealNetworkCallBack() {
            @Override
            public void onSuccess(List<CountryItem> countries) {
                view.showCountriesList(countries);
            }

            @Override
            public void onError(String message) {
                view.showErrorMessage(message);
            }
        });
    }

    @Override
    public void showIngredients() {
        repository.getIngredients(new OnIngredientNetworkCallBack() {
            @Override
            public void onSuccess(List<IngredientItem> ingredients) {
                view.showIngredients(ingredients);
            }

            @Override
            public void onError(String message) {
                view.showErrorMessage(message);
            }
        });
    }

    @Override
    public void fetchDataFromFirebase() {
        fireBaseAuth.getFavouriteItems(repository.getUserUid(), new IFirebaseAuth.OnCompleteListener<List<MealsItem>>() {
            @Override
            public void onComplete(Task<List<MealsItem>> task) {
                Log.d("FireBaseHome", "onComplete: " + task.getResult());
                for (MealsItem item : task.getResult()) {
                    repository.addMealToFavourites(item);
                }
            }
        });
        fireBaseAuth.getMealPlanItems(repository.getUserUid(), new IFirebaseAuth.OnCompleteListener<List<MealPlan>>() {
            @Override
            public void onComplete(Task<List<MealPlan>> task) {
                Log.d("FireBaseHome", "onComplete: " + task.getResult());
                for (MealPlan item : task.getResult()) {
                    repository.addMealToMealPlan(item);
                }
            }
        });
    }
}
