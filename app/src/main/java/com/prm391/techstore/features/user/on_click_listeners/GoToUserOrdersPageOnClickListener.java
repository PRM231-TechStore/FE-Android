package com.prm391.techstore.features.user.on_click_listeners;

import android.content.DialogInterface;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.prm391.techstore.utils.FragmentUtils;

public class GoToUserOrdersPageOnClickListener implements View.OnClickListener {
    private int currentFragmentId;
    private Fragment targetFragment;
    private FragmentManager fragmentManager;

    public GoToUserOrdersPageOnClickListener(int currentFragmentId, Fragment fragment,FragmentManager fragmentManager){
        this.currentFragmentId = currentFragmentId;
        this.targetFragment = fragment;
        this.fragmentManager = fragmentManager;
    }
    @Override
    public void onClick(View v) {
        FragmentUtils.replace(currentFragmentId,targetFragment,fragmentManager);
    }
}
