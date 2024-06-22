package com.prm391.techstore.features.product_details.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.DialogConstants;
import com.prm391.techstore.constants.ProductDetailsConstants;
import com.prm391.techstore.utils.DialogUtils;
import com.travijuu.numberpicker.library.NumberPicker;


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
                    ShowAddToCartSuccessfulDialog();
                    //Xử lý logic thêm sản phẩm vô giỏ hàng ở đây
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