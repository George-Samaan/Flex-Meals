package com.iti.flex_meals.authActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.iti.flex_meals.R;

public class AuthActivity extends AppCompatActivity {
    private static final String TAG = "AuthActivity";
    private TextView networkErrorBanner;
    private ConnectivityManager connectivityManager;
    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_auth_fragment);
        Intent intent = getIntent();
        String navigateTo = intent.getStringExtra("navigateTo");
        if ("targetFragment".equals(navigateTo)) {
            // Navigate to the specific fragment
            navController.navigate(R.id.loginFragment);
        }
        networkErrorBanner = findViewById(R.id.network_error_banner);
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        setupNetworkCallback();
        updateNetworkBanner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNetworkBanner();
    }

    private boolean isNetworkAvailable() {
        if (connectivityManager == null) {
            Log.w(TAG, "ConnectivityManager is null");
            return false;
        }

        Network network = connectivityManager.getActiveNetwork();
        if (network != null) {
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
            Log.d(TAG, "Network: " + network + " Capabilities: " + capabilities);
            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        return false;
    }

    private void updateNetworkBanner() {
        boolean networkAvailable = isNetworkAvailable();
        networkErrorBanner.setVisibility(networkAvailable ? View.GONE : View.VISIBLE);
    }

    private void setupNetworkCallback() {
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                runOnUiThread(() -> {
                    updateNetworkBanner();
                    Log.d(TAG, "Network available");
                });
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                runOnUiThread(() -> {
                    updateNetworkBanner();
                    Log.d(TAG, "Network lost");
                });
            }
        };

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkCallback != null) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        }
    }
}
