package com.prm391.techstore.features.product_details.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.features.product_details.activities.ProductDetailsActivity;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.models.ProductByIdResponse;
import com.prm391.techstore.models.ProductListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProductDetailsFragment extends Fragment {

    private LinearLayout mobilePhoneLinearLayout;
    private TechStoreAPIInterface techStoreAPIInterface;
    private ProgressBar productDetailsProgressBar;
    private View view;
    private Product product;
    public ProductDetailsFragment() {}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_product_details, container, false);
        InitializeClassVariables();
        GetProductById();
        return view;
    }
    private void InitializeClassVariables(){
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
        productDetailsProgressBar = view.findViewById(R.id.productDetailsProgressBar);
    }
    private void GetProductById(){
        ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
        String productId = activity.GetProductIdFromBundles();
        Call<ProductByIdResponse> call = techStoreAPIInterface.getProductById(productId);
        call.enqueue(new Callback<ProductByIdResponse>() {
            @Override
            public void onResponse(Call<ProductByIdResponse> call, Response<ProductByIdResponse> response) {
                ProductByIdResponse responseBody = response.body();
                product = responseBody.product;
                BindProductToView();
                InitializeMobilePhoneLinearLayout();
                productDetailsProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ProductByIdResponse> call, Throwable t) {
                call.cancel();
            }
        });

    }
    private void BindProductToView(){
        BindNameToTextView();
        BindPriceToTextView();
        BindDescriptionToExpandableTextView();
    }
    private void BindNameToTextView(){
        TextView productNameTextView = view.findViewById(R.id.productDetails_ProductName);
        productNameTextView.setText(product.getName());
    }
    private void BindPriceToTextView(){
        TextView productPriceTextView = view.findViewById(R.id.productDetails_ProductPrice);
        productPriceTextView.setText(String.format("%1$,.0f VND",product.getPrice()));
    }
    private void BindDescriptionToExpandableTextView(){
        ExpandableTextView expTv = (ExpandableTextView) view.findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
        expTv.setText(product.getDescription());
    }
    private void InitializeMobilePhoneLinearLayout(){
        mobilePhoneLinearLayout = view.findViewById(R.id.mobilePhoneLinearLayout);
        mobilePhoneLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakePhoneCallToStore();
            }
        });
    }



    private void MakePhoneCallToStore(){
        String phone = "+84123456789";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

}