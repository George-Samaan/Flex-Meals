package com.iti.flex_meals.profileFragment.view;

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

import com.github.leandroborgesferreira.loadingbutton.customViews.CircularProgressButton;
import com.iti.flex_meals.R;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.profileFragment.presenter.ProfilePresenter;
import com.iti.flex_meals.profileFragment.presenter.profilePresenterImpl;
import com.iti.flex_meals.utils.Utils;

public class ProfileFragment extends Fragment implements ProfileView {
    CircularProgressButton backupBtn;
    ProfilePresenter presenter;
    TextView emailTv;
    CircularProgressButton sync;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new profilePresenterImpl(this,
                new RepositoryImpl(SharedPreferencesDataSourceImpl.getInstance(getContext()),
                        new RemoteDataSourceImpl(), new LocalDataSourceImpl(requireContext())));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backupBtn = view.findViewById(R.id.backup);
        emailTv = view.findViewById(R.id.textView);
        sync = view.findViewById(R.id.sync);
        onBackUpClick();
        presenter.getEmail();
        onSyncClick();
    }

    private void onSyncClick() {
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkAvailable(requireContext())) {
                    Utils.toastNoInternetOnItemClick(requireContext());
                } else {
                    showLoading();
                    presenter.sync();
                    Toast.makeText(requireContext(), "Syncing", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onBackUpClick() {
        backupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Utils.isNetworkAvailable(requireContext())) {
                    Utils.toastNoInternetOnItemClick(requireContext());
                } else {
                    showLoadingBackup();
                    presenter.backUp();
                    Toast.makeText(requireContext(), "Backing Up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void showEmail(String email) {
        emailTv.setText(email);
        emailTv.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        sync.revertAnimation();
    }

    @Override
    public void showLoading() {
        sync.startAnimation();
    }

    @Override
    public void showLoadingBackup() {
        backupBtn.startAnimation();
    }

    @Override
    public void hideLoadingBackup() {
        backupBtn.revertAnimation();
    }
}