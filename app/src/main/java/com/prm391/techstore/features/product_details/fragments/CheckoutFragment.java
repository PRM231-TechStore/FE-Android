package com.prm391.techstore.features.product_details.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.R;
import com.prm391.techstore.constants.DialogConstants;
import com.prm391.techstore.constants.ProductDetailsConstants;
import com.prm391.techstore.features.product_details.activities.ProductDetailsActivity;
import com.prm391.techstore.utils.DialogUtils;
import com.prm391.techstore.utils.StorageUtils;
import com.travijuu.numberpicker.library.NumberPicker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;


public class CheckoutFragment extends Fragment {

   private View view;
   private NumberPicker quantityPicker;
   private Button addToCartButton;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_checkout, container, false);
        InitializeClassVariables();
        return view;
    }
    private void InitializeClassVariables(){
        quantityPicker = view.findViewById(R.id.quantityPicker);
        int cartAmount = ((ProductDetailsActivity) getActivity()).getIntent().getExtras().getInt("cartAmount");
        quantityPicker.setValue(cartAmount);
        InitializeAddToCartButton();
    }

    private void InitializeAddToCartButton(){
        addToCartButton = view.findViewById(R.id.addToCartButton);
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = quantityPicker.getValue();
                if (quantity==0) ShowInvalidQuantityDialog();
                else{
                    String contents = "";
                    ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
                    Map<String, Integer> productAmount = new HashMap<>();
                    int itemAmount = 0;
                    itemAmount = StorageUtils.GetFromStorage("itemAmount", itemAmount, new TypeToken<Integer>(){}.getType(), getContext());
                    productAmount = StorageUtils.GetFromStorage("cart", productAmount, new TypeToken<Map<String, Integer>>(){}.getType(), getContext());
                    productAmount.put(activity.GetProductIdFromBundles(), quantity);
                    itemAmount += quantity;
                    StorageUtils.SaveToStorage("cart", getContext(), productAmount);
                    StorageUtils.SaveToStorage("itemAmount", getContext(), itemAmount);

                    ShowAddToCartSuccessfulDialog();
                }
            }
        });
    }
    private void ShowInvalidQuantityDialog(){
        AlertDialog invalidQuantityDialog = DialogUtils.getBasicDialog(view.getContext(),
                DialogConstants.WARNING_DIALOG_TITLE,
                ProductDetailsConstants.INVALID_QUANTITY_MESSAGE);
        invalidQuantityDialog.show();
    }
    private void ShowAddToCartSuccessfulDialog(){
        AlertDialog invalidQuantityDialog = DialogUtils.getBasicDialog(view.getContext(),
                DialogConstants.INFO_DIALOG_TITLE,
                ProductDetailsConstants.ADD_TO_CART_SUCCESSFULLY);
        invalidQuantityDialog.show();
    }
}