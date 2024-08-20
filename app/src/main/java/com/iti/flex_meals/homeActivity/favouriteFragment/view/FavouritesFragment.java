package com.iti.flex_meals.homeActivity.favouriteFragment.view;

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
import com.iti.flex_meals.categoriesMealsActivity.view.OnMealClick;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;

import java.util.List;

public class FavouritesFragment extends Fragment implements OnMealClick {

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
        favouritesAdapter = new FavouritesAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false));

        repository.getAllFavoriteMeals(repository.getUserUid()).observe(requireActivity(), new Observer<List<MealsItem>>() {
            @Override
            public void onChanged(List<MealsItem> mealsItems) {
                favouritesAdapter.setFavourites(mealsItems);
                Log.d("TAG", "onChanged: " + mealsItems.size());
            }
        });
        recyclerView.setAdapter(favouritesAdapter);
    }

    @Override
    public void onMealClick(String id) {

    }
}