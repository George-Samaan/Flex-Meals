package com.iti.flex_meals.splashTwo.view;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.iti.flex_meals.R;

public class SplashTwo extends AppCompatActivity {
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_two);
        imageView = findViewById(R.id.imageView);

        // Load the animation
        Animation zoomAnimation = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out);

        // Start the animation
        imageView.startAnimation(zoomAnimation);
    }

}