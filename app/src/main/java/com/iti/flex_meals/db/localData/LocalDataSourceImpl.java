package com.iti.flex_meals.db.localData;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.db.room.MealDao;
import com.iti.flex_meals.db.room.MealDatabase;
import com.iti.flex_meals.db.room.MealPlanDao;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource {
    private final MealDao mealDao;
    private final MealPlanDao mealPlanDao;

    public LocalDataSourceImpl(Context context) {
        MealDatabase db = MealDatabase.getInstance(context);
        this.mealDao = db.mealDao();
        this.mealPlanDao = db.mealPlanDao();
    }

    @Override
    public void insertMeal(MealsItem meal) {
        new Thread(() -> mealDao.insertMeal(meal)).start();
    }

    @Override
    public void deleteMeal(String mealId) {
        new Thread(() -> {
            MealsItem mealToDelete = mealDao.getMealById(mealId);
            if (mealToDelete != null) {
                mealDao.deleteMeal(mealToDelete);
            }
        }).start();
    }

    @Override
    public LiveData<List<MealsItem>> getAllFavoriteMeals(String uid) {
        return mealDao.getAllFavoriteMeals(uid);
    }

    @Override
    public void isMealExistsInFavourite(String mealId, String uid, OnMealExistsCallback callback) {
        new Thread(() -> {
            boolean exists = mealDao.isMealExistsInFavourite(mealId, uid);
            callback.onResult(exists);
        }).start();
    }

    @Override
    public LiveData<MealsItem> getFavoriteMeal(String mealId) {
        return mealDao.getFavoriteMeal(mealId);
    }

    @Override
    public void insertMeal(MealPlan mealPlan) {
        new Thread(() -> mealPlanDao.insert(mealPlan)).start();
    }

    @Override
    public void deletePlanMeal(String mealId) {
        new Thread(() -> {
            MealPlan mealPlan = mealPlanDao.getMealById(mealId);
            if (mealPlan != null) {
                mealPlanDao.delete(mealPlan);
            }
        }).start();
    }

}
