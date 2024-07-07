package com.prm391.techstore.features.cart.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.R;
import com.prm391.techstore.features.product_details.activities.ProductDetailsActivity;
import com.prm391.techstore.models.CartProduct;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.utils.ImageUtils;
import com.prm391.techstore.utils.StorageUtils;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartProduct> products;
    private Context context;
    private TextView priceView;
    private double total;
    public CartAdapter(Context context, List<CartProduct> products, TextView priceView, double total){
        this.context = context;
        this.products = products;
        this.priceView = priceView;
        this.total = total;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_cart_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartProduct product = products.get(position);
//        holder.image.setImageBitmap(ImageUtils.getBitmapFromUrl(product.getImage()));
        holder.productName.setText( product.getName());
        holder.productPrice.setText(String.format("%1$,.0f VND",product.getPrice()));
        Executors.newSingleThreadExecutor().execute(() -> {
            Bitmap bitmap = ImageUtils.getBitmapFromUrl(product.getImage());
            if (bitmap != null) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    // Set the Bitmap to the ImageView on the main thread
                    holder.image.setImageBitmap(bitmap);
                });
            }
        });
        holder.numberPicker.setValue(product.getAmount());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView productName;
        TextView productPrice;
        NumberPicker numberPicker;
        TextView removeText;

        CartAdapter adapter;

        public ViewHolder(View view, CartAdapter adapter) {
            super(view);
            image = (ImageView) view.findViewById(R.id.productCartImage);
            productName = (TextView) view.findViewById(R.id.productCartName);
            productPrice = (TextView) view.findViewById(R.id.productCartPrice);
            numberPicker = (NumberPicker) view.findViewById(R.id.cartNumberPicker);
            removeText = (TextView) view.findViewById(R.id.cartRemove);
            this.adapter = adapter;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        CartProduct product = products.get(position);

                        Intent intent = new Intent(context, ProductDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("productId", product.getId());
                        intent.putExtra("cartAmount", product.getAmount());// Pass product ID as intent extra
                        context.startActivity(intent);
                    }
                }
            });
            removeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int itemAmount = 0;
                    itemAmount = StorageUtils.GetFromStorage("itemAmount", itemAmount, new TypeToken<Integer>(){}.getType(), context);
                    int position = getAdapterPosition();
                    CartProduct product = products.get(position);
                    Map<String, Integer> productAmount = new HashMap<>();
                    productAmount = StorageUtils.GetFromStorage("cart", productAmount, new TypeToken<Map<String, Integer>>(){}.getType(), context);
                    productAmount.remove(product.getId());
                    products.remove(product);
                    itemAmount -= product.getAmount();
                    total -= product.getPrice() * product.getAmount();
                    priceView.setText(String.format("%1$,.0f VND", total));
                    StorageUtils.SaveToStorage("cart", context, productAmount);
                    StorageUtils.SaveToStorage("itemAmount", context, itemAmount);
                    adapter.notifyItemRemoved(position);
                }
            });
            numberPicker.setValueChangedListener(new ValueChangedListener() {
                @Override
                public void valueChanged(int value, ActionEnum action) {
                    int position = getAdapterPosition();
                    CartProduct product = products.get(position);
                    int itemAmount = 0;
                    itemAmount = StorageUtils.GetFromStorage("itemAmount", itemAmount, new TypeToken<Integer>(){}.getType(), context);
                    Map<String, Integer> productAmount = new HashMap<>();
                    productAmount = StorageUtils.GetFromStorage("cart", productAmount, new TypeToken<Map<String, Integer>>(){}.getType(), context);
                    int oldAmount = productAmount.get(product.getId());
                    productAmount.put(product.getId(), value);
                    itemAmount += value - oldAmount;
                    total += product.getPrice() * (value - oldAmount);

                    priceView.setText(String.format("%1$,.0f VND", total));

                    product.setAmount(value);
                    StorageUtils.SaveToStorage("cart", context, productAmount);
                    StorageUtils.SaveToStorage("itemAmount", context, itemAmount);
                }
            });
        }
    }
}
