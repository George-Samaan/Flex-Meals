package com.iti.flex_meals.mealDetailedActivity.presenter;

import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.iti.flex_meals.db.localData.OnMealExistsCallback;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.room.MealDao;
import com.iti.flex_meals.db.room.MealDatabase;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.network.retrofit.networkCallBack.OnMealDetailsNetworkCallBack;
import com.iti.flex_meals.planFragment.model.MealPlan;


public class MealDetailPresenterImpl implements MealDetailPresenter {

    private MealDetailedActivity view;
    private Repository repository;
    MealDao mealDao;

    public MealDetailPresenterImpl(MealDetailedActivity view, Repository repository) {
        this.view = view;
        this.repository = repository;
        MealDatabase db = Room.databaseBuilder(view.getApplicationContext(), MealDatabase.class, "meals_database").build();
        mealDao = db.mealDao();
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

//    @Override
//    public void saveMealToFavorites(String id) {
//        repository.getMealById(id, new OnMealDetailsNetworkCallBack() {
//            @Override
//            public void onSuccess(MealsItem mealDetails) {
//                    String userUid = repository.getUserUid();
//                    mealDetails.setUID(userUid);
//                    repository.addMealToFavourites(mealDetails);
//                    view.onMealSaved();
//            }
//            @Override
//            public void onError(String message) {
//                view.showError(message);
//            }
//        });
//    }

    @Override
    public void saveMealToRoom(MealsItem mealsItem) {
        String userUid = repository.getUserUid();
        mealsItem.setUID(userUid);
        repository.addMealToFavourites(mealsItem);
        Log.d("MealDetailPresenter", "Meal saved successfully: " + mealsItem.getStrMeal());
    }


    @Override
    public void saveMealToMealPlan(MealPlan mealPlan) {
        try {
            String userUid = repository.getUserUid();
            mealPlan.setUID(userUid);
            repository.addMealToMealPlan(mealPlan);
            Log.d("MealPlan", "Meal saved successfully: " + mealPlan.getStrMeal());
        } catch (Exception e) {
            Log.e("MealPlan", "Error saving meal plan", e);
        }
    }

    @Override
    public void removeMealFromFavorites(String id) {
        repository.removeMealFromFavourites(id);
        view.onMealRemoved();
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

    @Override
    public void getFavoriteMealDetail(String mealId) {
        repository.getFavoriteMealById(mealId).observe(view.getLifecycleOwner(), new Observer<MealsItem>() {
            @Override
            public void onChanged(MealsItem mealsItem) {
                if (mealsItem != null) {
                    view.showMealDetails(mealsItem);
                } else {
                    view.showError("Meal not found");
                }
            }
        });
    }

    @Override
    public void getMealPlanDetailById(String id) {
        MealPlan mealPlan = repository.getMealById(id);
        if (mealPlan != null) {
            view.showMealPlanDetails(mealPlan);
        } else {
            view.showError("Meal not found");
        }

    }
}
