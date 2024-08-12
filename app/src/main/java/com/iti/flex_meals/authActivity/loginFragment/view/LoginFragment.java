package com.iti.flex_meals.authActivity.loginFragment.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.flex_meals.R;
import com.iti.flex_meals.authActivity.loginFragment.presenter.LoginPresenter;
import com.iti.flex_meals.authActivity.loginFragment.presenter.LoginPresenterImpl;
import com.iti.flex_meals.firebase.FireBaseAuthImpl;
import com.iti.flex_meals.firebase.IFirebaseAuth;


public class LoginFragment extends Fragment implements LoginView {

    private final int RC_SIGN_IN = 9001;

    TextView txt_register;
    NavController navController;
    Button btnSkip;
    TextInputEditText emailEditText, passwordEditText;
    CircularProgressButton btnLogin;
    LoginPresenter loginPresenter;
    private GoogleSignInClient mGoogleSignInClient;


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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        SignInButton signInButton = view.findViewById(R.id.sign_in_google_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGoogleSignIn();
            }
        });
        IFirebaseAuth fireBaseAuth = new FireBaseAuthImpl();
        loginPresenter = new LoginPresenterImpl(this, fireBaseAuth,getContext());

        initViews(view);
        navigateToRegister();
    }

    private void startGoogleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("LoginFragment", "onActivityResult called with requestCode: " + requestCode);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginPresenter.performGoogleLogin(account.getIdToken(), account.getEmail());
            } catch (ApiException e) {

                   Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void navigateToRegister() {
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
    }

    private void initViews(@NonNull View view) {
        navController = Navigation.findNavController(view);
        txt_register = view.findViewById(R.id.txtRegister);
        btnSkip = view.findViewById(R.id.btnSkip);
        emailEditText = view.findViewById(R.id.emailLoginEditTetx);
        passwordEditText = view.findViewById(R.id.passwordLoginEditText);
        btnLogin = view.findViewById(R.id.btnSignIn);


    }

    @Override
    public void showLoadingIndicator() {

    }

    @Override
    public void hideLoadingIndicator() {

    }

    @Override
    public void onLoginSuccess(String userId, String email) {

    }

    @Override
    public void onLoginFailure(String message) {

    }

    @Override
    public boolean validateInput(String email, String password) {
        return false;
    }

    @Override
    public boolean isValidEmail(String email) {
        return false;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void onGoogleLoginSuccess(String userID, String email) {
        Snackbar.make(getView(), "Login Successfully", BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void onGoogleLoginError(String message) {
        Snackbar.make(getView(), message, BaseTransientBottomBar.LENGTH_LONG).show();
    }
}
