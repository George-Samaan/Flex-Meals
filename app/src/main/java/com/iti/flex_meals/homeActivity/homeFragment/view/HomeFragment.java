package com.iti.flex_meals.homeActivity.homeFragment.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.iti.flex_meals.R;
import com.iti.flex_meals.categoriesMealsActivity.view.ViewerListCategoriesActivity;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.pojo.categories.CategoryListItem;
import com.iti.flex_meals.db.retrofit.pojo.countries.CountryItem;
import com.iti.flex_meals.db.retrofit.pojo.ingredients.IngredientItem;
import com.iti.flex_meals.db.retrofit.pojo.randomMeal.RandomMealItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeActivity.homeFragment.presenter.HomePresenterImpl;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;
import com.iti.flex_meals.utils.NetworkUtility;

import java.util.List;

public class HomeFragment extends Fragment implements RandomMealView, OnCategoryClickListener, OnCountryClickListener, OnIngredientClickListener {
    ImageView randomMeal;
    LottieAnimationView lottieRandomImage;
    ProgressBar progressBarCategories;
    ProgressBar progressBarIngredients;
    ProgressBar progressBarCountries;
    private HomePresenterImpl homePresenter;
    private CategoriesListAdapter categoriesListAdapter;
    private RecyclerView categoriesRecyclerView;
    private RecyclerView countriesRecyclerView;
    private RecyclerView ingredientsRecyclerView;
    private CountriesListAdapter countriesListAdapter;
    private IngredientsAdapter ingredientsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isNetworkAvailable;
    TextView categories;
    TextView countries;
    TextView ingredients;
    private TextView seeMore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(getContext()),
                        new RemoteDataSourceImpl(), new LocalDataSourceImpl(requireContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }
    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateSwipeRefreshLayout();
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView();
        onSeemMoreClick();
        NetworkUtility.getInstance(requireContext());

        fetchData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtility.getInstance(requireContext()).getNetworkStatus()) {
                    fetchData();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        updateSwipeRefreshLayout();

    }

    private void updateSwipeRefreshLayout() {
        isNetworkAvailable = NetworkUtility.getInstance(requireContext()).getNetworkStatus();
        swipeRefreshLayout.setEnabled(isNetworkAvailable);
        updateDataVisibility();
    }

    @Override
    public void onStart() {
        super.onStart();
        requireContext().registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onStop() {
        super.onStop();
        requireContext().unregisterReceiver(networkChangeReceiver);
    }


    private void updateDataVisibility() {
        boolean isNetworkAvailable = NetworkUtility.getInstance(requireContext()).getNetworkStatus();
        if (isNetworkAvailable) {
            // NETWORK AVAILABLE
            categoriesRecyclerView.setVisibility(View.VISIBLE);
            countriesRecyclerView.setVisibility(View.VISIBLE);
            ingredientsRecyclerView.setVisibility(View.VISIBLE);
            seeMore.setVisibility(View.VISIBLE);
            categories.setVisibility(View.VISIBLE);
            countries.setVisibility(View.VISIBLE);
            ingredients.setVisibility(View.VISIBLE);
            progressBarCategories.setVisibility(View.GONE);
            progressBarIngredients.setVisibility(View.GONE);
            progressBarCountries.setVisibility(View.GONE);
            fetchData();
        } else {
            categoriesRecyclerView.setVisibility(View.GONE);
            countriesRecyclerView.setVisibility(View.GONE);
            ingredientsRecyclerView.setVisibility(View.GONE);
            progressBarCategories.setVisibility(View.GONE);
            progressBarIngredients.setVisibility(View.GONE);
            progressBarCountries.setVisibility(View.GONE);
            seeMore.setVisibility(View.GONE);
//            categories.setVisibility(View.GONE);
            countries.setVisibility(View.GONE);
            ingredients.setVisibility(View.GONE);
        }
    }

    private void fetchData() {
        homePresenter.showRandomMeal();
        homePresenter.showMealCategories();
        homePresenter.showCountriesList();
        homePresenter.showIngredients();
        homePresenter.showIngredients();
    }

    private void initRecyclerView() {
        categoriesListAdapter = new CategoriesListAdapter();
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        categoriesRecyclerView.setAdapter(categoriesListAdapter);

        //countries recycler
        countriesListAdapter = new CountriesListAdapter();
        countriesRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false));
        countriesRecyclerView.setAdapter(countriesListAdapter);

        //ingredients recycler
        ingredientsAdapter = new IngredientsAdapter(false, this);
        ingredientsRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false));
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);
    }

    private void initViews(@NonNull View view) {
        randomMeal = view.findViewById(R.id.imv_single_meal);
        lottieRandomImage = view.findViewById(R.id.lottie_animation_meal);
        progressBarCategories = view.findViewById(R.id.progress_bar_categories);
        progressBarIngredients = view.findViewById(R.id.progress_bar_ingredients);
        progressBarCountries = view.findViewById(R.id.progress_bar_countries);
        categoriesRecyclerView = view.findViewById(R.id.categoryRecView);
        countriesRecyclerView = view.findViewById(R.id.countriesRecView);
        ingredientsRecyclerView = view.findViewById(R.id.ingredientdRecView);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        seeMore = view.findViewById(R.id.seeMoreTxt);
        categories = view.findViewById(R.id.categoriesTextView);
        countries = view.findViewById(R.id.countriesTextView);
        ingredients = view.findViewById(R.id.ingredientsTextView);
    }

    @Override
    public void showRandoMeal(RandomMealItem item) {
        Log.d("TAG", "showRandoMeal: " + item.getStrMeal());
        Glide.with(getContext())
                .load(item.getStrMealThumb())
                .into(randomMeal);
        animation();
        randomMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MealDetailedActivity.class);
                intent.putExtra("RANDOM_MEAL_ID", item.getIdMeal());
                requireContext().startActivity(intent);
                Log.d("TAG", "onClick: " + item.getIdMeal());
            }
        });
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
        lottieRandomImage.setVisibility(View.VISIBLE);
        progressBarCategories.setVisibility(View.VISIBLE);
        progressBarIngredients.setVisibility(View.VISIBLE);
        progressBarCountries.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {
        lottieRandomImage.setVisibility(View.GONE);
        progressBarCategories.setVisibility(View.GONE);
        progressBarIngredients.setVisibility(View.GONE);
        progressBarCountries.setVisibility(View.GONE);
    }

    @Override
    public void showMealCategories(List<CategoryListItem> categories) {
        Log.d("TAG", "showMealCategories: " + categories.size());
        categoriesListAdapter.setCategories(categories, this);
        categoriesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCountriesList(List<CountryItem> countries) {
        Log.d("TAG", "showCountriesList: " + countries.size());
        Log.d("TAG", "showCountriesList: " + countries.get(0).getStrArea());
        countriesListAdapter.setCountries(countries, this);
        countriesListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showIngredients(List<IngredientItem> ingredients) {
        Log.d("TAG", "showIngredients: " + ingredients.size());
        ingredientsAdapter.setIngredients(ingredients);
        ingredientsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(String categoryName) {
        Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
        intent.putExtra("CATEGORY_NAME", categoryName);
        requireContext().startActivity(intent);
    }

    @Override
    public void onCountryClick(String countryName) {
        Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
        intent.putExtra("COUNTRY_NAME", countryName);
        requireContext().startActivity(intent);
    }

    private void onSeemMoreClick() {
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
                intent.putExtra("INGREDIENT_NAME", "All Ingredients");
                requireContext().startActivity(intent);
            }
        });
    }

    @Override
    public void onIngredientClick(String ingredientDetail) {
        Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
        intent.putExtra("INGREDIENT_DETAIL", ingredientDetail);
        requireContext().startActivity(intent);
    }
}