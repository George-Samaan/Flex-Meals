package com.iti.flex_meals.profileFragment.presenter;

import android.util.Log;

import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.Task;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.network.firebase.FireBaseAuthImpl;
import com.iti.flex_meals.network.firebase.IFirebaseAuth;
import com.iti.flex_meals.planFragment.model.MealPlan;
import com.iti.flex_meals.profileFragment.view.ProfileFragment;

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
        view.showLoadingBackup();
        repository.getAllFavoriteMeals(repository.getUserUid()).observe(view.getViewLifecycleOwner(), new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                if (mealsItems != null && !mealsItems.isEmpty()) {
                    fireBaseAuth.uploadFavouriteItems(mealsItems, new IFirebaseAuth.OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Log.d("FireBase", "onCompleteUpload ");
                            if (task.isSuccessful()) {
                                Log.d("Backup", "Favorite items backed up successfully.");
                            } else {
                                Log.d("Backup", "Failed to back up favorite items.");
                            }
                            view.hideLoadingBackup();
                        }
                    });
                } else {
                    Log.d("Backup", "No favorite meals to back up.");
                    view.hideLoadingBackup();
                }
            }
        });

        repository.getAllMealPlanItems(repository.getUserUid()).observe(view.getViewLifecycleOwner(), new Observer<List<MealPlan>>() {
            @Override
            public void onChanged(List<MealPlan> mealPlanList) {
                if (mealPlanList != null && !mealPlanList.isEmpty()) {
                    fireBaseAuth.uploadMealPlanItems(mealPlanList, new IFirebaseAuth.OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            Log.d("FireBase", "onCompleteUploadMealPlan ");
                            if (task.isSuccessful()) {
                                Log.d("Backup", "Meal plan items backed up successfully.");
                            } else {
                                Log.d("Backup", "Failed to back up meal plan items.");
                            }
                            view.hideLoadingBackup(); // Hide loading after backup
                        }
                    });
                } else {
                    Log.d("Backup", "No meal plans to back up.");
                    view.hideLoadingBackup();
                }
            }
        });
    }

    @Override
    public void sync() {
        view.showLoading();
        fireBaseAuth.getFavouriteItems(repository.getUserUid(), new IFirebaseAuth.OnCompleteListener<List<MealsItem>>() {
            @Override
            public void onComplete(Task<List<MealsItem>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (MealsItem item : task.getResult()) {
                        repository.addMealToFavourites(item);
                        Log.d("Sync", "Added to favorites: " + item.getStrMeal());
                    }
                }
                view.hideLoading();
            }
        });

        fireBaseAuth.getMealPlanItems(repository.getUserUid(), new IFirebaseAuth.OnCompleteListener<List<MealPlan>>() {
            @Override
            public void onComplete(Task<List<MealPlan>> task) {
                if (task.isSuccessful() && task.getResult() != null) {
                    for (MealPlan item : task.getResult()) {
                        repository.addMealToMealPlan(item);
                        Log.d("Sync", "Added to meal plan: " + item.getStrMeal());
                    }
                }
                view.hideLoading();
            }
        });
    }

    @Override
    public void getEmail() {
        String email = repository.getEmail();
        Log.d("JEOOOOOOOO", "getEmail: " + email);
        view.showEmail(email);
    }
}
