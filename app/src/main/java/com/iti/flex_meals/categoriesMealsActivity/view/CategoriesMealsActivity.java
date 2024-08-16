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
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_meals);

        presenter = new CategoriesListImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(this), new RemoteDataSourceImpl()));
        selectedCategory = getIntent().getStringExtra("CATEGORY_NAME");
        selectedCountry = getIntent().getStringExtra("COUNTRY_NAME");
        initViews();
        toggleTitle();
        backButton.setOnClickListener(v -> finish());
        initRecyclerView();

        // Setup search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                categoriesDetailedAdapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                categoriesDetailedAdapter.filter(newText);
                return true;
            }
        });
        presenter.showCategoriesList(selectedCategory);
        presenter.showCountriesList(selectedCountry);
    }

    private void initRecyclerView() {
        categoriesDetailedAdapter = new CategoriesDetailedAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(categoriesDetailedAdapter);
    }

    private void toggleTitle() {
        if (selectedCategory != null) {
            title.setText(selectedCategory);
        } else if (selectedCountry != null) {
            title.setText(selectedCountry);
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
}
