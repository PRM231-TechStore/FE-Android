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
import android.widget.ProgressBar;

import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.constants.UserOrdersFragmentConstants;
import com.prm391.techstore.features.orders.adapters.UserOrdersAdapter;
import com.prm391.techstore.models.LoginInfo;
import com.prm391.techstore.models.ProductListResponse;
import com.prm391.techstore.models.UserOrder;
import com.prm391.techstore.models.UserOrdersResponse;
import com.prm391.techstore.utils.StorageUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserOrdersFragment extends Fragment {
    private UserOrdersAdapter userOrdersAdapter;
    private RecyclerView ordersRecyclerView;
    private TechStoreAPIInterface techStoreAPIInterface;
    private View view;
    private List<UserOrder> userOrders;
    private LoginInfo loginInfo;
    private ProgressBar userOrdersProgressBar;
    private LayoutInflater layoutInflater;

    public UserOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.productList_searchMenuItem);
        if (item != null)
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
        InitializeClassVariables();
        GetOrdersFromAPI();
        return this.view;
    }

    private void SetupActionbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(UserOrdersFragmentConstants.USER_ORDERS_TITLE);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_return_to_previous_activity);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void InitializeClassVariables() {
        userOrdersProgressBar = view.findViewById(R.id.userOrdersProgressBar);
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);

        loginInfo = StorageUtils.GetFromStorage("user", loginInfo,
                new TypeToken<LoginInfo>() {
                }.getType(), getContext());
    }

    private void GetOrdersFromAPI() {
        Call<UserOrdersResponse> call = techStoreAPIInterface.getOrdersByUserId(
                loginInfo.getToken(),
                loginInfo.getUserId(),
                UserOrdersFragmentConstants.PAGE_NUMBER,
                UserOrdersFragmentConstants.PAGE_SIZE
        );
        call.enqueue(new Callback<UserOrdersResponse>() {
            @Override
            public void onResponse(Call<UserOrdersResponse> call, Response<UserOrdersResponse> response) {
                userOrdersProgressBar.setVisibility(View.GONE);
                UserOrdersResponse userOrdersResponse = response.body();
                if (userOrdersResponse.getData() != null) {
                    userOrders = userOrdersResponse.getData().getItems();
                    InitializeOrdersRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<UserOrdersResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }

    private void InitializeOrdersRecyclerView() {
        ordersRecyclerView = view.findViewById(R.id.ordersRecyclerView);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userOrdersAdapter = new UserOrdersAdapter(getContext(), userOrders,this);
        ordersRecyclerView.setAdapter(userOrdersAdapter);
    }
}