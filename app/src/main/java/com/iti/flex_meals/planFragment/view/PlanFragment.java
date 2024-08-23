package com.iti.flex_meals.planFragment.view;

import static com.iti.flex_meals.utils.Constants.CALENDAR_MEAL_ID;
import static com.iti.flex_meals.utils.Utils.getDateOnly;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.flex_meals.R;
import com.iti.flex_meals.ViewerListActivity.view.OnMealClick;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.favouriteFragment.view.FavouritesAdapter;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;
import com.iti.flex_meals.planFragment.model.MealPlan;
import com.iti.flex_meals.planFragment.presenter.PlanPresenter;
import com.iti.flex_meals.planFragment.presenter.PlanPresenterImpl;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.List;


public class PlanFragment extends Fragment implements PlanView, OnMealClick, OnMealPlanDelete {
    PlanPresenter presenter;
    FavouritesAdapter breakfastItemView, lunchItemView, dinnerItemView;
    RecyclerView recyclerViewBreakfast, recyclerViewLunch, recyclerViewDinner;
    MaterialCalendarView calendarView;
    TextView tvBreakfast, tvLunch, tvDinner;


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
        tvBreakfast = view.findViewById(R.id.tv_breakfast);
        tvLunch = view.findViewById(R.id.tv_lunch);
        tvDinner = view.findViewById(R.id.tv_dinner);
    }

    private void fetchDataByCalendar() {
        calendarView.setSelectedDate(CalendarDay.today());
        Log.d("DateSend", "Date: " + CalendarDay.today().getDate());
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
        Intent intent = new Intent(requireContext(), MealDetailedActivity.class);
        intent.putExtra(CALENDAR_MEAL_ID, id);
        startActivity(intent);
    }

    @Override
    public void showBreakfastMeals(List<MealPlan> breakfastMeals) {
        if (breakfastMeals.isEmpty()) {
            tvBreakfast.setVisibility(View.GONE);
            recyclerViewBreakfast.setVisibility(View.GONE);
        } else {
            tvBreakfast.setVisibility(View.VISIBLE);
            recyclerViewBreakfast.setVisibility(View.VISIBLE);
        }
        breakfastItemView.setBreakfastPlan(breakfastMeals);
        Log.d("MealPlanJeo", "Breakfast Meals: " + breakfastMeals.size());
    }

    @Override
    public void showLunchMeals(List<MealPlan> lunchMeals) {
        if (lunchMeals.isEmpty()) {
            tvLunch.setVisibility(View.GONE);
            recyclerViewLunch.setVisibility(View.GONE);
        } else {
            tvLunch.setVisibility(View.VISIBLE);
            recyclerViewLunch.setVisibility(View.VISIBLE);
        }
        lunchItemView.setLunchPlan(lunchMeals);
        Log.d("MealPlanJeo", "Lunch Meals: " + lunchMeals.size());
    }

    @Override
    public void showDinnerMeals(List<MealPlan> dinnerMeals) {
        if (dinnerMeals.isEmpty()) {
            tvDinner.setVisibility(View.GONE);
            recyclerViewDinner.setVisibility(View.GONE);
        } else {
            tvDinner.setVisibility(View.VISIBLE);
            recyclerViewDinner.setVisibility(View.VISIBLE);
        }
        dinnerItemView.setDinnerPlan(dinnerMeals);
        Log.d("MealPlanJeo", "Dinner Meals: " + dinnerMeals.size());
    }

    @Override
    public void OnMealDeleted() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), R.string.meal_deleted, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMealDelete(String mealId) {
        presenter.deleteMeal(mealId);
    }
}
