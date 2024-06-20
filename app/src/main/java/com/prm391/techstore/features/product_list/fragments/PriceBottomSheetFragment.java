package com.prm391.techstore.features.product_list.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prm391.techstore.R;

public class PriceBottomSheetFragment extends Fragment {

    private View view;

    public PriceBottomSheetFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_price_bottom_sheet, container, false);
        return view;
    }
}