package com.iti.flex_meals.loginFragment.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.flex_meals.R;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeActivity.HomeActivity;
import com.iti.flex_meals.loginFragment.presenter.LoginPresenter;
import com.iti.flex_meals.loginFragment.presenter.LoginPresenterImpl;
import com.iti.flex_meals.network.firebase.FireBaseAuthImpl;
import com.iti.flex_meals.utils.NetworkUtility;
import com.iti.flex_meals.utils.Utils;


public class LoginFragment extends Fragment implements LoginView {
    TextView txt_register;
    NavController navController;
    Button btnSkip;
    TextInputEditText emailEditText, passwordEditText;
    CircularProgressButton btnLogin;
    LoginPresenter loginPresenter;
    private GoogleSignInClient mGoogleSignInClient;
    SignInButton googleSignInButton;
    private final int RC_SIGN_IN = 20;
    boolean isNetworkAvailable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPresenter = new LoginPresenterImpl(this,
                new FireBaseAuthImpl(),
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(getContext()),
                        new RemoteDataSourceImpl(), new LocalDataSourceImpl(requireContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NetworkUtility.getInstance(requireContext());
        buildGoogle();
        initViews(view);
        onGoogleClick();
        onGuestEntranceClick();
        navigateToRegister();
        onLoginClick();
        onBackPressed();
    }

    private void onGuestEntranceClick() {
        btnSkip.setOnClickListener(v -> {
            loginPresenter.handleGuestLogin();
        });
    }

    private void buildGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    private void onGoogleClick() {
        googleSignInButton.setOnClickListener(v -> {
            // Check the network status before starting Google Sign-In
            isNetworkAvailable = NetworkUtility.getInstance(requireContext()).getNetworkStatus();
            if (isNetworkAvailable) {
                startGoogleSignIn();
            } else {
                Toast.makeText(requireContext(), R.string.please_check_your_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void startGoogleSignIn() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginPresenter.performGoogleLogin(account.getIdToken(), account.getEmail());
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initViews(@NonNull View view) {
        googleSignInButton = view.findViewById(R.id.sign_in_google_button);
        navController = Navigation.findNavController(view);
        txt_register = view.findViewById(R.id.txtRegister);
        btnSkip = view.findViewById(R.id.btnSkip);
        emailEditText = view.findViewById(R.id.emailLoginEditTetx);
        passwordEditText = view.findViewById(R.id.passwordLoginEditText);
        btnLogin = view.findViewById(R.id.btnSignIn);
    }

    private void navigateToRegister() {
        txt_register.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private void onLoginClick() {
        btnLogin.setOnClickListener(v -> {
            isNetworkAvailable = NetworkUtility.getInstance(requireContext()).getNetworkStatus();
            if (isNetworkAvailable) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (validateInput(email, password)) {
                    showLoadingIndicator();
                    loginPresenter.performFirebaseLogin(email, password);
                }
            } else {
                Toast.makeText(getContext(), R.string.please_check_your_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showLoadingIndicator() {
        btnLogin.startAnimation();
    }

    @Override
    public void hideLoadingIndicator() {
        btnLogin.revertAnimation();
    }

    @Override
    public void onLoginSuccess(String userId, String email) {
        Utils.showCustomSnackbar(getView(), getString(R.string.login_successfully), R.color.colorSuccess, R.color.colorText);
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onLoginFailure(String message) {
        Utils.showCustomSnackbar(getView(), message, R.color.colorError, R.color.colorText);
    }

    @Override
    public boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError(getString(R.string.email_is_required));
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError(getString(R.string.password_is_required));
            return false;
        }
        if (!isValidEmail(email)) {
            emailEditText.setError(getString(R.string.invalid_email_format));
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailPattern = getString(R.string.emailRegex);
        if (email.matches(emailPattern)) {
            return true;
        }
        return false;
    }


    @Override
    public void onGoogleLoginSuccess(String userID, String email) {
        Utils.showCustomSnackbar(getView(), getString(R.string.login_successfully), R.color.colorSuccess, R.color.colorText);
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onGoogleLoginError(String message) {
        Utils.showCustomSnackbar(getView(), getString(R.string.login_failed), R.color.colorError, R.color.colorText);
    }

    @Override
    public void showGuestDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.guest_entrance)
                .setMessage(R.string.do_you_want_to_continue_as_a_guest)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    private void onBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });
    }
}
