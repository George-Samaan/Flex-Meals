package com.iti.flex_meals.homeActivity.planFragment.view;

import static com.iti.flex_meals.utils.Utils.getDateOnly;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.flex_meals.R;
import com.iti.flex_meals.categoriesMealsActivity.view.OnMealClick;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeActivity.favouriteFragment.view.FavouritesAdapter;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;
import com.iti.flex_meals.homeActivity.planFragment.presenter.PlanPresenter;
import com.iti.flex_meals.homeActivity.planFragment.presenter.PlanPresenterImpl;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.List;


public class PlanFragment extends Fragment implements PlanView, OnMealClick, OnMealPlanDelete {

    PlanPresenter presenter;
    FavouritesAdapter breakfastItemView;
    FavouritesAdapter lunchItemView;
    FavouritesAdapter dinnerItemView;
    RecyclerView recyclerViewBreakfast;
    RecyclerView recyclerViewLunch;
    RecyclerView recyclerViewDinner;
    MaterialCalendarView calendarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlanPresenterImpl(this, new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(requireContext()),
                new RemoteDataSourceImpl(),
                new LocalDataSourceImpl(requireContext())));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iniViews(view);
        initRecyclerBreakfast();
        initRecyclerLunch();
        initRecyclerDinner();
        fetchTheCurrentDate();
        fetchDataByCalendar();
    }

    private void fetchTheCurrentDate() {
        Calendar currentDate = Calendar.getInstance();
        long dateOnly = getDateOnly(currentDate);
        presenter.fetchMealsByDate(dateOnly);
    }
    private void initRecyclerDinner() {
        dinnerItemView = new FavouritesAdapter(this, this);
        recyclerViewDinner.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewDinner.setAdapter(dinnerItemView);
    }
    private void initRecyclerLunch() {
        recyclerViewBreakfast.setAdapter(breakfastItemView);
        recyclerViewLunch.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewLunch.setAdapter(lunchItemView);
    }
    private void initRecyclerBreakfast() {
        breakfastItemView = new FavouritesAdapter(this, this);
        lunchItemView = new FavouritesAdapter(this, this);
        recyclerViewBreakfast.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
    }
    private void iniViews(@NonNull View view) {
        recyclerViewBreakfast = view.findViewById(R.id.rv_meals);
        recyclerViewLunch = view.findViewById(R.id.rv_lucnh);
        recyclerViewDinner = view.findViewById(R.id.rv_dinner);
        calendarView = view.findViewById(R.id.calendarView);
    }

    private void fetchDataByCalendar() {
        calendarView.setSelectedDate(CalendarDay.today());
        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            if (selected) {
                Calendar currentDate = Calendar.getInstance();
                currentDate.set(date.getYear(), date.getMonth() - 1, date.getDay());
                long dateOnly = getDateOnly(currentDate);
                presenter.fetchMealsByDate(dateOnly);
                Log.d("DateSend", "Date: " + dateOnly);
                breakfastItemView.notifyDataSetChanged();
                lunchItemView.notifyDataSetChanged();
                dinnerItemView.notifyDataSetChanged();
            }

        });
    }


    @Override
    public void onMealClick(String id) {
        // Implementation for meal click handling
    }

    @Override
    public void showBreakfastMeals(List<MealPlan> breakfastMeals) {
        breakfastItemView.setBreakfastPlan(breakfastMeals);
        Log.d("MealPlanJeo", "Breakfast Meals: " + breakfastMeals.size());
    }

    @Override
    public void showLunchMeals(List<MealPlan> lunchMeals) {
        lunchItemView.setLunchPlan(lunchMeals);
        Log.d("MealPlanJeo", "Lunch Meals: " + lunchMeals.size());
    }

    @Override
    public void showDinnerMeals(List<MealPlan> dinnerMeals) {
        dinnerItemView.setDinnerPlan(dinnerMeals);
        Log.d("MealPlanJeo", "Dinner Meals: " + dinnerMeals.size());
    }

    @Override
    public void OnMealDeleted() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Meal Deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMealDelete(String mealId) {
        presenter.deleteMeal(mealId);

    }

}
