package com.iti.flex_meals.homeActivity.planFragment.presenter;

import androidx.lifecycle.Observer;

import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;
import com.iti.flex_meals.homeActivity.planFragment.view.PlanFragment;

import java.util.List;

public class PlanPresenterImpl implements PlanPresenter {
    private final Repository repository;
    private PlanFragment view;

    public PlanPresenterImpl(PlanFragment view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void fetchBreakfastMealsFromRoom() {
        repository.getBreakfastMeals(repository.getUserUid()).observe(view.requireActivity(), new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlans) {
                view.showBreakfastMeals(mealPlans);
            }
        });
    }

    @Override
    public void fetchLunchMealsFromRoom() {
        repository.getLunchMealsForUid(repository.getUserUid()).observe(view.requireActivity(), new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlans) {
                view.showLunchMeals(mealPlans);
            }
        });

    }

    @Override
    public void fetchDinnerMealsFromRoom() {
        repository.getDinnerMealsForUid(repository.getUserUid()).observe(view.requireActivity(), new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlans) {
                view.showDinnerMeals(mealPlans);
            }
        });
    }

    @Override
    public void deleteMeal(String mealId) {
        new Thread(() -> {
            repository.removeMealFromMealPlan(mealId);
            view.OnMealDeleted();
        }).start();
    }


}
