package com.iti.flex_meals.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static void showCustomSnackbar(View view, String message, int backgroundColor, int textColor) {
        Snackbar snackbar = Snackbar.make(view, message, BaseTransientBottomBar.LENGTH_LONG);

        snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), backgroundColor));

        View snackbarView = snackbar.getView();
        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(view.getContext(), textColor));

        snackbar.show();
    }

    public static void showConfirmationDialog(Context context, String title, String message,
                                              DialogInterface.OnClickListener onConfirm,
                                              DialogInterface.OnClickListener onCancel) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", onConfirm)
                .setNegativeButton("Cancel", onCancel)
                .create()
                .show();
    }

    // Overloaded method with default "Cancel" action
    public static void showConfirmationDialog(Context context, String title, String message,
                                              DialogInterface.OnClickListener onConfirm) {
        showConfirmationDialog(context, title, message, onConfirm,
                (dialog, which) -> dialog.dismiss());
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            } else {
                android.net.NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                return activeNetwork != null && activeNetwork.isConnected();
            }
        }
        return false;
    }
}