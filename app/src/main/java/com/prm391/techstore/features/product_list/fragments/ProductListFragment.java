package com.prm391.techstore.features.product_list.fragments;

import android.app.Activity;
import android.os.Bundle;

import com.prm391.techstore.features.product_list.adapters.ProductListAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.AssetsFolderConstants;
import com.prm391.techstore.constants.ProductListConstants;
import com.prm391.techstore.constants.RecyclerViewConstants;
import com.prm391.techstore.features.product_list.adapters.GridSpacingItemDecoration;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.utils.ImageUtils;
import com.prm391.techstore.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListFragment extends Fragment {

    private List<Product> productList;
    private View view;

    private RecyclerView productsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_product_list, container, false);
        try {
            super.onCreate(savedInstanceState);
            InitializeClassVariables();
            LoadProductsFromJsonToList();
            InitializeProductsRecyclerView();
        } catch (Exception e) {
            e.getMessage();
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void InitializeClassVariables() {
        productList = new ArrayList<>();
    }

    private void LoadProductsFromJsonToList() throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject(JsonUtils.loadJSONInAssetsFolder(getActivity(),
                "products/products.json"));
        JSONArray productJsonArray = jsonObject.getJSONArray("productList");

        for (int i = 0; i < productJsonArray.length(); i++) {
            JSONObject productJsonObject = productJsonArray.getJSONObject(i);
            Product product = ExtractProductFromJsonObject(productJsonObject);
            productList.add(product);
        }
    }

    private Product ExtractProductFromJsonObject(JSONObject productJSONObject) throws JSONException, IOException {
        Product product = new Product();
        product.setId(Integer.parseInt(productJSONObject.getString(ProductListConstants.ID_COL)));
        product.setName(productJSONObject.getString(ProductListConstants.NAME_COL));
        product.setDescription(productJSONObject.getString(ProductListConstants.DESCRIPTION_COL));
        product.setPrice(Double.parseDouble(productJSONObject.getString(ProductListConstants.PRICE_COL)));
        product.setImage(ImageUtils.getBitmapFromAssets(this.getActivity(),
                AssetsFolderConstants.PRODUCT_IMAGE_BY_ID(product.getId())));
        return product;
    }

    private void InitializeProductsRecyclerView() {
        productsRecyclerView = view.findViewById(R.id.productsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),
                RecyclerViewConstants.COLUMN_COUNT);
        productsRecyclerView.setLayoutManager(gridLayoutManager);
        productsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(
                RecyclerViewConstants.SPAN_COUNT,
                RecyclerViewConstants.SPACING,
                RecyclerViewConstants.INCLUDE_EDGE
        ));
        ProductListAdapter adapter = new ProductListAdapter(getActivity().getApplicationContext(), productList);
        productsRecyclerView.setAdapter(adapter);
    }

}