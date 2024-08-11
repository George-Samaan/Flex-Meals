package com.iti.flex_meals.splashOne.view;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.iti.flex_meals.R;
import com.iti.flex_meals.splashOne.presenter.SplashPresenter;
import com.iti.flex_meals.splashTwo.view.SplashTwo;

public class SplashActivity extends AppCompatActivity implements SplashView{

    private ProgressBar progressBar;
    private TextView progressText;
    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initViews();
        presenter = new SplashPresenter(this);
        presenter.startProgressAnimation();
    }

    private void initViews() {
        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
    }


    @Override
    public void progressBar(int progress) {
        progressBar.setProgress(progress);
        progressText.setText(progress + "%");
    }

    @Override
    public void navigateToTheNextScreen() {
        startActivity(new Intent(this, SplashTwo.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.stopProgressAnimation();
    }
}