package com.iti.flex_meals.homeActivity;

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

public class HomeActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView menuIcon;

    TextView tv_title;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setUpNavigation();
        onMenuIcoClick();
        initPageTitle();
        setDrawerItemListeners();
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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
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
                // Handle Exit action
                Toast.makeText(this, "Exiting the app...", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity, and thus exit the app
                return true;
            } else if (itemId == R.id.logout) {
                // Handle Logout action
                Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
                // Implement logout logic here (e.g., clear user data, navigate to login screen)
                return true;
            } else {
                drawerLayout.closeDrawer(GravityCompat.START);
                return NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item);
            }
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

