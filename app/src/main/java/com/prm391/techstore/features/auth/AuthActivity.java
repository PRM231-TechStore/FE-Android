package com.prm391.techstore.features.auth;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.prm391.techstore.constants.ProductListConstants;
import com.prm391.techstore.databinding.ActivityAuthBinding;
import com.prm391.techstore.R;
import com.prm391.techstore.databinding.ActivityMainBinding;
import com.prm391.techstore.features.auth.fragments.LoginFragment;
import com.prm391.techstore.features.product_list.fragments.ProductListFragment;
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