package com.prm391.techstore.features.product_list.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.SearchByConstants;
import com.prm391.techstore.features.product_list.adapters.CategoryAdapter;


public class SearchByFragment extends Fragment {
    private View view;
    private CategoryAdapter filterByAdapter;
    private CategoryAdapter sortByAdapter;
    private Button openFilterBottomSheetButton;
    private Button openSortByBottomSheetButton;
//    private CustomSpinner sortBySpinner;
    public SearchByFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_by, container, false);
        try {
            super.onCreate(savedInstanceState);
            InitializeClassVariables();
//            InitializeSortBySpinner();
//            InitializeFilterSpinner();

        } catch (Exception e) {
            e.getMessage();
        }
        return view;
    }
    private void InitializeClassVariables(){
//        filterByAdapter = new CategoryAdapter(this.getContext(), SearchByConstants.FILTER_BY_CATEGORIES);
        sortByAdapter = new CategoryAdapter(this.getContext(), SearchByConstants.SORT_BY_CATEGORIES);
        InitializeOpenFilterBottomSheetButton();
        InitializeOpenSortByBottomSheetButton();
    }

//    private void InitializeSortBySpinner(){
//        sortBySpinner = view.findViewById(R.id.productList_openSortByBottomSheetButton);
//        sortBySpinner.setAdapter(sortByAdapter);
//        sortBySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                try {
//                    Category selectedCategory = (Category) sortByAdapter.getItem(position);
//                    String[] parameters = (String[])selectedCategory.getValue();
//                    mainActivityViewModel.getSortBy().setValue(parameters[0]);
//                    mainActivityViewModel.getSortOrder().setValue(parameters[1]);
//                    FragmentManager fm = getParentFragmentManager();
//                    ProductListFragment productListFragment = (ProductListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.mainFrameLayout);
//                    productListFragment.GetProductsFromAPI();
//                }catch(Exception e){
//                    e.getMessage();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        sortBySpinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
//            @Override
//            public void onPopupWindowOpened(Spinner spinner) {
//                sortBySpinner.setBackground(getResources().getDrawable(R.drawable.bg_sort_by_spinner_up));
//            }
//
//            @Override
//            public void onPopupWindowClosed(Spinner spinner) {
//                sortBySpinner.setBackground(getResources().getDrawable(R.drawable.bg_sort_by_spinner_down));
//            }
//        });
//    }
    private void InitializeOpenFilterBottomSheetButton(){
        openFilterBottomSheetButton = view.findViewById(R.id.productList_openPriceBottomSheetButton);
        openFilterBottomSheetButton.setOnClickListener(v -> {
            ShowPriceBottomSheetFragment();
        });
    }
    private void InitializeOpenSortByBottomSheetButton(){
        openSortByBottomSheetButton = view.findViewById(R.id.productList_openSortByBottomSheetButton);
        openSortByBottomSheetButton.setOnClickListener(v -> {
            ShowSortByBottomSheetFragment();
        });
    }
    private void ShowPriceBottomSheetFragment(){
        FilterByPriceBottomSheetFragment priceBottomSheetFragment = new FilterByPriceBottomSheetFragment();
        priceBottomSheetFragment.show(this.getActivity().getSupportFragmentManager(), priceBottomSheetFragment.getTag());
    }
    private void ShowSortByBottomSheetFragment(){
        SortByBottomSheetFragment sortByBottomSheetFragment = new SortByBottomSheetFragment();
        sortByBottomSheetFragment.show(this.getActivity().getSupportFragmentManager(), sortByBottomSheetFragment.getTag());
    }
}