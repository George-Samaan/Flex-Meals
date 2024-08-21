package com.iti.flex_meals.homeActivity.planFragment.presenter;

public interface PlanPresenter {

    void fetchBreakfastMealsFromRoom();

    void fetchLunchMealsFromRoom();

    void fetchDinnerMealsFromRoom();
//    void onDestroy();


    void deleteMeal(String mealId);

}
