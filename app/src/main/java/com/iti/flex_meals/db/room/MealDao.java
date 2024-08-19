package com.iti.flex_meals.db.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;

import java.util.List;

@Dao
public interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(MealsItem meal);

    @Delete
    void deleteMeal(MealsItem meal);

    @Query("SELECT * FROM MealDetailsTable WHERE idMeal = :id")
    MealsItem getMealById(String id);


    @Query("Select * from MealDetailsTable")
    LiveData<List<MealsItem>> getAllFavoriteMeals();
}
