package com.iti.flex_meals.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;

public class NetworkUtility {
    private static NetworkUtility instance;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;
    private boolean isNetworkAvailable;
    private NetworkChangeListener networkChangeListener;

    private NetworkUtility(Context context) {
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        setupNetworkCallback();
        updateNetworkStatus();
    }

    public static synchronized NetworkUtility getInstance(Context context) {
        if (instance == null) {
            instance = new NetworkUtility(context.getApplicationContext());
        }
        return instance;
    }

    public void setNetworkChangeListener(NetworkChangeListener listener) {
        this.networkChangeListener = listener;
    }

    private void setupNetworkCallback() {
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                isNetworkAvailable = true;
                if (networkChangeListener != null) {
                    runOnUiThread(() -> networkChangeListener.onNetworkChange(isNetworkAvailable));
                }
            }

            @Override
            public void onLost(Network network) {
                isNetworkAvailable = false;
                if (networkChangeListener != null) {
                    runOnUiThread(() -> networkChangeListener.onNetworkChange(isNetworkAvailable));
                }
            }
        };

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    private void updateNetworkStatus() {
        isNetworkAvailable = checkNetworkAvailability();
        if (networkChangeListener != null) {
            networkChangeListener.onNetworkChange(isNetworkAvailable);
        }
    }

    public boolean getNetworkStatus() {
        return isNetworkAvailable;
    }

    private boolean checkNetworkAvailability() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
        } else {
            android.net.NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected();
        }
        return false;
    }

    public void unregisterNetworkCallback() {
        if (networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }

    private void runOnUiThread(Runnable action) {
        new android.os.Handler(android.os.Looper.getMainLooper()).post(action);
    }

    public interface NetworkChangeListener {
        void onNetworkChange(boolean isNetworkAvailable);
    }
}
