package com.prm391.techstore.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.prm391.techstore.R;

public class FragmentUtils {
    public static void replace(int currentFragmentId, Fragment target, FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(currentFragmentId, target).addToBackStack(null);
        fragmentTransaction.commit();
    }
    public static void replaceWithName(int currentFragmentId, Fragment target, String fragmentName, FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragmentName);
        fragmentTransaction.replace(currentFragmentId, target).addToBackStack(null);
        fragmentTransaction.commit();
    }
    public static void popFragmentFromBackstackByName(String name,FragmentManager fragmentManager){
        fragmentManager.popBackStack (name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
