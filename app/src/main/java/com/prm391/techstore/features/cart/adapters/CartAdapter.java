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

import com.prm391.techstore.R;
import com.prm391.techstore.features.product_details.activities.ProductDetailsActivity;
import com.prm391.techstore.models.CartProduct;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.utils.ImageUtils;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.List;
import java.util.concurrent.Executors;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartProduct> products;
    private Context context;
    public CartAdapter(Context context, List<CartProduct> products){
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_cart_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
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

        public ViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.productCartImage);
            productName = (TextView) view.findViewById(R.id.productCartName);
            productPrice = (TextView) view.findViewById(R.id.productCartPrice);
            numberPicker = (NumberPicker) view.findViewById(R.id.cartNumberPicker);
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
        }
    }
}
