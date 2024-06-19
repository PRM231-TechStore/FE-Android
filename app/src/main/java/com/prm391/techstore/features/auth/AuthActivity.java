package com.prm391.techstore.features.auth;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.prm391.techstore.databinding.ActivityAuthBinding;
import com.prm391.techstore.R;
import com.prm391.techstore.features.auth.fragments.LoginFragment;
import com.prm391.techstore.utils.FragmentUtils;

public class AuthActivity extends AppCompatActivity {
    ActivityAuthBinding activityAuthBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAuthBinding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(activityAuthBinding.getRoot());
        FragmentUtils.replace(R.id.authFrameLayout,new LoginFragment(),getSupportFragmentManager());
        SetupActionBar();
    }
    private void SetupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

}