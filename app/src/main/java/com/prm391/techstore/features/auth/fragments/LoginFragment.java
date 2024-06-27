package com.prm391.techstore.features.auth.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.models.LoginInfo;
import com.prm391.techstore.models.LoginPayLoad;
import com.prm391.techstore.models.LoginRequestBody;
import com.prm391.techstore.models.LoginResponse;
import com.prm391.techstore.R;
import com.prm391.techstore.features.main.activities.MainActivity;
import com.prm391.techstore.utils.FragmentUtils;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.utils.StorageUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment {
    private TextView signUpTextView;

    private TextView errorTextView;
    private Button loginButton;
    private View view;
    private Activity activity;

    private EditText username;
    private EditText password;

    private TechStoreAPIInterface techStoreAPIInterface;

    public LoginFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        activity = this.getActivity();
        InitializeClassVariables();
        SetupToSignupFragmentButton();
        SetupLoginButton();
        return view;
    }

    private void InitializeClassVariables(){
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
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
        errorTextView = (TextView)view.findViewById(R.id.errorText);

        errorTextView.setText("");

        username = (EditText)view.findViewById(R.id.username);
        password = (EditText)view.findViewById(R.id.password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click even

                try{
                    LoginPayLoad payload = new LoginPayLoad(username.getText().toString(),password.getText().toString());

                    LoginRequestBody data = new LoginRequestBody(payload);
                    Call<LoginResponse> call = techStoreAPIInterface.login(data);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            LoginInfo user = new LoginInfo(response.body().loginInfo.getUserId(),response.body().loginInfo.getToken(),response.body().loginInfo.getExpiryDate());
                            StorageUtils.SaveToStorage("user", getContext(), user);
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            //handle error
                            errorTextView.setText("Username or password is not correct !");
                        }
                    });
                }catch (NullPointerException e){
                    errorTextView.setText("Please enter your account !");
                }
                catch (Exception e){
                    errorTextView.setText("Something went wrong ! !");
                }
            }
        });
    }
}