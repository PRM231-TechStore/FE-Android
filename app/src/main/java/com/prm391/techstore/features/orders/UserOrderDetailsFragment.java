package com.prm391.techstore.features.orders;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.constants.UserOrdersFragmentConstants;
import com.prm391.techstore.features.orders.adapters.UserOrderDetailsAdapter;
import com.prm391.techstore.features.orders.adapters.UserOrdersAdapter;
import com.prm391.techstore.models.LoginInfo;
import com.prm391.techstore.models.UserOrderDetailsResponse;
import com.prm391.techstore.models.UserOrdersResponse;
import com.prm391.techstore.utils.StorageUtils;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserOrderDetailsFragment extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private TextView orderNoTextView;
    private TextView orderDateTextView;
    private TextView itemsCountTextView;
    private UserOrderDetailsAdapter userOrderDetailsAdapter;
    private RecyclerView orderDetailsRecyclerView;
    private TechStoreAPIInterface techStoreAPIInterface;
    private ProgressBar progressBar;
    private String userOrderDetailsId;
    private List<UserOrderDetailsResponse.Data.UserOrderDetails> userOrderDetails;
    private LoginInfo loginInfo;

    public UserOrderDetailsFragment() {
        // Required empty public constructor
    }
    public static UserOrderDetailsFragment newInstance(String userOrderDetailsId) {
        UserOrderDetailsFragment fragment = new UserOrderDetailsFragment();
        Bundle args = new Bundle();
        args.putString("userOrderDetailsId", userOrderDetailsId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.layoutInflater = inflater;
        this.view = inflater.inflate(R.layout.fragment_user_order_details, container, false);
        SetupActionbar();
        InitializeClassVariables();
        GetOrderDetailsByOrderId();
        return this.view;
    }
    private void SetupActionbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(UserOrdersFragmentConstants.USER_ORDERS_TITLE);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_return_to_previous_activity);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void InitializeClassVariables(){

        userOrderDetailsId = getArguments().getString("userOrderDetailsId");
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
        loginInfo = StorageUtils.GetFromStorage("user", loginInfo,
                new TypeToken<LoginInfo>() {
                }.getType(), getContext());
        InitializeUIComponents();
    }
    private void GetOrderDetailsByOrderId(){
        userOrderDetailsId = getArguments().getString("userOrderDetailsId");
        Call<UserOrderDetailsResponse> call = techStoreAPIInterface.getOrderDetailsByOrderId(
                loginInfo.getToken(),
                userOrderDetailsId
        );
        call.enqueue(new Callback<UserOrderDetailsResponse>() {
            @Override
            public void onResponse(Call<UserOrderDetailsResponse> call, Response<UserOrderDetailsResponse> response) {
                UserOrderDetailsResponse responseBody = response.body();
                if(responseBody==null) return;
                UserOrderDetailsResponse.Data data = responseBody.getData();
                if(data==null) return;
                userOrderDetails = data.getOrderDetails();
                String itemsCount = Integer.toString(userOrderDetails.size());
                itemsCountTextView.setText(itemsCount);
                InitializeOrderDetailsRecyclerView();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserOrderDetailsResponse> call, Throwable t) {
                t.printStackTrace();
                call.cancel();
            }
        });
    }
    private void InitializeUIComponents(){
        orderNoTextView = view.findViewById(R.id.userOrderDetails_OrderNo);
        orderNoTextView.setText(userOrderDetailsId);
        orderDateTextView = view.findViewById(R.id.userOrderDetails_OrderDate);
        itemsCountTextView = view.findViewById(R.id.userOrderDetails_ItemsCount);
        progressBar = view.findViewById(R.id.userOrderDetails_progressBar);
    }
    private void InitializeOrderDetailsRecyclerView() {
        orderDetailsRecyclerView = view.findViewById(R.id.orderDetailsRecyclerView);
        orderDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userOrderDetailsAdapter = new UserOrderDetailsAdapter(getContext(), userOrderDetails,this);
        orderDetailsRecyclerView.setAdapter(userOrderDetailsAdapter);
    }
}