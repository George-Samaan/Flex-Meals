package com.iti.flex_meals.homeActivity.homeFragment.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.flex_meals.R;
import com.iti.flex_meals.db.RemoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.pojo.categories.CategoriesItem;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeActivity.homeFragment.presenter.HomePresenterImpl;

import java.util.List;

public class HomeFragment extends Fragment implements RandomMealView {
    ImageView randomMeal;
    ProgressBar progressBar;
    private HomePresenterImpl homePresenter;
    private CategoriesListAdapter categoriesListAdapter;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(getContext()),
                        new RemoteDataSourceImpl()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView();
        homePresenter.showRandomMeal();
        homePresenter.showMealCategories();

    }

    private void initRecyclerView() {
        categoriesListAdapter = new CategoriesListAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(categoriesListAdapter);
    }

    private void initViews(@NonNull View view) {
        randomMeal = view.findViewById(R.id.imv_single_meal);
        progressBar = view.findViewById(R.id.progress_bar);
        recyclerView = view.findViewById(R.id.categoryRecView);
    }

    @Override
    public void showRandoMeal(RandomMealItem item) {
        Log.d("TAG", "showRandoMeal: " + item.getStrMeal());
        Glide.with(getContext())
                .load(item.getStrMealThumb())
                .into(randomMeal);
        animation();
    }

    private void animation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(randomMeal, "scaleX", 0f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(randomMeal, "scaleY", 0f, 1f);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(randomMeal, "alpha", 0f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, fadeIn);
        animatorSet.setDuration(1500);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.start();
        Animation zoomAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in_out_home);
        randomMeal.startAnimation(zoomAnimation);
    }

    @Override
    public void showErrorMessage(String message) {
        Log.d("TAG", "showErrorMessage: " + message);
    }

    @Override
    public void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMealCategories(List<CategoriesItem> categories) {
        Log.d("TAG", "showMealCategories: " + categories.size());
        categoriesListAdapter.setCategories(categories);
        categoriesListAdapter.notifyDataSetChanged();
    }
}