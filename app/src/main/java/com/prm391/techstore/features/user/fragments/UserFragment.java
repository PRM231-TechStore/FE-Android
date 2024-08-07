package com.prm391.techstore.features.user.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.constants.MainActivityConstants;
import com.prm391.techstore.constants.UserFragmentConstants;
import com.prm391.techstore.constants.UserOrdersFragmentConstants;
import com.prm391.techstore.features.user.on_click_listeners.LogoutOnClickListener;
import com.prm391.techstore.models.LoginInfo;
import com.prm391.techstore.models.UserDetailsResponse;
import com.prm391.techstore.models.UserSingleOption;
import com.prm391.techstore.utils.DialogUtils;
import com.prm391.techstore.utils.StorageUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private LinearLayout userOptionsLinearLayout;
    private LinearLayout userProfileLinearLayout;
    private Button logoutButton;
    private TechStoreAPIInterface techStoreAPIInterface;
    private UserFragmentConstants userFragmentConstants;
    private ProgressBar userProgressBar;

    public UserFragment() {
        // Required empty public constructor
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item=menu.findItem(R.id.productList_searchMenuItem);
        if(item!=null)
            item.setVisible(false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layoutInflater = inflater;
        this.view = this.layoutInflater.inflate(R.layout.fragment_user, container, false);
        userFragmentConstants = new UserFragmentConstants(this);
        SetupActionbar();
        InitializeClassVariables();
        GetUserDetailsFromAPI();
        return view;
    }
    private void SetupActionbar() {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle(MainActivityConstants.USER_PROFILE_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(false);
    }
    private void InitializeClassVariables() {
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
        userProgressBar = view.findViewById(R.id.user_ProgressBar);
        InitializeUserOptionsLinearLayout();
//        InitializeLogoutButton();
    }
    private void GetUserDetailsFromAPI(){
        LoginInfo loginInfo = null;
        loginInfo = StorageUtils.GetFromStorage("user", loginInfo,
                new TypeToken<LoginInfo>(){}.getType(), getContext());
        Call<UserDetailsResponse> call = techStoreAPIInterface.getUserDetailsById(
                loginInfo.getToken(),
                loginInfo.getUserId());
        call.enqueue(new Callback<UserDetailsResponse>() {
            @Override
            public void onResponse(Call<UserDetailsResponse> call, Response<UserDetailsResponse> response) {
                UserDetailsResponse responseBody = response.body();
                if(responseBody.getData()!=null){
                    InitializeUserProfileLinearLayout(responseBody.getData());
                    userProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<UserDetailsResponse> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void InitializeUserOptionsLinearLayout() {
        userOptionsLinearLayout = (LinearLayout) view.findViewById(R.id.userOptionsLinearLayout);
        for (UserSingleOption userSingleOption : userFragmentConstants.getUserSingleOptionList()) {
            userOptionsLinearLayout.addView(BuildSingleUserOptionLinearLayout(userSingleOption));
        }

    }
//    private void InitializeLogoutButton(){
//        logoutButton = view.findViewById(R.id.userLogoutButton);
//        logoutButton.setOnClickListener(v -> {
//            AlertDialog dialog = DialogUtils.getOkCancelDialog(getContext(),
//                    userFragmentConstants.getWARNING_TITLE(),
//                    userFragmentConstants.getLOGOUT_PROMPT(),
//                new LogoutOnClickListener());
//            dialog.show();
//        });
//    }


    private LinearLayout BuildSingleUserOptionLinearLayout(UserSingleOption userSingleOption ) {
        LinearLayout singleUserOptionLinearLayout = (LinearLayout) layoutInflater.inflate(
                R.layout.user_option_single_linear_layout,
                userOptionsLinearLayout,
                false);
        TextView settingName = singleUserOptionLinearLayout.findViewById(R.id.userOptionName);
        settingName.setText(userSingleOption.getName());
        TextView settingDescription = singleUserOptionLinearLayout.findViewById(R.id.userOptionDescription);
        settingDescription.setText(userSingleOption.getDescription());
        singleUserOptionLinearLayout.setOnClickListener(userSingleOption.getOnClickListener());
        return singleUserOptionLinearLayout;

    }

    private void InitializeUserProfileLinearLayout(UserDetailsResponse.UserDetails userDetails){
        userProfileLinearLayout = view.findViewById(R.id.userProfileLinearLayout);
        TextView username = userProfileLinearLayout.findViewById(R.id.userProfileLinearLayout_username);
        TextView email = userProfileLinearLayout.findViewById(R.id.userProfileLinearLayout_email);
        username.setText(userDetails.getUsername());
        email.setText(userDetails.getEmail());
    }
}