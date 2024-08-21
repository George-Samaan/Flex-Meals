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
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
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
                new RemoteDataSourceImpl(), new LocalDataSourceImpl(getApplication()));
//        drawerLayout.setScrimColor(Color.argb(50, 0, 0, 0));

    }

    private boolean isGuestUser() {
        // Check if the authentication data is present in shared preferences.
        String authToken = repository.getLoginAuth();
        return authToken == null || authToken.isEmpty(); // Return true if authToken is null or empty, indicating the user is a guest.
    }


    private void initPageTitle() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
            if (destinationId == R.id.homeFragment) {
                tv_title.setText("Home");
            } else if (destinationId == R.id.profileFragment) {
                handleGuestUser("Profile");
            } else if (destinationId == R.id.searchFragment) {
                tv_title.setText("Search");
            } else if (destinationId == R.id.favouritesFragment) {
                handleGuestUser("Favourites");
            } else if (destinationId == R.id.planFragment) {
                handleGuestUser("Today's Meals");
            } else {
                tv_title.setText("404");
            }
        });
    }

    private void handleGuestUser(String pageTitle) {
        if (isGuestUser()) {
            showGuestLoginDialog();
        } else {
            tv_title.setText(pageTitle);
        }
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

    private void showGuestLoginDialog() {
        Utils.showConfirmationDialog(
                this,
                "Login Required", " You need to log in to access this feature.", (dialog, which) -> {
                    Intent intent = new Intent(this, AuthActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }, (dialog, which) -> {
                    dialog.dismiss();
                    navController.popBackStack();
                }
        );
    }


    private void setDrawerItemListeners() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.exit) {
                performExit();
                return true;
            } else if (itemId == R.id.logout) {
                handleLogOut();
                return true;
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
                return NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item);
            }
        });
    }

    private void handleLogOut() {
        if (isGuestUser()) {
            Toast.makeText(this, "Guest user cannot log out.", Toast.LENGTH_SHORT).show();
        } else {
            performLogOut();
        }
    }

    private void performLogOut() {
        Utils.showConfirmationDialog(this, "Log Out", "Are you sure you want to log out?", (dialog, which) -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            repository.clearAuthData();
            Intent intent = new Intent(this, AuthActivity.class);
            intent.putExtra("navigateTo", "targetFragment");
            startActivity(intent);
            finish();
        });
    }

    private void performExit() {
        Utils.showConfirmationDialog(this, "Exit", "Are you sure you want to exit?", (dialog, which) -> {
            Toast.makeText(this, "Exiting the app...", Toast.LENGTH_SHORT).show();
            finishAffinity();
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

