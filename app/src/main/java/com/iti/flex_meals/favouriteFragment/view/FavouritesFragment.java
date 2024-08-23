package com.iti.flex_meals.favouriteFragment.view;

import static com.iti.flex_meals.utils.Constants.FAVORITE_ID;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.flex_meals.R;
import com.iti.flex_meals.ViewerListActivity.view.OnMealClick;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.favouriteFragment.presenter.FavouritePresenter;
import com.iti.flex_meals.favouriteFragment.presenter.FavouritePresenterImpl;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.planFragment.view.OnMealPlanInteraction;

import java.util.List;

public class FavouritesFragment extends Fragment implements OnMealClick, FavouritesView {

    private RecyclerView recyclerView;
    private FavouritesAdapter favouritesAdapter;
    private FavouritePresenter favouritesPresenter;
    private OnMealPlanInteraction onMealPlanInteraction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favouritesPresenter = new FavouritePresenterImpl(this, new RepositoryImpl(
                SharedPreferencesDataSourceImpl.getInstance(requireContext()),
                new RemoteDataSourceImpl(),
                new LocalDataSourceImpl(requireContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecyclerView(view);
        favouritesPresenter.fetchFavouriteMealsByUID();
    }

    private void initRecyclerView(@NonNull View view) {
        recyclerView = view.findViewById(R.id.favRecyclerView);
        favouritesAdapter = new FavouritesAdapter(this, onMealPlanInteraction);
        int spanCount;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 4;
        } else {
            spanCount = 2;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(favouritesAdapter);
    }

    @Override
    public void showFavouriteMeals(List<MealsItem> mealsItems) {
        favouritesAdapter.setFavourites(mealsItems);
        Log.d("TAG", "onChanged: " + mealsItems.size());
    }

    @Override
    public void onMealClick(String id) {
        Intent intent = new Intent(requireContext(), MealDetailedActivity.class);
        intent.putExtra(FAVORITE_ID, id);
        startActivity(intent);
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }
}
