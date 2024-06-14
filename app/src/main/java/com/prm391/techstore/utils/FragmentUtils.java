package com.prm391.techstore.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.prm391.techstore.R;

public class FragmentUtils {
    public static void replace(int currentFragmentId, Fragment target, FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(currentFragmentId, target);
        fragmentTransaction.commit();
    }
}
