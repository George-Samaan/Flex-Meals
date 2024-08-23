package com.iti.flex_meals.authActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.iti.flex_meals.R;

public class AuthActivity extends AppCompatActivity {
    private TextView networkErrorBanner;

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


//        networkErrorBanner = findViewById(R.id.network_error_banner);
//        NetworkUtility.getInstance(this).setNetworkChangeListener(this);
//        updateNetworkBanner(NetworkUtility.getInstance(this).getNetworkStatus());
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        NetworkUtility.getInstance(this).setNetworkChangeListener(this);
//        updateNetworkBanner(NetworkUtility.getInstance(this).getNetworkStatus());
//    }

//    private void updateNetworkBanner(boolean isNetworkAvailable) {
//        networkErrorBanner.setVisibility(isNetworkAvailable ? View.GONE : View.VISIBLE);
//    }
//
//    @Override
//    public void onNetworkChange(boolean isNetworkAvailable) {
//        updateNetworkBanner(isNetworkAvailable);
//
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Unregister the network callback to avoid memory leaks
//        NetworkUtility.getInstance(this).unregisterNetworkCallback();
//    }
}
