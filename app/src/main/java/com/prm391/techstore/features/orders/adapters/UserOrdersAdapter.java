package com.prm391.techstore.features.orders.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.prm391.techstore.R;
import com.prm391.techstore.features.orders.UserOrderDetailsFragment;
import com.prm391.techstore.features.orders.UserOrdersFragment;
import com.prm391.techstore.features.product_details.activities.ProductDetailsActivity;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.models.UserOrder;
import com.prm391.techstore.utils.FragmentUtils;


import java.util.List;


public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.ViewHolder> {
    private List<UserOrder> userOrders;
    private Context context;
    private UserOrdersFragment userOrdersFragment;

    public UserOrdersAdapter(Context context, List<UserOrder> userOrders, UserOrdersFragment userOrdersFragment) {
        this.context = context;
        this.userOrders = userOrders;
        this.userOrdersFragment = userOrdersFragment;
    }

    @NonNull
    @Override
    public UserOrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_order_card_view, parent, false);
        UserOrdersAdapter.ViewHolder viewHolder = new UserOrdersAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrdersAdapter.ViewHolder holder, int position) {
        UserOrder userOrder = userOrders.get(position);
        holder.bind(userOrder);
    }

    public void updateData(List<UserOrder> newUserOrderList) {
        this.userOrders.clear();
        this.userOrders.addAll(newUserOrderList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(userOrders==null) return 0;
        return userOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userOrderId;
        private TextView userOrderDate;
        private TextView userOrderQuantity;
        private TextView userOrderDeliveryStatus;
        private View view;
        private Handler handler;
        private AppCompatButton detailsButton;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            InitializeClassVariables();
        }


        public void bind(UserOrder userOrder) {
            this.userOrderId.setText(userOrder.getId());
            this.userOrderQuantity.setText(Integer.toString(userOrder.getAmount()));
            this.userOrderDate.setText(userOrder.getCreatedAt().toString());

            int deliveryStatus =userOrder.getStatus();
            if(deliveryStatus==0)
                this.userOrderDeliveryStatus.setText("In progress");
            else
                this.userOrderDeliveryStatus.setText("Delivered");
        }
        private void InitializeClassVariables() {
            this.userOrderId = (TextView) view.findViewById(R.id.userOrder_orderId);
            this.userOrderQuantity = (TextView) view.findViewById(R.id.userOrder_quantity);
            this.userOrderDate = (TextView) view.findViewById(R.id.userOrder_orderDate);
            this.userOrderDeliveryStatus = (TextView) view.findViewById(R.id.userOrder_deliveryStatus);
            this.handler = new Handler(Looper.getMainLooper());
            InitializeDetailsButton();
        }
        private void InitializeDetailsButton(){
            this.detailsButton = view.findViewById(R.id.userOrder_detailsButton);
            this.detailsButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    StartUserOrderDetailsFragment();
                }
            });
        }
        private void StartUserOrderDetailsFragment(){
            int position = getAdapterPosition();
            UserOrder userOrder =userOrders.get(position);
            FragmentUtils.replace(R.id.mainFrameLayout,
                     UserOrderDetailsFragment.newInstance(userOrder.getId()),
                    userOrdersFragment.getParentFragmentManager());
        }

    }
}
