package com.prm391.techstore.features.product_list.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.prm391.techstore.R;
import com.prm391.techstore.constants.SearchByConstants;
import com.prm391.techstore.features.main.activities.MainActivityViewModel;
import com.prm391.techstore.models.Category;


public class SortByBottomSheetFragment extends BottomSheetDialogFragment {

    private View view;
    private LayoutInflater layoutInflater;
    private LinearLayout sortByOptionsLinearLayout;
    private MainActivityViewModel mainActivityViewModel;

    public SortByBottomSheetFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.layoutInflater = inflater;
        view = layoutInflater.inflate(R.layout.fragment_sort_by_bottom_sheet, container, false);
        InitializeClassVariables();
        return view;
    }

    private void InitializeClassVariables() {

        InitializeSortByOptionsLinearLayout();
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

    }

    private void InitializeSortByOptionsLinearLayout(){
        sortByOptionsLinearLayout = view.findViewById(R.id.sort_by_options_linear_layout);
        for (Category category : SearchByConstants.SORT_BY_CATEGORIES) {
            Button singleOptionBtn = BuildSingleOptionButton(category);
            sortByOptionsLinearLayout.addView(singleOptionBtn);
        }
    }

    private Button BuildSingleOptionButton(Category category) {

        Button singleOptionBtn =
                (Button) layoutInflater.inflate(R.layout.sort_by_single_option, sortByOptionsLinearLayout, false);
        singleOptionBtn.setText(category.getName());
        singleOptionBtn.setOnClickListener(v -> {
            try {
                SortProductsByCategory(category);
            } catch (Exception e) {
                e.getMessage();
            }

        });
        return singleOptionBtn;
    }

    private void SortProductsByCategory(Category category) throws Exception {
        String[] parameters = (String[]) category.getValue();
        mainActivityViewModel.getSortBy().setValue(parameters[0]);
        mainActivityViewModel.getSortOrder().setValue(parameters[1]);
        ProductListFragment productListFragment = (ProductListFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.mainFrameLayout);
        productListFragment.GetProductsFromAPI();
        this.dismiss();
    }
}