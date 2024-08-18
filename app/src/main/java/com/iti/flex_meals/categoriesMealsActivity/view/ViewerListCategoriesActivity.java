package com.iti.flex_meals.categoriesMealsActivity.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.flex_meals.R;
import com.iti.flex_meals.categoriesMealsActivity.presenter.CategoriesList;
import com.iti.flex_meals.categoriesMealsActivity.presenter.CategoriesListImpl;
import com.iti.flex_meals.db.RemoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.pojo.categoriesList.CategoryListDetailed;
import com.iti.flex_meals.db.retrofit.pojo.ingredients.IngredientItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeActivity.homeFragment.view.IngredientsAdapter;
import com.iti.flex_meals.homeActivity.homeFragment.view.OnIngredientClickListener;
import com.iti.flex_meals.mealDetailedActivity.view.MealDetailedActivity;

import java.util.List;

public class ViewerListCategoriesActivity extends AppCompatActivity implements CategoriesMealView, OnIngredientClickListener, OnMealClick {
    private CategoriesList presenter;
    private String selectedCategory;
    private RecyclerView recyclerView;
    private CategoriesDetailedAdapter categoriesDetailedAdapter;
    private ImageView backButton;
    private SearchView searchView;
    private TextView title;
    private String selectedCountry;
    private String allIngredients;
    private String ingredientDetail;
    private IngredientsAdapter ingredientsAdapter;
    private List<IngredientItem> savedIngredientsList; // Save the list of all ingredients
    private boolean isViewingDetail = false; // Flag to track whether you're viewing a detail

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_list_viewer);
        presenter = new CategoriesListImpl(this, new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(this), new RemoteDataSourceImpl()));
        getIntentKeys();
        initViews();
        toggleTitle();
        initPresenters();
        onSearchQueryListener();
        onBackButtonClick();
    }

    private void getIntentKeys() {
        selectedCategory = getIntent().getStringExtra("CATEGORY_NAME");
        selectedCountry = getIntent().getStringExtra("COUNTRY_NAME");
        ingredientDetail = getIntent().getStringExtra("INGREDIENT_DETAIL");
        allIngredients = getIntent().getStringExtra("INGREDIENT_NAME");
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerview);
        backButton = findViewById(R.id.imgBack);
        searchView = findViewById(R.id.search_view);
        title = findViewById(R.id.title);
    }

    private void toggleTitle() {
        if (selectedCategory != null) {
            title.setText(selectedCategory);
        } else if (selectedCountry != null) {
            title.setText(selectedCountry);
        } else if (allIngredients != null) {
            title.setText(allIngredients);
        } else if (ingredientDetail != null) {
            title.setText(ingredientDetail);
        }
    }

    private void initPresenters() {
        if (allIngredients != null) {
            // Show ingredients
            initIngredientsRecyclerView();
            presenter.showIngredients();
        } else {
            initCategoriesRecyclerView();
            presenter.showCategoriesList(selectedCategory);
            presenter.showCountriesList(selectedCountry);
            presenter.showIngredientsDetails(ingredientDetail);
        }
    }

    private void onSearchQueryListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String query) {
        if (isViewingDetail) {
            // If viewing a detailed ingredient, filter the detailed view
            if (categoriesDetailedAdapter != null) {
                categoriesDetailedAdapter.filter(query);
            }
        } else if (allIngredients != null) {
            // If in the general ingredients view, filter the ingredients
            if (ingredientsAdapter != null) {
                ingredientsAdapter.filter(query);
            }
        } else {
            // Otherwise, filter the categories or countries list
            if (categoriesDetailedAdapter != null) {
                categoriesDetailedAdapter.filter(query);
            }
        }
    }

    private void initCategoriesRecyclerView() {
        categoriesDetailedAdapter = new CategoriesDetailedAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(categoriesDetailedAdapter);
    }

    private void initIngredientsRecyclerView() {
        ingredientsAdapter = new IngredientsAdapter(true, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(ingredientsAdapter);
    }

    @Override
    public void showCategoriesList(List<CategoryListDetailed> categoryListItemList) {
        if (categoryListItemList != null) {
            categoriesDetailedAdapter.setCategories(categoryListItemList);
            Log.d("TAG", "showCategoriesList: " + categoryListItemList.size());
        } else {
//            Toast.makeText(this, " No Data", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void showCountriesList(List<CategoryListDetailed> countriesList) {
        if (countriesList != null) {
            Log.d("TAG", "showCountriesList: " + countriesList.size());
            categoriesDetailedAdapter.setCategories(countriesList);
        } else {
//            Toast.makeText(this, " No Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showIngredients(List<IngredientItem> ingredients) {
        if (ingredients != null) {
            Log.d("TAG", "showIngredientsList: " + ingredients.size());
            ingredientsAdapter.setIngredients(ingredients);
            savedIngredientsList = ingredients; // Save the list of all ingredients

        } else {
//            Toast.makeText(this, " No Data", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showIngredientsDetails(List<CategoryListDetailed> ingredients) {
        if (ingredients != null) {
            Log.d("TAG", "showIngredientsDetails: " + ingredients.size());
            initCategoriesRecyclerView();
            categoriesDetailedAdapter.setCategories(ingredients);
        } else {
            Log.e("CategoriesMealsActivity", "Received null ingredients list");
        }
    }

    @Override
    public void onIngredientClick(String ingredientDetail) {
        Log.d("TAG", "onIngredientClick: " + ingredientDetail);
        if (ingredientDetail != null) {
            title.setText(ingredientDetail);
            initCategoriesRecyclerView();
            isViewingDetail = true; // Set flag to true when viewing detail
            presenter.showIngredientsDetails(ingredientDetail); // Call presenter to show ingredient details
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void onBackButtonClick() {
        backButton.setOnClickListener(v -> {
            if (isViewingDetail) {
                // If viewing detail, go back to showing all ingredients
                title.setText(allIngredients); // Set the title back to "All Ingredients"
                isViewingDetail = false; // No longer viewing a detail

                // Reset the RecyclerView to show the original list of ingredients
                initIngredientsRecyclerView(); // Reinitialize the ingredients RecyclerView
                ingredientsAdapter.setIngredients(savedIngredientsList); // Restore the full list
            } else {
                // If not viewing a detail, go back as usual
                finish(); // Close the activity
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isViewingDetail) {
            // If viewing detail, go back to showing all ingredients
            title.setText(allIngredients); // Set the title back to "All Ingredients"
            isViewingDetail = false; // No longer viewing a detail

            // Reset the RecyclerView to show the original list of ingredients
            initIngredientsRecyclerView(); // Reinitialize the ingredients RecyclerView
            ingredientsAdapter.setIngredients(savedIngredientsList); // Restore the full list
        } else {
            // If not viewing a detail, go back as usual
            super.onBackPressed(); // This will close the activity
        }
    }

    @Override
    public void onMealClick(String id) {
        Intent intent = new Intent(this, MealDetailedActivity.class);
        intent.putExtra("MEAL_ID", id);
        startActivity(intent);
    }
}
