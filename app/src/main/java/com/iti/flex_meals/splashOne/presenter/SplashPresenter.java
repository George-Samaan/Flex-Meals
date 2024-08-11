package com.iti.flex_meals.splashOne.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;

import androidx.annotation.NonNull;

import com.iti.flex_meals.splashOne.view.SplashView;

public class SplashPresenter {
    private SplashView view;
    private ValueAnimator animator;

    public SplashPresenter(SplashView view) {
        this.view = view;
    }

    public void startProgressAnimation() {
        animator = ValueAnimator.ofInt(0, 100);
        animator.setDuration(2500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                view.progressBar(progress);
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(@NonNull Animator animation, boolean isReverse) {
                super.onAnimationEnd(animation, isReverse);
                view.navigateToTheNextScreen();
            }
        });
        animator.start();
    }

    public void stopProgressAnimation() {
        if (animator != null) {
            animator.cancel();
        }
    }
}
