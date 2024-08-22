package com.iti.flex_meals.homeActivity.profileFragment.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.iti.flex_meals.R;
import com.iti.flex_meals.db.localData.LocalDataSourceImpl;
import com.iti.flex_meals.db.remoteData.RemoteDataSourceImpl;
import com.iti.flex_meals.db.repository.RepositoryImpl;
import com.iti.flex_meals.db.sharedPreferences.SharedPreferencesDataSourceImpl;
import com.iti.flex_meals.homeActivity.profileFragment.presenter.ProfilePresenter;
import com.iti.flex_meals.homeActivity.profileFragment.presenter.profilePresenterImpl;

public class ProfileFragment extends Fragment implements ProfileView {
    Button backupBtn;
    ;
    ProfilePresenter presenter;
    TextView emailtv;
    Button sync;

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
        emailtv = view.findViewById(R.id.textView);
        sync = view.findViewById(R.id.sync);
        onBackUpClick();
        presenter.getEmail();
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sync();
            }
        });

    }

    private void onBackUpClick() {
        backupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.backUp();
            }
        });
    }

    @Override
    public void showEmail(String email) {
        emailtv.setText(email);
        emailtv.setVisibility(View.VISIBLE);
    }
}