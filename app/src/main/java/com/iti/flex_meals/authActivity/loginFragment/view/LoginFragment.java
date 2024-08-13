package com.iti.flex_meals.authActivity.loginFragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.iti.flex_meals.authActivity.loginFragment.presenter.LoginPresenter;
import com.iti.flex_meals.authActivity.loginFragment.presenter.LoginPresenterImpl;
import com.iti.flex_meals.firebase.FireBaseAuthImpl;
import com.iti.flex_meals.firebase.IFirebaseAuth;
import com.iti.flex_meals.utils.SnackbarUtils;


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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        initFireBase();
        buildGoogle();
        initViews(view);
        onGoogleClick();
        navigateToRegister();
        onLoginClick();
    }




    private void initFireBase() {
        loginPresenter = new LoginPresenterImpl(this, new FireBaseAuthImpl());
    }
    private void buildGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }
    private void onGoogleClick() {
        googleSignInButton.setOnClickListener(v -> startGoogleSignIn());
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
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (validateInput(email, password)) {
                loginPresenter.performFirebaseLogin(email, password);
            }
        });
    }

    @Override
    public void showLoadingIndicator() {
        btnLogin.startAnimation();

    }

    @Override
    public void hideLoadingIndicator() {
        btnLogin.stopAnimation();
        btnLogin.revertAnimation();
    }

    @Override
    public void onLoginSuccess(String userId, String email) {
        SnackbarUtils.showCustomSnackbar(getView(), "Login Successfully", R.color.colorSuccess, R.color.colorText);
    }

    @Override
    public void onLoginFailure(String message) {
        SnackbarUtils.showCustomSnackbar(getView(), message, R.color.colorSuccess, R.color.colorText);

    }

    @Override
    public boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (!isValidEmail(email)) {
            emailEditText.setError("Invalid email format");
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern)) {
            return true;
        }
        return false;
    }


    @Override
    public void onGoogleLoginSuccess(String userID, String email) {
        SnackbarUtils.showCustomSnackbar(getView(), "Login Successfully", R.color.colorSuccess, R.color.colorText);
    }
    @Override
    public void onGoogleLoginError(String message) {
        SnackbarUtils.showCustomSnackbar(getView(), "Login Failed", R.color.colorError, R.color.colorText);
    }
}