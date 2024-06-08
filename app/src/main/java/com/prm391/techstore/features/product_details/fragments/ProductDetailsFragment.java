package com.prm391.techstore.features.product_details.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.prm391.techstore.R;


public class ProductDetailsFragment extends Fragment {

    private LinearLayout mobilePhoneLinearLayout;
    private View view;
    public ProductDetailsFragment() {}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_product_details, container, false);
        InitializeMobilePhoneLinearLayout();
        InitializeDescriptionExpandableTextView();
        return view;
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
    private void InitializeDescriptionExpandableTextView(){
        ExpandableTextView expTv = (ExpandableTextView) view.findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
        expTv.setText(getString(R.string.large_text));
    }
    private void MakePhoneCallToStore(){
        String phone = "+84123456789";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

}