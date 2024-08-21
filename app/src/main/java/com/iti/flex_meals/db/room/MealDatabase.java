package com.iti.flex_meals.db.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

@Database(entities = {MealsItem.class, MealPlan.class}, version = 1, exportSchema = false)
public abstract class MealDatabase extends RoomDatabase {
    private static MealDatabase instance;

    public static synchronized MealDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            MealDatabase.class, "meals_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract MealDao mealDao();

    public abstract MealPlanDao mealPlanDao();
}
