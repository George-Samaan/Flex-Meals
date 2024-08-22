package com.iti.flex_meals.mealDetailedActivity.view;


import static com.iti.flex_meals.utils.Utils.getDateOnly;
import static com.iti.flex_meals.utils.Utils.isNetworkAvailable;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.iti.flex_meals.R;
import com.iti.flex_meals.authActivity.AuthActivity;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.retrofit.pojo.mealDetails.MealsItem;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeActivity.planFragment.model.MealPlan;
import com.iti.flex_meals.mealDetailedActivity.presenter.MealDetailPresenter;
import com.iti.flex_meals.mealDetailedActivity.presenter.MealDetailPresenterImpl;
import com.iti.flex_meals.utils.Utils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MealDetailedActivity extends AppCompatActivity implements MealDetailedView {
    String key;
    String favKey;
    String randomKey;
    String calendarKey;
    ImageView calendarBtn;
    MealDetailPresenter presenter;
    ImageView favClick;
    ImageView mealBackButton;
    TextView mealName;
    TextView mealArea;
    TextView mealCategory;
    TextView mealInstructions;
    TextView selectDateTv;
    YouTubePlayerView youTubePlayerView;
    ImageView mealCover;
    IngredientAdapter adapter;
    RecyclerView recyclerView;
    private boolean isFavorite = false;
    TextInputLayout dateInput;
    FloatingActionButton addMeal;
    RadioGroup selectedMealRadioGroup;
    String selectedDate;
    int selectedRadioButtonText;
    RadioButton selectedRadioButton;
    String selectedMeal;
    BottomSheetDialog bottomSheetDialog;
    private MealsItem currentMeal;
    Calendar calendarDateOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MealDatabase db = Room.databaseBuilder(getApplicationContext(), MealDatabase.class, "meals_database").build();
//        mealDao = db.mealDao();
        setContentView(R.layout.activity_mel_detailed);
        presenter = new MealDetailPresenterImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(this),
                        new RemoteDataSourceImpl(),
                        new LocalDataSourceImpl(this)));
        getIntentKey();
        initViews();
        checkWhichIdToRun();
        initRecyclerView();
        checkFavoriteStatus();
        onFavouriteClick();
        onBackClick();
        onCalendarClick();
    }
    private MealPlan mapMealsItemToMealPlan(MealsItem meal) {
        MealPlan mealPlan = new MealPlan();
        mealPlan.setIdMeal(meal.getIdMeal());
        Log.d("meal_name", meal.getStrMeal());
        Log.d("meal_name", meal.getStrMealThumb());
        Log.d("meal_name", meal.getStrInstructions());
        mealPlan.setStrMeal(meal.getStrMeal());
        mealPlan.setStrMealThumb(meal.getStrMealThumb());
        mealPlan.setStrInstructions(meal.getStrInstructions());
        mealPlan.setStrCategory(meal.getStrCategory());
        mealPlan.setStrArea(meal.getStrArea());
        mealPlan.setStrYoutube(meal.getStrYoutube());
        mealPlan.setIngredientsAndMeasurements(meal.filterIngredientsAndMeasurements());
        mealPlan.setMealType(selectedMeal);
        calendarDateOnly = Calendar.getInstance();
        calendarDateOnly.set(Calendar.DAY_OF_MONTH, Integer.parseInt(selectedDate.split("/")[0]));
        calendarDateOnly.set(Calendar.MONTH, Integer.parseInt(selectedDate.split("/")[1]) - 1);
        calendarDateOnly.set(Calendar.YEAR, Integer.parseInt(selectedDate.split("/")[2]));
        long dateOnly = getDateOnly(calendarDateOnly);
        mealPlan.setDate(dateOnly);
        Log.d("Date", "Date Saved in DataBase is: " + String.valueOf(dateOnly));
        return mealPlan;
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                R.style.CustomDatePickerDialog, // Apply custom style here
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date in the calendar
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        selectDateTv.setText(formatDate(calendar));
                        dateInput.setError(null);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show();
    }


    private String formatDate(Calendar calendar) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return formatter.format(calendar.getTime());
    }


    private boolean isValidated() {
        boolean isValid = true;
        if (selectDateTv.getText() == null || selectDateTv.getText().toString().isEmpty()) {
            isValid = false;
            dateInput.setError("Please select date");
        }
        int selectedId = selectedMealRadioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            isValid = false;
            Toast.makeText(this, "Please select meal", Toast.LENGTH_SHORT).show();
        }
        return isValid;
    }

    private void getIntentKey() {
        key = getIntent().getStringExtra("MEAL_ID");
        randomKey = getIntent().getStringExtra("RANDOM_MEAL_ID");
        favKey = getIntent().getStringExtra("FAVORITE_ID");
        calendarKey = getIntent().getStringExtra("CALENDAR_MEAL_ID");
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
        calendarBtn = findViewById(R.id.imv_calendarAdd);
    }

    private void checkWhichIdToRun() {
        if (randomKey != null && !randomKey.isEmpty()) {
            presenter.getMealDetail(randomKey);
        }
        if (key != null && !key.isEmpty()) {
            presenter.getMealDetail(key);
        }
        if (favKey != null && !favKey.isEmpty()) {
            presenter.getFavoriteMealDetail(favKey);
        }
        if (calendarKey != null && !calendarKey.isEmpty()) {
            new Thread(() -> presenter.getMealPlanDetailById(calendarKey)).start();
        }
    }

    private void navigateToStartAsUser() {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showMealDetails(MealsItem meal) {
        currentMeal = meal;
        Log.d("meal_name", meal.getStrMeal());
        Glide.with(this).load(meal.getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mealCover);
        mealName.setText(meal.getStrMeal());
        mealInstructions.setText(meal.getStrInstructions());
        mealArea.setText(meal.getStrArea());
        mealCategory.setText(meal.getStrCategory());
        youtubePlayerView(meal);
        adapter.setIngredientsAndMeasurements(meal.filterIngredientsAndMeasurements());
    }

    private <T> void youtubePlayerView(T meal) {
        try {
            // Use reflection to get the getStrYoutube method
            Method getStrYoutubeMethod = meal.getClass().getMethod("getStrYoutube");
            String youtubeUrl = (String) getStrYoutubeMethod.invoke(meal);

            if (youtubeUrl == null || !youtubeUrl.contains("=") || !isNetworkAvailable(this)) {
                youTubePlayerView.setVisibility(View.GONE);
                Toast.makeText(MealDetailedActivity.this, "Error: No network or Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            } else {
                youTubePlayerView.setVisibility(View.VISIBLE);
                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        super.onReady(youTubePlayer);
                        String videoId = youtubeUrl.split("=")[1];
                        youTubePlayer.cueVideo(videoId, 0);
                    }
                });
            }

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            Toast.makeText(MealDetailedActivity.this, "Error: Unable to retrieve YouTube URL", Toast.LENGTH_SHORT).show();
            youTubePlayerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String errorMssg) {
        Toast.makeText(this, errorMssg, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onMealSaved() {
//        Toast.makeText(MealDetailedActivity.this, "Added to favourites", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onMealRemoved() {
        Toast.makeText(MealDetailedActivity.this, "Removed from favourites", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    public void showMealPlanDetails(MealPlan mealPlan) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("meal_name", mealPlan.getStrMeal());
                Glide.with(MealDetailedActivity.this).load(mealPlan.getStrMealThumb()).into(mealCover);
                Log.d("mealPlanImg", "imagelink" + mealPlan.getStrMealThumb());
                mealName.setText(mealPlan.getStrMeal());
                mealInstructions.setText(mealPlan.getStrInstructions());
                mealArea.setText(mealPlan.getStrArea());
                mealCategory.setText(mealPlan.getStrCategory());
//                youTubePlayerView.setVisibility(View.GONE);
                youtubePlayerView(mealPlan);
                adapter.setIngredientsAndMeasurements(mealPlan.filterIngredientsAndMeasurements());
                calendarBtn.setVisibility(View.GONE);
            }
        });
    }

    private void checkFavoriteStatus() {
        String selectedKey = getSelectedKey();
        if (selectedKey != null && !selectedKey.isEmpty()) {
            presenter.isMealExistsInFavourite(selectedKey, presenter.getUserUid(), exists -> {
                runOnUiThread(() -> {
                    isFavorite = exists;
                    updateImageFavouriteColor(presenter.getUserUid());  // Update the xml fav color based on the favorite status
                });
            });
        }
    }

    private void updateImageFavouriteColor(String mealId) {
        if (isFavorite) {
            favClick.setColorFilter(ContextCompat.getColor(MealDetailedActivity.this, R.color.colorAccent1));
        } else {
            favClick.setColorFilter(ContextCompat.getColor(MealDetailedActivity.this, R.color.colorBackgroundLight));
        }
    }

    private void onFavouriteClick() {
        favClick.setOnClickListener(v -> {
            if (!presenter.checkingCredentialOfUser()) {
                dialogLoginRequired();
                return;
            }
            String selectedKey = getSelectedKey();
            if (selectedKey == null || selectedKey.isEmpty()) {
                Toast.makeText(MealDetailedActivity.this, "Invalid key", Toast.LENGTH_SHORT).show();
                return;
            }
            // Toggle favorite status and update UI accordingly
            if (isFavorite) {
                presenter.removeMealFromFavorites(selectedKey);
                Log.d("jeooo", "mas7thhha");
                favClick.setColorFilter(ContextCompat.getColor(MealDetailedActivity.this, R.color.colorBackgroundLight));
            } else {
                presenter.saveMealToRoom(currentMeal);
                favClick.setColorFilter(ContextCompat.getColor(MealDetailedActivity.this, R.color.colorAccent1));
            }
            isFavorite = !isFavorite;
        });
    }

    private void dialogLoginRequired() {
        Utils.showConfirmationDialog(
                this,
                "Login Required",
                "You must log in to save this meal",
                (dialog, which) -> navigateToStartAsUser(),  // Navigate to login
                (dialog, which) -> dialog.dismiss()  // Dismiss dialog
        );
    }

    private void onCalendarClick() {
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!presenter.checkingCredentialOfUser()) {
                    dialogLoginRequired();
                    return;
                }
                View bottomSheetView = getLayoutInflater().inflate(R.layout.calendar_bottomsheet, null);
                intiBottomSheet(bottomSheetView);
                dateInput.setOnClickListener(v1 -> showDatePicker());
                addMeal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isValidated()) {
                            return;
                        }
                        extractingDataFromBottomSheet();
                        Log.d("jeo", selectedDate);
                        Log.d("jeo", selectedMeal);
                        // i want to save here
                        MealPlan mealPlan = mapMealsItemToMealPlan(currentMeal);
                        presenter.saveMealToMealPlan(mealPlan);    //ehtmal tehtag thread lw 3amal crash tant
                        bottomSheetDialog.dismiss();
                    }

                    private void extractingDataFromBottomSheet() {
                        selectedDate = selectDateTv.getText().toString();
                        selectedRadioButtonText = selectedMealRadioGroup.getCheckedRadioButtonId();
                        selectedRadioButton = bottomSheetView.findViewById(selectedRadioButtonText);
                        selectedMeal = selectedRadioButton.getText().toString();
                    }
                });
            }

            private void intiBottomSheet(View bottomSheetView) {
                bottomSheetDialog = new BottomSheetDialog(MealDetailedActivity.this);
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();
                dateInput = bottomSheetView.findViewById(R.id.select_date_til);
                selectDateTv = bottomSheetView.findViewById(R.id.select_date_tv);
                addMeal = bottomSheetView.findViewById(R.id.add_meal);
                selectedMealRadioGroup = bottomSheetView.findViewById(R.id.select_meal_rg);
            }
        });
    }

    @Nullable
    private String getSelectedKey() {
        String selectedKey = null;

        if (favKey != null && !favKey.isEmpty()) {
            selectedKey = favKey;
        } else if (randomKey != null && !randomKey.isEmpty()) {
            selectedKey = randomKey;
        } else if (key != null && !key.isEmpty()) {
            selectedKey = key;
        } else if (calendarKey != null && !calendarKey.isEmpty()) {
            selectedKey = calendarKey;
        }
        return selectedKey;
    }

    private void onBackClick() {
        mealBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

