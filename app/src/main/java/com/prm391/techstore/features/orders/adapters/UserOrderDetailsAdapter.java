package com.prm391.techstore.features.orders.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.features.orders.UserOrderDetailsFragment;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.models.ProductByIdResponse;
import com.prm391.techstore.models.UserOrder;
import com.prm391.techstore.models.UserOrderDetailsResponse;
import com.prm391.techstore.utils.FragmentUtils;
import com.prm391.techstore.utils.ImageUtils;


import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOrderDetailsAdapter extends RecyclerView.Adapter<UserOrderDetailsAdapter.ViewHolder> {
    private List<UserOrderDetailsResponse.Data.UserOrderDetails> userOrderDetailsList;
    private TechStoreAPIInterface techStoreAPIInterface;
    private Context context;
    private UserOrderDetailsFragment userOrderDetailsFragment;

    public UserOrderDetailsAdapter(Context context, List<UserOrderDetailsResponse.Data.UserOrderDetails> userOrderDetailsList,
                                   UserOrderDetailsFragment userOrderDetailsFragment) {
        this.context = context;
        this.userOrderDetailsList = userOrderDetailsList;
        this.userOrderDetailsFragment = userOrderDetailsFragment;
        techStoreAPIInterface = TechStoreRetrofitClient.getClient().create(TechStoreAPIInterface.class);
    }

    @NonNull
    @Override
    public UserOrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_order_details_card_view, parent, false);
        UserOrderDetailsAdapter.ViewHolder viewHolder = new UserOrderDetailsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderDetailsAdapter.ViewHolder holder, int position) {
        UserOrderDetailsResponse.Data.UserOrderDetails userOrderDetails = userOrderDetailsList.get(position);
        holder.bind(userOrderDetails);
    }

    public void updateData(List<UserOrderDetailsResponse.Data.UserOrderDetails> newUserOrderList) {
        this.userOrderDetailsList.clear();
        this.userOrderDetailsList.addAll(newUserOrderList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userOrderDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productPrice;
        private ImageView productImageView;
        private View view;
        private Handler handler;
        private AppCompatButton detailsButton;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            InitializeUIComponents();
        }

        private void InitializeUIComponents() {
            this.productName = view.findViewById(R.id.userOrderDetails_productName);
            this.productPrice = view.findViewById(R.id.userOrderDetails_productPrice);
            this.productImageView = view.findViewById(R.id.userOrderDetails_productImage);
        }
        public void bind(UserOrderDetailsResponse.Data.UserOrderDetails userOrderDetails) {
            Call<ProductByIdResponse> call = techStoreAPIInterface.getProductById(userOrderDetails.getProductId());
            call.enqueue(new Callback<ProductByIdResponse>() {
                @Override
                public void onResponse(Call<ProductByIdResponse> call, Response<ProductByIdResponse> response) {
                    ProductByIdResponse responseBody = response.body();
                    if(responseBody==null) return;
                    Product product = responseBody.product;
                    productName.setText(product.getName());
                    productPrice.setText(String.format("%1$,.0f VND", product.getPrice()));
                    Executors.newSingleThreadExecutor().execute(() -> {
                        Bitmap bitmap = ImageUtils.getBitmapFromUrl(product.getImage());
                        if (bitmap != null) {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                // Set the Bitmap to the ImageView on the main thread

                                productImageView.setImageBitmap(bitmap);
                            });
                        }
                        });

                }

                @Override
                public void onFailure(Call<ProductByIdResponse> call, Throwable t) {
                    call.cancel();
                }
            });


        }

    }
}
