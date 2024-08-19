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
    public void deleteMeal(MealsItem meal) {
        new Thread(() -> mealDao.deleteMeal(meal)).start();
    }

    @Override
    public LiveData<List<MealsItem>> getAllFavoriteMeals(String uid) {
        return mealDao.getAllFavoriteMeals(uid);
    }

//    @Override
//    public void updateFavouriteStatus(String id, boolean isFavourite) {
//        new Thread(() -> mealDao.updateFavoriteStatus(id, isFavourite)).start();
//
//    }

    @Override
    public void isMealExistsInFavourite(String mealId, OnMealExistsCallback callback) {
        new Thread(() -> {
            boolean exists = mealDao.isMealExistsInFavourite(mealId);
            callback.onResult(exists);
        }).start();
    }


}
