package com.prm391.techstore.features.main.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.google.android.material.navigation.NavigationBarView;
import com.prm391.techstore.R;
import com.prm391.techstore.constants.MainActivityConstants;
import com.prm391.techstore.features.cart.fragments.CartFragment;
import com.prm391.techstore.constants.SearchByConstants;
import com.prm391.techstore.databinding.ActivityMainBinding;
import com.prm391.techstore.features.product_list.fragments.ProductListFragment;
import com.prm391.techstore.features.user.fragments.UserFragment;
import com.prm391.techstore.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding activityMainBinding;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private MainActivityViewModel mainActivityViewModel;
    private ProductListFragment productListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            InitializeClassVariables();
            SetupActionBar();
            SetupBottomNavBar();

        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        InitializeSearch(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void InitializeClassVariables() {
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        FragmentUtils.replace(R.id.mainFrameLayout, new ProductListFragment(), getSupportFragmentManager());
        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
    }

    private void InitializeSearch(Menu menu) {
        InitializeSearchMenu(menu);
        InitializeSearchView(menu);
    }

    private void InitializeSearchMenu(Menu menu) {
        searchMenuItem = menu.findItem(R.id.productList_searchMenuItem);
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
    }

    private void InitializeSearchView(Menu menu) {
        searchView = (SearchView) menu.findItem(R.id.productList_searchMenuItem).getActionView();
        searchView.setQueryHint(MainActivityConstants.SEARCH_PLACEHOLDER_TEXT);
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String keyword) {
                LoadProductsListBySearchKeyword(keyword);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnCloseListener(() -> {
            ResetSearch();
            return false;
        });
    }

    private void LoadProductsListBySearchKeyword(String keyword) {
        try {
            productListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.mainFrameLayout);
            if (productListFragment != null) {
                mainActivityViewModel.getSearchTerm().setValue(keyword);
                productListFragment.GetProductsFromAPI();
            }
            searchView.clearFocus(); //prevents the API from being called twice.
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void ResetSearch() {
        try {
            productListFragment = (ProductListFragment) getSupportFragmentManager().findFragmentById(R.id.mainFrameLayout);
            if (productListFragment != null) {
                mainActivityViewModel.ClearAllSearchCategories();
                productListFragment.GetProductsFromAPI();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    private void SetupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(MainActivityConstants.ALL_PRODUCTS_TITLE);
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
                            actionBar.setTitle(MainActivityConstants.ALL_PRODUCTS_TITLE);
                            fragment = new ProductListFragment();

                        } else if (navMenuItemId == R.id.cartNavMenu) {
                            ActionBar actionBar = getSupportActionBar();
                            actionBar.setTitle(MainActivityConstants.CART_TITLE);
                            fragment = new CartFragment();
                        } else if (navMenuItemId == R.id.userNavMenu) {
                            ActionBar actionBar = getSupportActionBar();
                            actionBar.setTitle(MainActivityConstants.USER_PROFILE_TITLE);
                            fragment = new UserFragment();
                        }
                        FragmentUtils.replace(R.id.mainFrameLayout, fragment, getSupportFragmentManager());
                        return true;
                    }
                }

        );
    }
}

