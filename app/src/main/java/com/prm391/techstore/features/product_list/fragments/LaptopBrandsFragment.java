package com.prm391.techstore.features.product_list.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.features.main.activities.MainActivityViewModel;
import com.prm391.techstore.models.Category;
import com.prm391.techstore.models.LaptopBrandsResponse;
import com.prm391.techstore.models.ProductListResponse;
import com.prm391.techstore.utils.ImageUtils;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LaptopBrandsFragment extends Fragment {

    private View view;
    private LinearLayout laptopBrandsLinearLayout;
    private LayoutInflater layoutInflater;
    private TechStoreAPIInterface techStoreAPIInterface;
    private MainActivityViewModel mainActivityViewModel;
    private List<LaptopBrandsResponse.Data.LaptopBrand> laptopBrandList;
    public LaptopBrandsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            // Inflate the layout for this fragment
            this.layoutInflater = inflater;
            view = inflater.inflate(R.layout.fragment_laptop_brands, container, false);
            InitializeClassVariables();
            GetLaptopBrands();

        }catch(Exception e){
            e.getMessage();
        }
        return view;
    }
    private void InitializeClassVariables() throws Exception{
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
    }
    private void GetLaptopBrands(){
        Call<LaptopBrandsResponse> call = techStoreAPIInterface.getLaptopBrands("1","10");
        call.enqueue(new Callback<LaptopBrandsResponse>() {
            @Override
            public void onResponse(Call<LaptopBrandsResponse> call, Response<LaptopBrandsResponse> response) {
                try {
                    LaptopBrandsResponse responseBody = response.body();
                    laptopBrandList = responseBody.getData().getItems();
                    InitializeLaptopBrandsLinearLayout();
                }catch(Exception e){
                    e.getMessage();
                }
            }

            @Override
            public void onFailure(Call<LaptopBrandsResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }
    private void InitializeLaptopBrandsLinearLayout() throws Exception{
        laptopBrandsLinearLayout = view.findViewById(R.id.laptopBrandsLinearLayout);
        for (LaptopBrandsResponse.Data.LaptopBrand laptopBrand : laptopBrandList) {
            ImageButton laptopBrandImageButton = BuildLaptopBrandImageButton(laptopBrand);
            laptopBrandsLinearLayout.addView(laptopBrandImageButton);
        }
    }

    private ImageButton BuildLaptopBrandImageButton(LaptopBrandsResponse.Data.LaptopBrand laptopBrand) throws Exception{
        ImageButton laptopBrandImageButton =(ImageButton) layoutInflater.inflate(R.layout.laptop_brand_single_item,laptopBrandsLinearLayout,false);

        Executors.newSingleThreadExecutor().execute(() -> {
            Bitmap bitmap = ImageUtils.getBitmapFromUrl(laptopBrand.getImage());
            Bitmap resizedBitmap = ImageUtils.resizeBitmap(bitmap,120,120);
            if (bitmap != null) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Set the Bitmap to the ImageView on the main thread
                    laptopBrandImageButton.setImageBitmap(resizedBitmap);
                });
            }
        });
        laptopBrandImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyBrandFilter(laptopBrand.getName());
            }
        });
        return laptopBrandImageButton;

    }
    private void ApplyBrandFilter(String brandName) {
        try {
            ProductListFragment productListFragment = (ProductListFragment) getActivity()
                    .getSupportFragmentManager()
                    .findFragmentById(R.id.mainFrameLayout);
            mainActivityViewModel.getLabel().setValue(brandName);
            productListFragment.GetProductsFromAPI();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}