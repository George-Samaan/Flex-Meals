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
    }
}
