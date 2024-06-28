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
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.prm391.techstore.R;
import com.prm391.techstore.constants.DialogConstants;
import com.prm391.techstore.constants.ProductDetailsConstants;
import com.prm391.techstore.constants.SearchByConstants;
import com.prm391.techstore.features.main.activities.MainActivityViewModel;
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
    private ProductListFragment productListFragment;
    private List<Float> customPriceValues;
    private MainActivityViewModel mainActivityViewModel;

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
        try {
            InitializePriceItemsGridLayout();
            InitializePriceRangeSlider();
            InitializeBeginFilterBySliderButton();
            productListFragment = (ProductListFragment) this.getParentFragmentManager()
                    .findFragmentById(R.id.mainFrameLayout);
            mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        } catch (Exception e) {
            e.getMessage();
        }
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

    private void InitializeBeginFilterBySliderButton() {
        beginFilterByPriceButton = view.findViewById(R.id.beginFilterByPriceButton);
        beginFilterByPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApplyPriceFilterBySlider();
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
                ApplyPriceFilterByOption(category);
            }
        });
        return priceCategoryButton;
    }

    private void ApplyPriceFilterByOption(Category category) {
        try {
            float[] priceRange = (float[]) category.getValue();
            int minPriceInt = (int)priceRange[0];
            int maxPriceInt = (int)priceRange[1];
            mainActivityViewModel.getMinPrice().setValue(Integer.toString(minPriceInt));
            mainActivityViewModel.getMaxPrice().setValue(Integer.toString(maxPriceInt));
            productListFragment.GetProductsFromAPI();
            this.dismiss();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void ApplyPriceFilterBySlider() {
        try {
            if (customPriceValues.size() == 0) {
                ShowInvalidPriceRangesDialog();
                return;
            }
            int minPriceInt = Math.round(customPriceValues.get(0));
            int maxPriceInt = Math.round(customPriceValues.get(1));
            mainActivityViewModel.getMinPrice().setValue(Integer.toString(minPriceInt));
            mainActivityViewModel.getMaxPrice().setValue(Integer.toString(maxPriceInt));
            productListFragment.GetProductsFromAPI();
            this.dismiss();
        }catch(Exception e){
            e.getMessage();
        }
    }

    private void ShowInvalidPriceRangesDialog() {
        AlertDialog invalidQuantityDialog = DialogUtils.getOkDialog(view.getContext(),
                DialogConstants.WARNING_DIALOG_TITLE,
                SearchByConstants.INVALID_PRICE_RANGE);
        invalidQuantityDialog.show();
    }
}