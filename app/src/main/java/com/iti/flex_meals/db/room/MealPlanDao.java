package com.iti.flex_meals.db.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;

@Dao
public interface MealPlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MealPlan mealPlan);

    @Delete
    void delete(MealPlan mealPlan);

    @Query("SELECT * FROM MEAL_PLAN WHERE idMeal = :id")
    MealPlan getMealById(String id);


}
