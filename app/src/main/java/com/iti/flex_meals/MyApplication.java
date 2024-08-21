package com.iti.flex_meals;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.iti.flex_meals.db.room.MealDatabase;
import com.jakewharton.threetenabp.AndroidThreeTen;

public class MyApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        MealDatabase.getInstance(this);
    }
}