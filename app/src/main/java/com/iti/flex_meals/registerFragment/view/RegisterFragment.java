package com.iti.flex_meals.registerFragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.flex_meals.R;
import com.iti.flex_meals.network.firebase.FireBaseAuthImpl;
import com.iti.flex_meals.registerFragment.presenter.RegisterPresenter;
import com.iti.flex_meals.registerFragment.presenter.RegisterPresenterImpl;
import com.iti.flex_meals.utils.Utils;

public class RegisterFragment extends Fragment implements RegisterView {

    private RegisterPresenter presenter;
    ImageView imv_back;
    NavController navController;
    TextInputEditText edt_email, edt_password, edt_confirmPassword;
    CircularProgressButton btn_register;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new RegisterPresenterImpl(this, new FireBaseAuthImpl());
        initViews(view);
        onBackClick();
        onRegisterClick();
    }

    private void initViews(View view) {
        navController = Navigation.findNavController(view);
        imv_back = view.findViewById(R.id.imgBack);
        edt_email = view.findViewById(R.id.emailRegisterEditTetx);
        edt_password = view.findViewById(R.id.passwordRegisterEditText);
        edt_confirmPassword = view.findViewById(R.id.confirmPasswordRegisterEditText);
        btn_register = view.findViewById(R.id.btnRegister);
    }

    private void onBackClick() {
        imv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });
    }

    private void onRegisterClick() {
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isNetworkAvailable(requireContext())) {
                    String email = edt_email.getText().toString();
                    String password = edt_password.getText().toString();
                    String confirmPassword = edt_confirmPassword.getText().toString();
                    if (validateInput(email, password, confirmPassword)) {
                        presenter.performRegisterFireBase(email, password);
                    }
                } else {
                    Toast.makeText(getContext(), R.string.please_check_your_internet_connection, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    public void showLoading() {
        btn_register.startAnimation();
    }

    @Override
    public void hideLoading() {
        btn_register.stopAnimation();
        btn_register.revertAnimation();
        btn_register.dispose();
    }

    @Override
    public void showError(String message) {
        Utils.showCustomSnackBar(getView(), message, R.color.colorAccent1, R.color.colorText);

    }

    @Override
    public void showSuccess() {
        Utils.showCustomSnackBar(getView(), getString(R.string.register_success), R.color.colorSuccess, R.color.colorText);
        navController.popBackStack();
    }

    @Override
    public boolean validateInput(String email, String password, String confirmPassword) {
        if (email.isEmpty()) {
            edt_email.setError(getString(R.string.email_is_required));
            return false;
        }
        if (password.isEmpty()) {
            edt_password.setError(getString(R.string.password_is_required));
            return false;
        }
        if (!password.equals(confirmPassword)) {
            edt_confirmPassword.setError(getString(R.string.password_doesn_t_match));
            return false;
        }
        if (!isEmailValid(email)) {
            edt_email.setError(getString(R.string.invalid_email_format));
            return false;
        }
        if (!isValidPassword(password)) {
            edt_password.setError(getString(R.string.password_must_be_at_least_4_characters_long_include_one_digit_one_uppercase_letter_one_special_character_and_no_spaces));
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmailValid(String email) {
        String emailPattern = getString(R.string.emailRegex);
        return email.matches(emailPattern);
    }

    @Override
    public boolean isValidPassword(String password) {
        String passwordPattern = getString(R.string.passwordRegex);
        return password.matches(passwordPattern);
    }
}