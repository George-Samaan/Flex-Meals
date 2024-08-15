package com.iti.flex_meals.homeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.iti.flex_meals.R;
import com.iti.flex_meals.authActivity.AuthActivity;
import com.iti.flex_meals.db.RemoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.Repository;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.utils.Utils;

public class HomeActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView menuIcon;
    TextView tv_title;
    NavController navController;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setUpNavigation();
        onMenuIcoClick();
        initPageTitle();
        setDrawerItemListeners();
        repository = new RepositoryImpl(new SharedPreferencesDataSourceImpl(this),
                new RemoteDataSourceImpl());
    }


    private void initPageTitle() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
            if (destinationId == R.id.homeFragment) {
                tv_title.setText("Home");
            } else if (destinationId == R.id.profileFragment) {
                tv_title.setText("Profile");
            } else if (destinationId == R.id.searchFragment) {
                tv_title.setText("Search");

            } else if (destinationId == R.id.favouritesFragment) {

                tv_title.setText("Favourites");

            } else if (destinationId == R.id.planFragment) {
                tv_title.setText("Today's Meals");

            } else {
                tv_title.setText("404");
            }
        });
    }

    private void onMenuIcoClick() {
        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    private void setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_auth_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void initViews() {
        navigationView = findViewById(R.id.navigation);
        drawerLayout = findViewById(R.id.main);
        menuIcon = findViewById(R.id.menuIcon);
        tv_title = findViewById(R.id.pagetitle);
    }

    private void setDrawerItemListeners() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.exit) {
                performExit();
                return true;
            } else if (itemId == R.id.logout) {
                performLogOut();
                return true;
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
                return NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item);
            }
        });
    }

    private void performExit() {
        Utils.showConfirmationDialog(this, "Exit", "Are you sure you want to exit?", (dialog, which) -> {
            Toast.makeText(this, "Exiting the app...", Toast.LENGTH_SHORT).show();
            finishAffinity();
        });
    }

    private void performLogOut() {
        Utils.showConfirmationDialog(this, "Log Out", "Are you sure you want to log out?", (dialog, which) -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            repository.clearAuthData();
            Intent intent = new Intent(this, AuthActivity.class);
            intent.putExtra("navigateTo", "targetFragment"); // Optional: Pass extra data if needed
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}

