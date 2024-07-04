package com.prm391.techstore.features.auth.fragments;

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
import android.widget.Toast;

import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.features.main.activities.MainActivity;
import com.prm391.techstore.models.LoginResponse;
import com.prm391.techstore.models.RegisterBody;
import com.prm391.techstore.models.RegisterPayload;
import com.prm391.techstore.utils.EmailUtils;
import com.prm391.techstore.utils.ErrorMessageUtils;
import com.prm391.techstore.utils.FragmentUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterFragment extends Fragment {

    private TextView loginTextView;
    private TextView errorText;
    private Button registerButton;
    private View view;
    private EditText username;

    private EditText confirmPassword;
    private EditText password;
    private EditText email;
    private TechStoreAPIInterface techStoreAPIInterface;
    public RegisterFragment(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_register, container, false);
        SetupToSignupFragmentButton();
        InitializeClassVariables();
        SetupRegisterButton();
        return view;
    }

    private void InitializeClassVariables(){
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
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

    private void  SetupRegisterButton(){
        registerButton = view.findViewById(R.id.registerButton);

        username = (EditText)view.findViewById(R.id.username);
        password = (EditText)view.findViewById(R.id.password);
        email = (EditText) view.findViewById(R.id.email);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        errorText = (TextView) view.findViewById((R.id.errorText));

        errorText.setText("");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.i("API", "i'm in");
                    if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || confirmPassword.getText().toString().isEmpty() || email.getText().toString().isEmpty()){
                        errorText.setText("Please enter all field !");
                        return;
                    }

                    if(!password.getText().toString().equals(confirmPassword.getText().toString())){
                        errorText.setText("Passwords do NOT match!");
                        return;
                    }

                    if(!EmailUtils.isValidEmailAddress(email.getText().toString())){
                        errorText.setText("Email is not valid !!");
                        return;
                    }
                    Log.i("API", "pass validation");
                    RegisterPayload payload = new RegisterPayload(username.getText().toString(),password.getText().toString(),email.getText().toString());
                    RegisterBody data = new RegisterBody(payload);
                    Call<Void> call = techStoreAPIInterface.Register(data);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(!response.isSuccessful()){

                                String errorMessage = ErrorMessageUtils.getErrorMessage(response);

                                errorText.setText(errorMessage);
                                return;
                            }else {
                                Toast.makeText(getContext(), "Register successfully", Toast.LENGTH_SHORT).show();
                                FragmentUtils.replace(R.id.authFrameLayout, new LoginFragment(), getParentFragmentManager());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            //handle error
                            Log.i("API", "onClick: false: %s",t);
                            errorText.setText("Some thing went wrong !");
                        }
                    });
                }catch (Exception e){
                    Log.i("API", "%s", e);
                    errorText.setText("Some thing went wrong !!");
                }
            }
        });
    }
}