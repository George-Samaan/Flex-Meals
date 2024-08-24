package com.iti.flex_meals.favouriteFragment.presenter;

import androidx.lifecycle.Observer;

import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.favouriteFragment.view.FavouritesView;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;

import java.util.List;

public class FavouritePresenterImpl implements FavouritePresenter {
    private final FavouritesView view;
    private final Repository repository;

    public FavouritePresenterImpl(FavouritesView view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void fetchFavouriteMealsByUID() {
        repository.getAllFavoriteMeals(repository.getUserUid()).observe(view.getLifecycleOwner(), new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                view.showFavouriteMeals(mealsItems);
            }
        });
    }
}
