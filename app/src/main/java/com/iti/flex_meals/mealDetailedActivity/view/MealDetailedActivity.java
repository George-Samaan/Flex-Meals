package com.iti.flex_meals.mealDetailedActivity.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iti.flex_meals.R;
import com.iti.flex_meals.db.RemoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.mealDetailedActivity.presenter.MealDetailPresenter;
import com.iti.flex_meals.mealDetailedActivity.presenter.MealDetailPresenterImpl;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class MealDetailedActivity extends AppCompatActivity implements MealDetailedView {
    String key;
    String randomKey;
    MealDetailPresenter presenter;
    ImageView favClick;
    ImageView mealBackButton;
    TextView mealName;
    TextView mealArea;
    TextView mealCategory;
    TextView mealInstructions;
    YouTubePlayerView youTubePlayerView;
    ImageView mealCover;
    IngredientAdapter adapter;
    RecyclerView recyclerView;
    private boolean isFavorite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mel_detailed);
        presenter = new MealDetailPresenterImpl(this, new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(this), new RemoteDataSourceImpl()));
        getIntentKey();
        initViews();
        checkWhichIdToRun();
        onBackClick();
        initRecyclerView();
        onFavouriteClick();


    }

    private void onFavouriteClick() {
        favClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                testToggleFav();
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void onBackClick() {
        mealBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void checkWhichIdToRun() {
        if (randomKey != null && !randomKey.isEmpty()) {
            presenter.getMealDetail(randomKey);
        }
        if (key != null && !key.isEmpty()) {
            presenter.getMealDetail(key);
        }
    }

    private void getIntentKey() {
        key = getIntent().getStringExtra("MEAL_ID");
        randomKey = getIntent().getStringExtra("RANDOM_MEAL_ID");
    }

    private void initViews() {
        adapter = new IngredientAdapter(new ArrayList<>());
        recyclerView = findViewById(R.id.recView_ingrideints);
        mealName = findViewById(R.id.mealTitle);
        mealInstructions = findViewById(R.id.mealDescription);
        mealArea = findViewById(R.id.countryName);
        mealCategory = findViewById(R.id.mealCategoryName);
        mealCover = findViewById(R.id.mealCover);
        youTubePlayerView = findViewById(R.id.youtube_player);
        mealBackButton = findViewById(R.id.mealBackButton);
        favClick = findViewById(R.id.imv_favourite);
    }

    private void testToggleFav() {
        if (isFavorite) {
            favClick.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent1));
            Toast.makeText(this, "Added to favourites", Toast.LENGTH_SHORT).show();

        } else {
            favClick.setColorFilter(ContextCompat.getColor(this, R.color.colorBackgroundLight));
            Toast.makeText(this, "Removed from favourites", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMealDetails(MealsItem meal) {
        Log.d("meal_name", meal.getStrMeal());
        Glide.with(this).load(meal.getStrMealThumb()).into(mealCover);
        mealName.setText(meal.getStrMeal());
        mealInstructions.setText(meal.getStrInstructions());
        mealArea.setText(meal.getStrArea());
        mealCategory.setText(meal.getStrCategory());
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                String youtubeUrl = meal.getStrYoutube();
                String videoId = null;

                if (youtubeUrl != null && youtubeUrl.contains("=")) {
                    String[] parts = youtubeUrl.split("=");
                    if (parts.length > 1) {
                        videoId = parts[1];
                    }
                }
                if (videoId != null) {
                    youTubePlayer.cueVideo(videoId, 0);
                } else {
                    Toast.makeText(MealDetailedActivity.this, "Error: Invalid YouTube URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
        adapter.setIngredientsAndMeasurements(meal.filterIngredientsAndMeasurements());


    }

    @Override
    public void showError(String errorMssg) {
        Toast.makeText(this, errorMssg, Toast.LENGTH_SHORT).show();
    }
}