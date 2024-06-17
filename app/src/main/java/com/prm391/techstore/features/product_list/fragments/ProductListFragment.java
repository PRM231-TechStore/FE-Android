package com.prm391.techstore.features.product_list.fragments;

import android.os.Bundle;

import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.features.product_list.adapters.ProductListAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.RecyclerViewConstants;
import com.prm391.techstore.features.product_list.decorations.GridSpacingItemDecoration;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.models.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListFragment extends Fragment {

    private List<Product> productList;
    private View view;
    private RecyclerView productsRecyclerView;
    private ProgressBar productListProgressBar;
    private TechStoreAPIInterface techStoreAPIInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        try {
            super.onCreate(savedInstanceState);
            InitializeClassVariables();
            GetProductsFromAPI();
        } catch (Exception e) {
            e.getMessage();
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void InitializeClassVariables() {
        productList = new ArrayList<>();
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
        productListProgressBar = view.findViewById(R.id.productListProgressBar);
    }

    private void GetProductsFromAPI() throws Exception{
        Call<ProductListResponse> call = techStoreAPIInterface.getProducts();
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                ProductListResponse responseBody = response.body();
                productList = responseBody.data.items;
                InitializeProductsRecyclerView();
                productListProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void InitializeProductsRecyclerView() {
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),
                RecyclerViewConstants.COLUMN_COUNT);
        productsRecyclerView.setLayoutManager(gridLayoutManager);
        productsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(
                RecyclerViewConstants.SPAN_COUNT,
                RecyclerViewConstants.SPACING,
                RecyclerViewConstants.INCLUDE_EDGE
        ));
        ProductListAdapter adapter = new ProductListAdapter(getActivity().getApplicationContext(), productList);
        productsRecyclerView.setAdapter(adapter);
    }

}