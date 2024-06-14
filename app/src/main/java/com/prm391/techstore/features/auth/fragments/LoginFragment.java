package com.prm391.techstore.features.auth.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.prm391.techstore.R;
import com.prm391.techstore.features.main.activities.MainActivity;
import com.prm391.techstore.utils.FragmentUtils;


public class LoginFragment extends Fragment {
    private TextView signUpTextView;
    private Button loginButton;
    private View view;
    private Activity activity;

    public LoginFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        activity = this.getActivity();
        SetupToSignupFragmentButton();
        SetupLoginButton();
        return view;
    }

    private void SetupToSignupFragmentButton() {
        signUpTextView = view.findViewById(R.id.signUpTextView);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                FragmentUtils.replace(R.id.authFrameLayout, new RegisterFragment(), getParentFragmentManager());
            }
        });
    }

    private void SetupLoginButton() {
        loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}