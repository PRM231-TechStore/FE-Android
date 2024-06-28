package com.prm391.techstore.features.orders;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.UserOrdersFragmentConstants;


public class UserOrdersFragment extends Fragment {
    public UserOrdersFragment() {
        // Required empty public constructor
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.productList_searchMenuItem);
        if(item!=null)
            item.setVisible(false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SetupActionbar();
        return inflater.inflate(R.layout.fragment_user_orders, container, false);
    }
    private void SetupActionbar(){
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(UserOrdersFragmentConstants.USER_ORDERS_TITLE);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_return_to_previous_activity);
    }
}