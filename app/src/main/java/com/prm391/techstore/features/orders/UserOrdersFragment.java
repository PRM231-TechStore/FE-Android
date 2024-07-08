package com.prm391.techstore.features.orders;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.UserOrdersFragmentConstants;
import com.prm391.techstore.features.orders.adapters.UserOrdersAdapter;


public class UserOrdersFragment extends Fragment {
    private UserOrdersAdapter userOrdersAdapter;
    private RecyclerView ordersRecyclerView;
    private View view;
    private LayoutInflater layoutInflater;
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
        this.view = inflater.inflate(R.layout.fragment_user_orders, container, false);
        this.layoutInflater = inflater;
        SetupActionbar();
        GetOrdersFromAPI();
        InitializeClassVariables();
        return this.view;
    }
    private void SetupActionbar(){
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle(UserOrdersFragmentConstants.USER_ORDERS_TITLE);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_return_to_previous_activity);
    }
    private void GetOrdersFromAPI(){

    }
    private void InitializeClassVariables(){

        InitializeOrdersRecyclerView();

    }
    private void InitializeOrdersRecyclerView(){
        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userOrdersAdapter = new UserOrdersAdapter(getContext(),UserOrdersFragmentConstants.TEMP_USER_ORDERS_LIST);
        ordersRecyclerView.setAdapter(userOrdersAdapter);
    }
}