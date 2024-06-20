package com.prm391.techstore.features.product_list.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.prm391.techstore.R;


public class LaptopBrandsFragment extends Fragment {

    private View view;
    private String[] brands = {"HP", "Lenovo", "Dell", "Apple", "Asus", "Acer"};
    private LinearLayout laptopBrandsLinearLayout;
    public LaptopBrandsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_laptop_brands, container, false);
        InitializeClassVariables();
        return view;
    }
    private void InitializeClassVariables(){
        laptopBrandsLinearLayout = view.findViewById(R.id.laptopBrandsLinearLayout);
        for (String brand : brands) {
            Button button = new Button(this.getContext());
            button.setText(brand);
            button.setAllCaps(false);
            button.setPadding(16, 8, 16, 8);
            // You can set button style and attributes here
            button.setTextColor(getResources().getColor(R.color.white)); // Set the text color
            button.setBackgroundColor(getResources().getColor(R.color.dark_blue));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click
                    // For example, filter products by selected brand
//                    filterProductsByBrand(brand);
                }
            });

            laptopBrandsLinearLayout.addView(button);
        }
    }
}