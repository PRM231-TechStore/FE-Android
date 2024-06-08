package com.prm391.techstore.features.main.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationBarView;
import com.prm391.techstore.R;
import com.prm391.techstore.features.test.TestFragment;
import com.prm391.techstore.constants.ProductListConstants;
import com.prm391.techstore.databinding.ActivityMainBinding;
import com.prm391.techstore.features.product_list.fragments.ProductListFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(activityMainBinding.getRoot());
            ReplaceFragment(new ProductListFragment());
            SetupActionBar();
            SetupBottomNavBar();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SetupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(ProductListConstants.TITLE_NAME);
    }

    private void SetupBottomNavBar() {
        activityMainBinding.bottomNavigationBar.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int navMenuItemId = item.getItemId();
                        Fragment fragment = null;
                        if (navMenuItemId == R.id.homeNavMenu) {
                            fragment = new ProductListFragment();

                        } else if (navMenuItemId == R.id.cartNavMenu) {
                            fragment = new TestFragment();
                        }
                        ReplaceFragment(fragment);
                        return true;
                    }
                }

        );
    }

    private void ReplaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameLayout, fragment);
        fragmentTransaction.commit();
    }
}

