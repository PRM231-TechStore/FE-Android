package com.prm391.techstore.features.product_list.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.ProductListConstants;
import com.prm391.techstore.features.product_list.adapters.CategoryAdapter;
import com.prm391.techstore.features.product_list.spinners.CustomSpinner;


public class SearchByFragment extends Fragment {
    private View view;
    private CategoryAdapter filterByAdapter;
    private CategoryAdapter sortByAdapter;
    private CustomSpinner filterBySpinner;
    private CustomSpinner sortBySpinner;
    public SearchByFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_by, container, false);
        try {
            super.onCreate(savedInstanceState);
            InitializeClassVariables();
            InitializeSortBySpinner();
            InitializeFilterSpinner();

        } catch (Exception e) {
            e.getMessage();
        }
        return view;
    }
    private void InitializeClassVariables(){
        filterByAdapter = new CategoryAdapter(this.getContext(), ProductListConstants.FILTER_BY_CATEGORIES);
        sortByAdapter = new CategoryAdapter(this.getContext(), ProductListConstants.SORT_BY_CATEGORIES);
    }

    private void InitializeSortBySpinner(){
        sortBySpinner = view.findViewById(R.id.productList_sortBySpinner);
        sortBySpinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
            @Override
            public void onPopupWindowOpened(Spinner spinner) {
                sortBySpinner.setBackground(getResources().getDrawable(R.drawable.bg_sort_by_spinner_up));
            }

            @Override
            public void onPopupWindowClosed(Spinner spinner) {
                sortBySpinner.setBackground(getResources().getDrawable(R.drawable.bg_sort_by_spinner_down));
            }
        });
        sortBySpinner.setAdapter(sortByAdapter);

    }
    private void InitializeFilterSpinner(){
        filterBySpinner = view.findViewById(R.id.productList_filterBySpinner);
        filterBySpinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
            @Override
            public void onPopupWindowOpened(Spinner spinner) {
                filterBySpinner.setBackground(getResources().getDrawable(R.drawable.bg_filter_by_spinner_up));
            }

            @Override
            public void onPopupWindowClosed(Spinner spinner) {
                filterBySpinner.setBackground(getResources().getDrawable(R.drawable.bg_filter_by_spinner_down));
            }
        });
        filterBySpinner.setAdapter(filterByAdapter);

    }
}