package com.prm391.techstore.features.product_list.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.prm391.techstore.models.Product;
import com.prm391.techstore.utils.ImageUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<Product> products;
    private Context context;
    public ProductListAdapter(Context context, List<Product> products){
        this.context = context;
        this.products = products;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }

    public void updateData(List<Product> newProductList) {
        this.products.clear();
        this.products.addAll(newProductList);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImageView;
        private View view;
        private TextView productName;
        private TextView productPrice;
        private Handler handler;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            InitializeClassVariables();
        }
        private void InitializeClassVariables(){
            this.productImageView = (ImageView) view.findViewById(R.id.productImage);
            this.productName = (TextView) view.findViewById(R.id.productName);
            this.productPrice = (TextView) view.findViewById(R.id.productPrice);
            this.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Product product = products.get(position);

                        Intent intent = new Intent(context, ProductDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("productId", product.getId()); // Pass product ID as intent extra
                        context.startActivity(intent);
                    }
                }
            });
            this.handler = new Handler(Looper.getMainLooper());
        }
        public void bind(Product product) {
            this.productName.setText(product.getName());
            this.productPrice.setText(String.format("%1$,.0f VND",product.getPrice()));
            Executors.newSingleThreadExecutor().execute(() -> {
                Bitmap bitmap = ImageUtils.getBitmapFromUrl(product.getImage());
                if (bitmap != null) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        // Set the Bitmap to the ImageView on the main thread

                        this.productImageView.setImageBitmap(bitmap);
                    });
                }
            });
        }
    }
}

