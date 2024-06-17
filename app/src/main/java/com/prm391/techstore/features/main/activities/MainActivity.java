package com.prm391.techstore.features.main.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationBarView;
import com.prm391.techstore.R;
import com.prm391.techstore.features.cart.fragments.CartFragment;
import com.prm391.techstore.features.test.TestFragment;
import com.prm391.techstore.constants.ProductListConstants;
import com.prm391.techstore.databinding.ActivityMainBinding;
import com.prm391.techstore.features.product_list.fragments.ProductListFragment;
import com.prm391.techstore.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(activityMainBinding.getRoot());
            FragmentUtils.replace(R.id.mainFrameLayout,new ProductListFragment(),getSupportFragmentManager());
            SetupActionBar();
            SetupBottomNavBar();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        SetupSearchMenuItem(menu);

        return true;
    }
    private void SetupSearchMenuItem(Menu menu){
        MenuItem searchMenuItem = menu.findItem(R.id.productList_searchMenuItem);
        MenuItem.OnActionExpandListener onActionExpandListener = new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(@NonNull MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(@NonNull MenuItem item) {
                return true;
            }
        };
        searchMenuItem.setOnActionExpandListener(onActionExpandListener);
        SearchView searchView =(SearchView) menu.findItem(R.id.productList_searchMenuItem).getActionView();
        searchView.setQueryHint(ProductListConstants.SEARCH_PLACEHOLDER_TEXT);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
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
                            ActionBar actionBar = getSupportActionBar();
                            actionBar.setTitle(ProductListConstants.TITLE_NAME);
                            fragment = new ProductListFragment();

                        } else if (navMenuItemId == R.id.cartNavMenu) {
                            ActionBar actionBar = getSupportActionBar();
                            actionBar.setTitle("Your Cart");
                            fragment = new CartFragment();
                        }
                        FragmentUtils.replace(R.id.mainFrameLayout,fragment,getSupportFragmentManager());
                        return true;
                    }
                }

        );
    }
}

