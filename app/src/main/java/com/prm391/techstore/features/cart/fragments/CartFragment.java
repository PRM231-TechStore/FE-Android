package com.prm391.techstore.features.cart.fragments;

import android.os.Bundle;

import com.prm391.techstore.features.cart.adapters.CartAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prm391.techstore.R;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private List<Product> productList;
    private View view;

    private RecyclerView cartRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);
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
        /*TODO: Convert the product's Id to String, and Image to String Url,
           as it is now retrieved from the API.*/
//        product.setId(Integer.parseInt(productJSONObject.getString(ProductListConstants.ID_COL)));
//        product.setName(productJSONObject.getString(ProductListConstants.NAME_COL));
//        product.setDescription(productJSONObject.getString(ProductListConstants.DESCRIPTION_COL));
//        product.setPrice(Double.parseDouble(productJSONObject.getString(ProductListConstants.PRICE_COL)));
//        product.setImage(ImageUtils.getBitmapFromAssets(this.getActivity(),
//                AssetsFolderConstants.PRODUCT_IMAGE_BY_ID(product.getId())));
        return product;
    }

    private void InitializeProductsRecyclerView() {
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),1);
        cartRecyclerView.setLayoutManager(gridLayoutManager);
        CartAdapter adapter = new CartAdapter(getActivity().getApplicationContext(), productList);
        cartRecyclerView.setAdapter(adapter);
    }

}