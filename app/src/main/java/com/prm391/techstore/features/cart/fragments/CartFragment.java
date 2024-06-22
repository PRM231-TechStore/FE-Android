package com.prm391.techstore.features.cart.fragments;

import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.features.cart.adapters.CartAdapter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prm391.techstore.R;
import com.prm391.techstore.features.main.activities.MainActivityViewModel;
import com.prm391.techstore.models.CartProduct;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.models.ProductByIdResponse;
import com.prm391.techstore.models.ProductListResponse;
import com.prm391.techstore.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private List<CartProduct> cartProductList;
    private View view;
    private boolean firstView;

    private RecyclerView cartRecyclerView;

    private CartAdapter cartAdapter;

    private TechStoreAPIInterface techStoreAPIInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);
        try {
            super.onCreate(savedInstanceState);
            firstView = true;
            InitializeClassVariables();
            GetProductsFromAPI();
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

    @Override
    public void onResume() {
        try {
            super.onResume();
            if (!firstView) {
                cartProductList = new ArrayList<CartProduct>();
                GetProductsFromAPI();
                InitializeProductsRecyclerView();
            }
            firstView = false;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void GetProductsFromAPI() throws Exception{
        String contents = null;
        Map<String, Integer> productAmount = new HashMap<>();
        FileInputStream fis = null;
        try {
            fis = getContext().openFileInput("cart");
            InputStreamReader inputStreamReader =
                    new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
                String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();
                }
            } catch (IOException e) {
                // Error occurred when opening raw file for reading.
            } finally {
                contents = stringBuilder.toString();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Integer>>(){}.getType();
            if (!contents.isEmpty()) {
                productAmount = gson.fromJson(contents, type);
            }
        } catch (FileNotFoundException e) {

        }

        for (Map.Entry<String, Integer> entry: productAmount.entrySet()) {
            Call<ProductByIdResponse> call = techStoreAPIInterface.getProductById(entry.getKey());
            call.enqueue(new Callback<ProductByIdResponse>() {
                @Override
                public void onResponse(Call<ProductByIdResponse> call, Response<ProductByIdResponse> response) {
                    ProductByIdResponse responseBody = response.body();
                    Product product = responseBody.product;
                    CartProduct cartProduct = new CartProduct();
                    cartProduct.setId(product.getId());
                    cartProduct.setName(product.getName());
                    cartProduct.setImage(product.getImage());
                    cartProduct.setPrice(product.getPrice());
                    cartProduct.setDescription(product.getDescription());
                    cartProduct.setAmount(entry.getValue());
                    cartProductList.add(cartProduct);

                    InitializeProductsRecyclerView();
                }

                @Override
                public void onFailure(Call<ProductByIdResponse> call, Throwable t) {
                    call.cancel();
                }
            });

        }
    }


    private void InitializeClassVariables() {
        cartProductList = new ArrayList<CartProduct>();
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
        InitializeProductsRecyclerView();
    }

//    private void LoadProductsFromJsonToList() throws JSONException, IOException {
//
//        JSONObject jsonObject = new JSONObject(JsonUtils.loadJSONInAssetsFolder(getActivity(),
//                "products/products.json"));
//        JSONArray productJsonArray = jsonObject.getJSONArray("productList");
//
//        for (int i = 0; i < productJsonArray.length(); i++) {
//            JSONObject productJsonObject = productJsonArray.getJSONObject(i);
//            Product product = ExtractProductFromJsonObject(productJsonObject);
//            productList.add(product);
//        }
//    }
//
//    private Product ExtractProductFromJsonObject(JSONObject productJSONObject) throws JSONException, IOException {
//        Product product = new Product();
//        /*TODO: Convert the product's Id to String, and Image to String Url,
//           as it is now retrieved from the API.*/
////        product.setId(Integer.parseInt(productJSONObject.getString(ProductListConstants.ID_COL)));
////        product.setName(productJSONObject.getString(ProductListConstants.NAME_COL));
////        product.setDescription(productJSONObject.getString(ProductListConstants.DESCRIPTION_COL));
////        product.setPrice(Double.parseDouble(productJSONObject.getString(ProductListConstants.PRICE_COL)));
////        product.setImage(ImageUtils.getBitmapFromAssets(this.getActivity(),
////                AssetsFolderConstants.PRODUCT_IMAGE_BY_ID(product.getId())));
//        return product;
//    }

    private void InitializeProductsRecyclerView() {
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getApplicationContext(),1);
        cartRecyclerView.setLayoutManager(gridLayoutManager);
        cartAdapter = new CartAdapter(getActivity().getApplicationContext(), cartProductList);
        cartRecyclerView.setAdapter(cartAdapter);
    }

}