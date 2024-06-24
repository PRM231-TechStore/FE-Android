package com.prm391.techstore.features.product_list.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.prm391.techstore.R;
import com.prm391.techstore.constants.DialogConstants;
import com.prm391.techstore.constants.ProductDetailsConstants;
import com.prm391.techstore.constants.SearchByConstants;
import com.prm391.techstore.models.Category;
import com.prm391.techstore.utils.CurrencyUtils;
import com.prm391.techstore.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

public class FilterByPriceBottomSheetFragment extends BottomSheetDialogFragment {

    private View view;
    private LayoutInflater layoutInflater;
    private LinearLayout priceItemsGridLayout;
    private RangeSlider priceRangeSlider;
    private Button beginFilterByPriceButton;
    private List<Float> customPriceValues;

    public FilterByPriceBottomSheetFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layoutInflater = inflater;
        view = inflater.inflate(R.layout.fragment_filter_by_price_bottom_sheet, container, false);
        InitializeClassVariables();
        return view;
    }

    private void InitializeClassVariables() {
        InitializePriceItemsGridLayout();
        InitializePriceRangeSlider();
        InitializeBeginFilterByPriceButton();
    }

    private void InitializePriceItemsGridLayout() {
        priceItemsGridLayout = view.findViewById(R.id.price_items_linear_layout);
        for (Category priceCategory : SearchByConstants.PRICE_CATEGORIES) {
            Button priceCategoryButton = BuildPriceCategoryButton(priceCategory);
            priceItemsGridLayout.addView(priceCategoryButton);
        }
    }

    private void InitializePriceRangeSlider() {
        customPriceValues = new ArrayList<>();
        priceRangeSlider = view.findViewById(R.id.price_range_slider);

        priceRangeSlider.setValues(SearchByConstants.MIN_PRICE_ON_SLIDER,
                SearchByConstants.MAX_PRICE_ON_SLIDER);
        priceRangeSlider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float v) {
                return String.format(String.format("%s VND", CurrencyUtils.DecimalToVND(v)));
            }
        });
        priceRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider rangeSlider) {
                customPriceValues = rangeSlider.getValues();
            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider rangeSlider) {
                customPriceValues = rangeSlider.getValues();
            }
        });
    }
    private void InitializeBeginFilterByPriceButton(){
        beginFilterByPriceButton = view.findViewById(R.id.beginFilterByPriceButton);
        beginFilterByPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyPriceFilterWithinRange();
            }
        });
    }

    private Button BuildPriceCategoryButton(Category category) {
        Button priceCategoryButton = (Button) layoutInflater.inflate(
                R.layout.filter_by_price_single_option,
                priceItemsGridLayout, false);
        priceCategoryButton.setText(category.getName());
        priceCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyPriceFilter(category);
            }
        });
        return priceCategoryButton;
    }

    private void ApplyPriceFilter(Category category) {
        float[] priceRange = (float[]) category.getValue();
        Log.i("Beginning price:",Float.toString(priceRange[0]));
        Log.i("Ending price:",Float.toString(priceRange[1]));
        this.dismiss();
    }
    private void ApplyPriceFilterWithinRange(){
        //TODO: Call API to fetch products between price ranges using the customPriceValues list.
        if(customPriceValues.size()==0){
            ShowInvalidPriceRangesDialog();
            return;
        }
        Log.i("Current minimum and maximum values:",customPriceValues.toString());
        this.dismiss();
    }
    private void ShowInvalidPriceRangesDialog(){
        AlertDialog invalidQuantityDialog = DialogUtils.getBasicDialog(view.getContext(),
                DialogConstants.WARNING_DIALOG_TITLE,
                SearchByConstants.INVALID_PRICE_RANGE);
        invalidQuantityDialog.show();
    }
}