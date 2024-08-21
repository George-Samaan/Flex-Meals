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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(MealsItem meal);

    @Delete
    void deleteMeal(MealsItem meal);

    @Query("SELECT * FROM MealDetailsTable WHERE idMeal = :id")
    MealsItem getMealById(String id);

    @Query("Select * from MealDetailsTable WHERE UID = :uid")
    LiveData<List<MealsItem>> getAllFavoriteMeals(String uid);

    @Query("SELECT COUNT(*) > 0 FROM MealDetailsTable WHERE idMeal = :mealId AND uid = :uid")
    boolean isMealExistsInFavourite(String mealId, String uid);

    @Query("SELECT * FROM MealDetailsTable WHERE idMeal = :mealId LIMIT 1")
    LiveData<MealsItem> getFavoriteMeal(String mealId);

}
