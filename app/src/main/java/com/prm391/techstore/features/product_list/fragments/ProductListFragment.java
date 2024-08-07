package com.prm391.techstore.features.product_list.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.constants.ProductListFragmentConstants;
import com.prm391.techstore.features.main.activities.MainActivityViewModel;
import com.prm391.techstore.features.product_list.adapters.ProductListAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.prm391.techstore.models.ProductListQueryConditions;
import com.prm391.techstore.models.ProductListResponse;
import com.prm391.techstore.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;

public class ProductListFragment extends Fragment {

    private List<Product> productList;
    private View view;
    private RecyclerView productsRecyclerView;
    private ProgressBar productListProgressBar;
    private ProductListAdapter productListAdapter;
    private TechStoreAPIInterface techStoreAPIInterface;
    private MainActivityViewModel mainActivityViewModel;

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
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden)
            mainActivityViewModel.ClearAllSearchCategories();
    }

    public void GetProductsFromAPI() throws Exception{
        productListProgressBar.setVisibility(View.VISIBLE);
        Call<ProductListResponse> call = techStoreAPIInterface.getProducts(
                mainActivityViewModel.getSearchTerm().getValue(),
                mainActivityViewModel.getSortBy().getValue(),
                mainActivityViewModel.getSortOrder().getValue(),
                mainActivityViewModel.getMinPrice().getValue(),
                mainActivityViewModel.getMaxPrice().getValue(),
                mainActivityViewModel.getPageNumber().getValue(),
                mainActivityViewModel.getPageSize().getValue(),
                mainActivityViewModel.getLabel().getValue()
        );
        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                ProductListResponse responseBody = response.body();
                productList = responseBody.data.items;
                if(productList==null){
                    ShowResultsNotFoundAlertDialog();
                    return;
                }
                productListAdapter.updateData(productList);
                productListProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void InitializeClassVariables() {
        productList = new ArrayList<>();
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
        productListProgressBar = view.findViewById(R.id.productListProgressBar);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        mainActivityViewModel.ClearAllSearchCategories();
        InitializeProductsRecyclerView();
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
        productListAdapter = new ProductListAdapter(getActivity().getApplicationContext(), productList);
        productsRecyclerView.setAdapter(productListAdapter);
//        InitializeScrollListener();
    }
    private void ShowResultsNotFoundAlertDialog(){
        AlertDialog alertDialog = DialogUtils.getOkDialog(getActivity(),
                ProductListFragmentConstants.INFORMATION_TITLE,
                ProductListFragmentConstants.PRODUCTS_NOT_FOUND);
        alertDialog.show();
    }
//    private void InitializeScrollListener(){
//        productsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
//                if (layoutManager != null && dy > 0) { // Check if scrolling down
//                    int visibleItemCount = layoutManager.getChildCount();
//                    int totalItemCount = layoutManager.getItemCount();
//                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
//
//                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                        // End has been reached
//                        if (!mainActivityViewModel.getIsLoading().getValue() && !mainActivityViewModel.getIsLastPage().getValue()) {
//                            mainActivityViewModel.getIsLoading().setValue(true);
//                            LoadMoreProductsFromAPI();
//                        }
//                    }
//                }
//            }
//        });
//    }
//    private void LoadMoreProductsFromAPI() {
//        productListProgressBar.setVisibility(View.VISIBLE);
//
//        mainActivityViewModel.getPageNumber().setValue(mainActivityViewModel.getPageNumber().getValue() + 1); // Increment page number
//
//        Call<ProductListResponse> call = techStoreAPIInterface.getProducts(
//                mainActivityViewModel.getSearchTerm().getValue(),
//                mainActivityViewModel.getSortBy().getValue(),
//                mainActivityViewModel.getSortOrder().getValue(),
//                mainActivityViewModel.getMinPrice().getValue(),
//                mainActivityViewModel.getMaxPrice().getValue(),
//                mainActivityViewModel.getPageNumber().getValue(),
//                mainActivityViewModel.getPageSize().getValue(),
//                mainActivityViewModel.getLabel().getValue()
//        );
//
//        call.enqueue(new Callback<ProductListResponse>() {
//            @Override
//            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
//                ProductListResponse responseBody = response.body();
//                List<Product> newProducts = responseBody.data.items;
//                if (newProducts == null) {
//                    mainActivityViewModel.getIsLastPage().setValue(true); // No more pages
//                    return;
//                }
//                productList.addAll(newProducts);
//                productListAdapter.updateData(productList);
//                productListProgressBar.setVisibility(View.GONE);
//                mainActivityViewModel.getIsLoading().setValue(false);
//            }
//
//            @Override
//            public void onFailure(Call<ProductListResponse> call, Throwable t) {
//                call.cancel();
//                mainActivityViewModel.getIsLoading().setValue(false);
//            }
//        });
//    }
}