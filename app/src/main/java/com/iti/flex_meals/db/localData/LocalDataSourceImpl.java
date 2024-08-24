package com.iti.flex_meals.db.localData;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.room.MealDao;
import com.iti.flex_meals.db.room.MealDatabase;
import com.iti.flex_meals.db.room.MealPlanDao;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.planFragment.model.MealPlan;

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
    public MealPlan getMealById(String id) {
        return mealPlanDao.getMealById(id);
    }


    @Override
    public LiveData<List<MealsItem>> getAllFavoriteMeals(String uid) {
        return mealDao.getAllFavoriteMeals(uid);
    }

    @Override
    public LiveData<List<MealPlan>> getAllMealPlanItems(String uid) {
        return mealPlanDao.getAllMealsForUid(uid);
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

    @Override
    public LiveData<List<MealPlan>> getBreakfastMeals(String uid) {
        return mealPlanDao.getBreakfastForUid(uid);
    }

    @Override
    public LiveData<List<MealPlan>> getLunchMealsForUid(String uid) {
        return mealPlanDao.getLunchMealsForUid(uid);
    }

    @Override
    public LiveData<List<MealPlan>> getDinnerMealsForUid(String uid) {
        return mealPlanDao.getDinnerMealsForUid(uid);
    }

    @Override
    public LiveData<List<MealPlan>> getBreakfastMealsByUidAndDate(String uid, long date) {
        return mealPlanDao.getBreakfastMealsForUidAndDate(uid, date);
    }

    @Override
    public LiveData<List<MealPlan>> getLunchMealsByUidAndDate(String uid, long date) {
        return mealPlanDao.getLunchMealsForUidAndDate(uid, date);
    }

    @Override
    public LiveData<List<MealPlan>> getDinnerMealsByUidAndDate(String uid, long date) {
        return mealPlanDao.getDinnerMealsForUidAndDate(uid, date);
    }

}
