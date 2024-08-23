package com.iti.flex_meals.favouriteFragment.view;

import static com.iti.flex_meals.utils.Constants.FAVORITE_ID;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.flex_meals.R;
import com.iti.flex_meals.ViewerListActivity.view.OnMealClick;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;
import com.iti.flex_meals.model.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.planFragment.view.OnMealPlanDelete;

import java.util.List;

public class FavouritesFragment extends Fragment implements OnMealClick, OnMealPlanDelete {

    Repository repository;
    RecyclerView recyclerView;
    FavouritesAdapter favouritesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(requireContext()), new RemoteDataSourceImpl(),
                new LocalDataSourceImpl(requireContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.favRecyclerView);
        favouritesAdapter = new FavouritesAdapter(this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(favouritesAdapter);

        repository.getAllFavoriteMeals(repository.getUserUid()).observe(requireActivity(), new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                favouritesAdapter.setFavourites(mealsItems);
                Log.d("TAG", "onChanged: " + mealsItems.size());
            }
        });
    }

    @Override
    public void onMealClick(String id) {
        Intent intent = new Intent(requireContext(), MealDetailedActivity.class);
        intent.putExtra(FAVORITE_ID, id);
        startActivity(intent);
    }

    @Override
    public void onMealDelete(String mealId) {

    }
}
