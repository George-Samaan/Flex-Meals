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
    private RecyclerView recyclerView;
    private CategoriesDetailedAdapter categoriesDetailedAdapter;
    private ImageView backButton;
    private SearchView searchView;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_meals);

        presenter = new CategoriesListImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(this), new RemoteDataSourceImpl()));
        selectedCategory = getIntent().getStringExtra("CATEGORY_NAME");


        recyclerView = findViewById(R.id.recyclerview);
        backButton = findViewById(R.id.imgBack);
        searchView = findViewById(R.id.search_view);
        title = findViewById(R.id.title);
        title.setText(selectedCategory);

        backButton.setOnClickListener(v -> finish());

        categoriesDetailedAdapter = new CategoriesDetailedAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(categoriesDetailedAdapter);

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
        presenter.showCategoriesList(selectedCategory);  // Fetch and set the categories

    }

    @Override
    public void showCategoriesList(List<CategoryListDetailed> categoryListItemList) {
        categoriesDetailedAdapter.setCategories(categoryListItemList);
        Log.d("TAG", "showCategoriesList: " + categoryListItemList.size());
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
