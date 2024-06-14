package com.prm391.techstore.features.auth.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prm391.techstore.R;
import com.prm391.techstore.utils.FragmentUtils;


public class RegisterFragment extends Fragment {

    private TextView loginTextView;
    private View view;
    public RegisterFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_register, container, false);
        SetupToSignupFragmentButton();
        return view;
    }
    private void SetupToSignupFragmentButton(){
        loginTextView = view.findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                FragmentUtils.replace(R.id.authFrameLayout,new LoginFragment(),getParentFragmentManager());
            }
        });
    }
}