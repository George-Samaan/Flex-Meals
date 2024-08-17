package com.iti.flex_meals.categoriesMealsActivity.view;

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
import com.iti.flex_meals.categoriesMealsActivity.presenter.CategoriesListImpl;
import com.iti.flex_meals.db.RemoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.pojo.categoriesList.CategoryListDetailed;
import com.iti.flex_meals.db.retrofit.pojo.ingredients.IngredientItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.ingredients.view.IngredientsAdapter;

import java.util.List;

public class CategoriesMealsActivity extends AppCompatActivity implements CategoriesMealView {

    private CategoriesListImpl presenter;
    private String selectedCategory;
    private String x;
    private RecyclerView recyclerView;
    private CategoriesDetailedAdapter categoriesDetailedAdapter;
    private ImageView backButton;
    private SearchView searchView;
    private TextView title;
    private String selectedCountry;
    private String allIngredients;
    private IngredientsAdapter ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_meals);

        presenter = new CategoriesListImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(this), new RemoteDataSourceImpl()));
        selectedCategory = getIntent().getStringExtra("CATEGORY_NAME");
        selectedCountry = getIntent().getStringExtra("COUNTRY_NAME");
        allIngredients = getIntent().getStringExtra("INGREDIENT_NAME");
        initViews();
        toggleTitle();
        backButton.setOnClickListener(v -> finish());

        if (allIngredients != null) {
            // Show ingredients
            initIngredientsRecyclerView();
            presenter.showIngredients();
        } else {
            initCategoriesRecyclerView();
            presenter.showCategoriesList(selectedCategory);
            presenter.showCountriesList(selectedCountry);
        }

//        initRecyclerView();

        // Setup search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (allIngredients != null) {
                    // If ingredients are being displayed
                    if (ingredientsAdapter != null) {
                        ingredientsAdapter.filter(query);
                    }
                } else {
                    // If categories or countries are being displayed
                    if (categoriesDetailedAdapter != null) {
                        categoriesDetailedAdapter.filter(query);
                    }
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (allIngredients != null) {
                    // If ingredients are being displayed
                    if (ingredientsAdapter != null) {
                        ingredientsAdapter.filter(newText);
                    }
                } else {
                    // If categories or countries are being displayed
                    if (categoriesDetailedAdapter != null) {
                        categoriesDetailedAdapter.filter(newText);
                    }
                }
                return true;
            }
        });

    }

    private void initCategoriesRecyclerView() {
        categoriesDetailedAdapter = new CategoriesDetailedAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(categoriesDetailedAdapter);
    }

    private void initIngredientsRecyclerView() {
        ingredientsAdapter = new IngredientsAdapter(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(ingredientsAdapter);
    }

//    private void initRecyclerView() {
//        categoriesDetailedAdapter = new CategoriesDetailedAdapter();
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
//        recyclerView.setAdapter(categoriesDetailedAdapter);
//    }

    private void toggleTitle() {
        if (selectedCategory != null) {
            title.setText(selectedCategory);
        } else if (selectedCountry != null) {
            title.setText(selectedCountry);
        } else if (allIngredients != null) {
            title.setText(allIngredients);
        }
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerview);
        backButton = findViewById(R.id.imgBack);
        searchView = findViewById(R.id.search_view);
        title = findViewById(R.id.title);
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
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

        } else {
            Toast.makeText(this, " No Data", Toast.LENGTH_SHORT).show();
        }
    }
}
