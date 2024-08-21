package com.iti.flex_meals.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

import java.util.List;

@Dao
public interface MealPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealPlan mealPlan);

    @Delete
    void delete(MealPlan mealPlan);

    @Query("SELECT * FROM MEAL_PLAN WHERE idMeal = :id")
    MealPlan getMealById(String id);


    @Query("SELECT * FROM MEAL_PLAN WHERE UID = :uid AND mealType = 'Breakfast'")
    LiveData<List<MealPlan>> getBreakfastForUid(String uid);

    @Query("SELECT * FROM MEAL_PLAN WHERE UID = :uid AND mealType = 'Lunch'")
    LiveData<List<MealPlan>> getLunchMealsForUid(String uid);

    @Query("SELECT * FROM MEAL_PLAN WHERE UID = :uid AND mealType = 'Dinner'")
    LiveData<List<MealPlan>> getDinnerMealsForUid(String uid);

    @Query("SELECT * FROM MEAL_PLAN WHERE UID = :uid AND mealType = 'Breakfast' AND date = :date")
    LiveData<List<MealPlan>> getBreakfastMealsForUidAndDate(String uid, long date);

    @Query("SELECT * FROM MEAL_PLAN WHERE UID = :uid AND mealType = 'Lunch' AND date = :date")
    LiveData<List<MealPlan>> getLunchMealsForUidAndDate(String uid, long date);

    @Query("SELECT * FROM MEAL_PLAN WHERE UID = :uid AND mealType = 'Dinner' AND date = :date")
    LiveData<List<MealPlan>> getDinnerMealsForUidAndDate(String uid, long date);

}
