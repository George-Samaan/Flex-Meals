package com.iti.flex_meals.homeFragment.view;

import static com.iti.flex_meals.utils.Constants.CATEGORY_NAME;
import static com.iti.flex_meals.utils.Constants.COUNTRY_NAME;
import static com.iti.flex_meals.utils.Constants.INGREDIENT_DETAIL;
import static com.iti.flex_meals.utils.Constants.INGREDIENT_NAME;
import static com.iti.flex_meals.utils.Constants.RANDOM_MEAL_ID;
import static com.iti.flex_meals.utils.Utils.toastNoInternetOnItemClick;

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
import com.iti.flex_meals.ViewerListActivity.view.ViewerListCategoriesActivity;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeFragment.presenter.HomePresenterImpl;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;
import com.iti.flex_meals.model.pojo.categories.CategoryListItem;
import com.iti.flex_meals.model.pojo.countries.CountryItem;
import com.iti.flex_meals.model.pojo.ingredients.IngredientItem;
import com.iti.flex_meals.model.pojo.randomMeal.RandomMealItem;
import com.iti.flex_meals.utils.Utils;

import java.util.List;

public class HomeFragment extends Fragment implements RandomMealView, OnCategoryClickListener, OnCountryClickListener, OnIngredientClickListener {
    ImageView randomMeal;
    LottieAnimationView lottieRandomImage;
    RecyclerView categoriesRecyclerView, countriesRecyclerView, ingredientsRecyclerView;
    TextView categories, countries, ingredients, seeMore;
    private ProgressBar progressBarCategories, progressBarIngredients, progressBarCountries;
    private HomePresenterImpl homePresenter;
    private CategoriesListAdapter categoriesListAdapter;
    private CountriesListAdapter countriesListAdapter;
    private IngredientsAdapter ingredientsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean isNetworkAvailable;
    private boolean wasNetworkDisconnected = false;
    private boolean isFetchingData = false;
    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean noConnection = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                isNetworkAvailable = !noConnection; // Update network availability status
                updateSwipeRefreshLayout();

                if (wasNetworkDisconnected && isNetworkAvailable) {
                    wasNetworkDisconnected = false;
                    fetchData();
                } else if (!isNetworkAvailable) {
                    wasNetworkDisconnected = true;
                }
            }
        }
    };

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(getContext()),
                        new RemoteDataSourceImpl(), new LocalDataSourceImpl(requireContext())));
        homePresenter.fetchDataFromFirebase();
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
        onSeemMoreClick();
        fetchData();
        onSwipeRefresh();
    }

    private void onSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        updateSwipeRefreshLayout();
    }

    private void updateSwipeRefreshLayout() {
        swipeRefreshLayout.setEnabled(isNetworkAvailable);
    }

    private void fetchData() {
        if (isFetchingData) return;
        homePresenter.showRandomMeal();
        homePresenter.showMealCategories();
        homePresenter.showCountriesList();
        homePresenter.showIngredients();
        homePresenter.showIngredients();
        isFetchingData = false;
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
                if (Utils.isNetworkAvailable(requireContext())) {
                    Intent intent = new Intent(requireContext(), MealDetailedActivity.class);
                    intent.putExtra(RANDOM_MEAL_ID, item.getIdMeal());
                    requireContext().startActivity(intent);
                    Log.d("TAG", "onClick: " + item.getIdMeal());
                } else {
                    toastNoInternetOnItemClick(requireContext());
                }

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
        if (categories.isEmpty()) {
            categoriesRecyclerView.setVisibility(View.GONE);
            this.categories.setVisibility(View.GONE);
        } else {
            categoriesRecyclerView.setVisibility(View.VISIBLE);
            this.categories.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCountriesList(List<CountryItem> countries) {
        Log.d("TAG", "showCountriesList: " + countries.size());
        Log.d("TAG", "showCountriesList: " + countries.get(0).getStrArea());
        countriesListAdapter.setCountries(countries, this);
        countriesListAdapter.notifyDataSetChanged();
        if (countries.isEmpty()) {
            countriesRecyclerView.setVisibility(View.GONE);
            this.countries.setVisibility(View.GONE);
        } else {
            countriesRecyclerView.setVisibility(View.VISIBLE);
            this.countries.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showIngredients(List<IngredientItem> ingredients) {
        Log.d("TAG", "showIngredients: " + ingredients.size());
        ingredientsAdapter.setIngredients(ingredients);
        ingredientsAdapter.notifyDataSetChanged();
        if (ingredients.isEmpty()) {
            ingredientsRecyclerView.setVisibility(View.GONE);
            this.ingredients.setVisibility(View.GONE);
            seeMore.setVisibility(View.GONE);
        } else {
            ingredientsRecyclerView.setVisibility(View.VISIBLE);
            this.ingredients.setVisibility(View.VISIBLE);
            seeMore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCategoryClick(String categoryName) {
        if (!Utils.isNetworkAvailable(requireContext())) {
            toastNoInternetOnItemClick(requireContext());
        } else {
            Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
            intent.putExtra(CATEGORY_NAME, categoryName);
            requireContext().startActivity(intent);
        }
    }

    @Override
    public void onCountryClick(String countryName) {
        if (!Utils.isNetworkAvailable(requireContext())) {
            toastNoInternetOnItemClick(requireContext());
        } else {
            Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
            intent.putExtra(COUNTRY_NAME, countryName);
            requireContext().startActivity(intent);
        }
    }

    private void onSeemMoreClick() {
        seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkAvailable(requireContext())) {
                    toastNoInternetOnItemClick(requireContext());
                } else {
                    Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
                    intent.putExtra(INGREDIENT_NAME, "All Ingredients");
                    requireContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onIngredientClick(String ingredientDetail) {
        if (!Utils.isNetworkAvailable(requireContext())) {
            toastNoInternetOnItemClick(requireContext());

        } else {
            Intent intent = new Intent(requireContext(), ViewerListCategoriesActivity.class);
            intent.putExtra(INGREDIENT_DETAIL, ingredientDetail);
            requireContext().startActivity(intent);
        }
    }
}