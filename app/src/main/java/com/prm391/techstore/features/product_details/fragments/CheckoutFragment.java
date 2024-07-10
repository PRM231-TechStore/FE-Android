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
import com.prm391.techstore.models.LoginInfo;
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
        int cartAmount = ((ProductDetailsActivity) getActivity()).GetCartProductAmountFromBundles();
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
                    ProductDetailsActivity activity = (ProductDetailsActivity) getActivity();
                    Map<String ,Map<String, Integer>> userProductAmount = new HashMap<>();
                    Map<String, Integer> userItemAmount = new HashMap<>();
                    LoginInfo currentUser = null;
                    currentUser = StorageUtils.GetFromStorage("user", currentUser, new TypeToken<LoginInfo>(){}.getType(), getContext());
                    userItemAmount = StorageUtils.GetFromStorage("itemAmount", userItemAmount, new TypeToken<Map<String, Integer>>(){}.getType(), getContext());
                    userProductAmount = StorageUtils.GetFromStorage("cart", userProductAmount, new TypeToken<Map<String ,Map<String, Integer>>>(){}.getType(), getContext());
                    Map<String, Integer> productAmount = userProductAmount.get(currentUser.getUserId()) != null ? userProductAmount.get(currentUser.getUserId()) : new HashMap<String, Integer>();
                    int itemAmount = 0;
                    try {
                        itemAmount = userItemAmount.get(currentUser.getUserId()) != null ? userItemAmount.get(currentUser.getUserId()) : 0;
                        productAmount.put(activity.GetProductIdFromBundles(), quantity);
                    } catch (Exception e) {

                    }
                    userProductAmount.put(currentUser.getUserId(), productAmount);
                    itemAmount += quantity;
                    userItemAmount.put(currentUser.getUserId(), itemAmount);
                    StorageUtils.SaveToStorage("cart", getContext(), userProductAmount);
                    StorageUtils.SaveToStorage("itemAmount", getContext(), userItemAmount);

                    ShowAddToCartSuccessfulDialog();
                }
            }
        });
    }
    private void ShowInvalidQuantityDialog(){
        AlertDialog invalidQuantityDialog = DialogUtils.getOkDialog(view.getContext(),
                DialogConstants.WARNING_DIALOG_TITLE,
                ProductDetailsConstants.INVALID_QUANTITY_MESSAGE);
        invalidQuantityDialog.show();
    }
    private void ShowAddToCartSuccessfulDialog(){
        AlertDialog invalidQuantityDialog = DialogUtils.getOkDialog(view.getContext(),
                DialogConstants.INFO_DIALOG_TITLE,
                ProductDetailsConstants.ADD_TO_CART_SUCCESSFULLY);
        invalidQuantityDialog.show();
    }
}