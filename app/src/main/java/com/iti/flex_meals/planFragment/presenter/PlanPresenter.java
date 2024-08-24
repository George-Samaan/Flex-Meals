package com.iti.flex_meals.planFragment.presenter;

public interface PlanPresenter {

    void fetchBreakfastMealsFromRoom();

    void fetchLunchMealsFromRoom();

    void fetchDinnerMealsFromRoom();

    void deleteMeal(String mealId);

    void fetchMealsByDate(long date); // New method

}
