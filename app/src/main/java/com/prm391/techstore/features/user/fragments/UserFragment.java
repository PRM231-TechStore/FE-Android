package com.prm391.techstore.features.user.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prm391.techstore.R;


public class UserFragment extends Fragment {

    private View view;
    private LayoutInflater layoutInflater;
    private LinearLayout userOptionsLinearLayout;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layoutInflater = inflater;
        this.view = this.layoutInflater.inflate(R.layout.fragment_user, container, false);
        InitializeClassVariables();
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    private void InitializeClassVariables() {
        InitializeUserOptionsLinearLayout();

    }

    private void InitializeUserOptionsLinearLayout() {
        userOptionsLinearLayout = (LinearLayout) view.findViewById(R.id.userOptionsLinearLayout);
        for (int i = 0; i < 6; i++) {
            userOptionsLinearLayout.addView(BuildSingleUserOptionLinearLayout());
        }
    }

    private LinearLayout BuildSingleUserOptionLinearLayout() {
        LinearLayout singleUserOptionLinearLayout = (LinearLayout) layoutInflater.inflate(
                R.layout.user_option_single_linear_layout,
                userOptionsLinearLayout,
                false);
        TextView settingName = singleUserOptionLinearLayout.findViewById(R.id.userOptionName);
        settingName.setText("Ahihih");
        TextView settingDescription = singleUserOptionLinearLayout.findViewById(R.id.userOptionDescription);
        settingDescription.setText("Some bullshit description");
        return singleUserOptionLinearLayout;

    }

}