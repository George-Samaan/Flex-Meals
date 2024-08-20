package com.iti.flex_meals.db.localData;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.db.room.MealDao;
import com.iti.flex_meals.db.room.MealDatabase;

import java.util.List;

public class LocalDataSourceImpl implements LocalDataSource {
    private final MealDao mealDao;

    public LocalDataSourceImpl(Context context) {
        MealDatabase db = MealDatabase.getInstance(context);
        this.mealDao = db.mealDao();
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

}
