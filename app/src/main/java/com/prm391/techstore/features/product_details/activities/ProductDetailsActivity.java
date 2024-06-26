package com.prm391.techstore.features.product_details.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.prm391.techstore.R;
import com.prm391.techstore.constants.ProductDetailsConstants;


public class ProductDetailsActivity extends AppCompatActivity {
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetupAndroid();
        SetupActionbar();
    }
    public String GetProductIdFromBundles() {
        Bundle extras = getIntent().getExtras();
        return extras.getString("productId");
    }
    public int GetCartProductAmountFromBundles() {
        Bundle extras = getIntent().getExtras();
        return extras.getInt("cartAmount");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void SetupAndroid() {
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void SetupActionbar() {
        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.drawable.ic_return_to_previous_activity);
        actionBar.setTitle(ProductDetailsConstants.TITLE_NAME);

        actionBar.setDisplayHomeAsUpEnabled(true);
    }


}