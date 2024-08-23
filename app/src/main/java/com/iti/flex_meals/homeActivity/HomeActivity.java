package com.iti.flex_meals.homeActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
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
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;

public class HomeActivity extends AppCompatActivity {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ImageView menuIcon;
    TextView tv_title;
    NavController navController;
    Repository repository;
    TextView networkBanner;
    private Alerter alerter; // Variable to hold the Alerter
    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean noConnection = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                updateNetworkAlert(noConnection);
            }
        }
    };
    private boolean isConnected = false;

    public void showNetworkErrorAlert() {
        if (alerter == null) {
            alerter = Alerter.create(this)
                    .setTitle(getString(R.string.no_internet_connection))
                    .setText(R.string.please_check_your_network_settings_and_try_again)
                    .setBackgroundColorRes(R.color.colorAccent1) // or use setBackgroundColorInt(int color)
                    .setIcon(R.drawable.wifi_off_)
                    .addButton(getString(R.string.dismiss), com.tapadoo.alerter.R.style.AlertButton, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Alerter.hide();
                        }
                    })
                    .addButton(getString(R.string.retry), com.tapadoo.alerter.R.style.AlertButton, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
                            startActivityForResult(intent, 0);
                        }
                    })
                    .setOnHideListener(new OnHideAlertListener() {
                        @Override
                        public void onHide() {
                            updateNetworkAlert(true);
                        }
                    })
                    .enableInfiniteDuration(true)
                    .enableSwipeToDismiss();
            alerter.show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //init broadcast receiver
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);

        initViews();
        setUpNavigation();
        onMenuIcoClick();
        initPageTitle();
        setDrawerItemListeners();
        repository = new RepositoryImpl(new SharedPreferencesDataSourceImpl(this),
                new RemoteDataSourceImpl(), new LocalDataSourceImpl(getApplication()));
    }


    private boolean isGuestUser() {
        String authToken = repository.getLoginAuth();
        return authToken == null || authToken.isEmpty(); //  authToken is null --> the user is a guest.
    }


    private void initPageTitle() {
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
            if (destinationId == R.id.homeFragment) {
                tv_title.setText(getString(R.string.home));
            } else if (destinationId == R.id.profileFragment) {
                handleGuestUser(getString(R.string.profile));
            } else if (destinationId == R.id.searchFragment) {
                tv_title.setText(getString(R.string.search));
            } else if (destinationId == R.id.favouritesFragment) {
                handleGuestUser(getString(R.string.favourites));
            } else if (destinationId == R.id.planFragment) {
                handleGuestUser(getString(R.string.today_s_meals));
            } else {
                tv_title.setText(R.string._404);
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
        networkBanner = findViewById(R.id.network_error_banner);
    }

    private void showGuestLoginDialog() {
        Utils.showConfirmationDialog(
                this,
                getString(R.string.login_required), getString(R.string.you_need_to_log_in_to_access_this_feature), (dialog, which) -> {
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
            Toast.makeText(this, R.string.guest_user_cannot_log_out, Toast.LENGTH_SHORT).show();
        } else {
            performLogOut();
        }
    }

    private void performLogOut() {
        Utils.showConfirmationDialog(this, getString(R.string.log_out), getString(R.string.are_you_sure_you_want_to_log_out), (dialog, which) -> {
            Toast.makeText(this, R.string.logging_out, Toast.LENGTH_SHORT).show();
            repository.clearAuthData();
            Intent intent = new Intent(this, AuthActivity.class);
            intent.putExtra("navigateTo", "targetFragment");
            startActivity(intent);
            finish();
        });
    }

    private void performExit() {
        Utils.showConfirmationDialog(this, getString(R.string.exit), getString(R.string.are_you_sure_you_want_to_exit), (dialog, which) -> {
            Toast.makeText(this, R.string.exiting_the_app, Toast.LENGTH_SHORT).show();
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

    private void dismissNetworkErrorAlert() {
        if (alerter != null) {
            alerter.hide();
            alerter = null;
        }
    }

    private void updateNetworkAlert(boolean noConnection) {
        runOnUiThread(() -> {
            if (noConnection) {
                showNetworkErrorAlert(); // Show alert if there's no connection
            } else {
                dismissNetworkErrorAlert(); // Dismiss alert if connection is restored
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        dismissNetworkErrorAlert(); // Ensure alert is dismissed on destroy

    }
}

