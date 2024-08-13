package com.iti.flex_meals.utils;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {

    public static void showCustomSnackbar(View view, String message, int backgroundColor, int textColor) {
        Snackbar snackbar = Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG);

        // Set background color
        snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), backgroundColor));

        // Set text color
        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(view.getContext(), textColor));

        snackbar.show();
    }
}