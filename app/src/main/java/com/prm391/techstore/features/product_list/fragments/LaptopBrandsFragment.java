package com.prm391.techstore.features.product_list.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.prm391.techstore.R;
import com.prm391.techstore.utils.ImageUtils;

import java.util.concurrent.Executors;


public class LaptopBrandsFragment extends Fragment {

    private View view;
    private String hpLogoUrl = "https://inkythuatso.com/uploads/thumbnails/800/2021/11/logo-hp-inkythuatso-3-01-26-10-51-17.jpg";
    private String[] brands = {"HP","Lenovo","Dell","Apple"};
    private LinearLayout laptopBrandsLinearLayout;
    private LayoutInflater layoutInflater;
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

        }catch(Exception e){
            e.getMessage();
        }
        return view;
    }
    private void InitializeClassVariables() throws Exception{
        laptopBrandsLinearLayout = view.findViewById(R.id.laptopBrandsLinearLayout);
        for (String brand : brands) {
            ImageButton laptopBrandImageButton = BuildLaptopBrandImageButton();
            laptopBrandsLinearLayout.addView(laptopBrandImageButton);
        }
    }
    private ImageButton BuildLaptopBrandImageButton() throws Exception{
        ImageButton laptopBrandImageButton =(ImageButton) layoutInflater.inflate(R.layout.laptop_brand_single_item,laptopBrandsLinearLayout,false);

//        Executors.newSingleThreadExecutor().execute(() -> {
//            Bitmap bitmap = ImageUtils.getBitmapFromUrl(hpLogoUrl);
//            Bitmap resizedBitmap = ImageUtils.resizeBitmap(bitmap,120,120);
//            if (bitmap != null) {
//                new Handler(Looper.getMainLooper()).post(() -> {
//                    // Set the Bitmap to the ImageView on the main thread
//                    laptopBrandImageButton.setImageBitmap(resizedBitmap);
//                });
//            }
//        });
        laptopBrandImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return laptopBrandImageButton;

    }
}