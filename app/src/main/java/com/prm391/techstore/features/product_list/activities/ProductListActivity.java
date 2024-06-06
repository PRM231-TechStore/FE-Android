package com.prm391.techstore.features.product_list.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.prm391.techstore.R;
import com.prm391.techstore.constants.AssetsFolderConstants;
import com.prm391.techstore.constants.ProductListConstants;
import com.prm391.techstore.constants.RecyclerViewConstants;
import com.prm391.techstore.features.product_list.adapters.GridSpacingItemDecoration;
import com.prm391.techstore.features.product_list.adapters.ProductListAdapter;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.utils.ImageUtils;
import com.prm391.techstore.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    private List<Product> productList;
    private RecyclerView productsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            Setup();
            InitializeClassVariables();
            LoadProductsFromJsonToList();
            InitializeProductsRecyclerView();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Setup(){
        SetupAndroid();
        SetupActionBar();
    }
    private void SetupAndroid() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void SetupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(ProductListConstants.TITLE_NAME);
    }
    private void InitializeClassVariables() {
        productList = new ArrayList<>();
    }

    private void LoadProductsFromJsonToList() throws JSONException, IOException {

        JSONObject jsonObject = new JSONObject(JsonUtils.loadJSONInAssetsFolder(this, "products/products.json"));
        JSONArray productJsonArray = jsonObject.getJSONArray("productList");

        for (int i = 0; i < productJsonArray.length(); i++) {
            JSONObject productJsonObject = productJsonArray.getJSONObject(i);
            Product product = ExtractProductFromJsonObject(productJsonObject);
            productList.add(product);
        }
    }
    private Product ExtractProductFromJsonObject(JSONObject productJSONObject) throws JSONException,IOException{
        Product product = new Product();
        product.setId(Integer.parseInt(productJSONObject.getString(ProductListConstants.ID_COL)));
        product.setName(productJSONObject.getString(ProductListConstants.NAME_COL));
        product.setDescription(productJSONObject.getString(ProductListConstants.DESCRIPTION_COL));
        product.setPrice(Double.parseDouble(productJSONObject.getString(ProductListConstants.PRICE_COL)));
        product.setImage(ImageUtils.getBitmapFromAssets(this,
                AssetsFolderConstants.PRODUCT_IMAGE_BY_ID(product.getId())));
        return product;
    }

    private void InitializeProductsRecyclerView(){
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),
                RecyclerViewConstants.COLUMN_COUNT);
        productsRecyclerView.setLayoutManager(gridLayoutManager);
        productsRecyclerView.addItemDecoration(new GridSpacingItemDecoration(
                RecyclerViewConstants.SPAN_COUNT,
                RecyclerViewConstants.SPACING,
                RecyclerViewConstants.INCLUDE_EDGE
        ));
        ProductListAdapter adapter = new ProductListAdapter(getApplicationContext(),productList);
        productsRecyclerView.setAdapter(adapter);
    }




}