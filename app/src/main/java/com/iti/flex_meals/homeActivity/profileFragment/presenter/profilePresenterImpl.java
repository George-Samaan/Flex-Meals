package com.iti.flex_meals.homeActivity.profileFragment.presenter;

import android.util.Log;

import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.Task;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.firebase.FireBaseAuthImpl;
import com.iti.flex_meals.firebase.IFirebaseAuth;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;
import com.iti.flex_meals.homeActivity.profileFragment.view.ProfileFragment;

import java.util.List;

public class profilePresenterImpl implements ProfilePresenter {
    ProfileFragment view;
    Repository repository;
    FireBaseAuthImpl fireBaseAuth = new FireBaseAuthImpl();

    public profilePresenterImpl(ProfileFragment view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void backUp() {
        repository.getAllFavoriteMeals(repository.getUserUid()).observe(view.getViewLifecycleOwner(), new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                if (mealsItems != null) {
                    Log.d("JEOOOOOOOO", "onChanged: " + mealsItems.size());
                    fireBaseAuth.uploadFavouriteItems(mealsItems, new IFirebaseAuth.OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Log.d("FireBase", "onCompleteUpload ");
                        }
                    });
                } else {
                    Log.d("JEOOOOOOOO", "onChanged: null");
                }
            }
        });
        repository.getAllMealPlanItems(repository.getUserUid()).observe(view.getViewLifecycleOwner(), new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlanList) {
                if (mealPlanList != null) {
                    fireBaseAuth.uploadMealPlanItems(mealPlanList, new IFirebaseAuth.OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Log.d("FireBase", "onCompleteUploadMealPlan ");
                        }
                    });
                }
            }
        });
    }
}