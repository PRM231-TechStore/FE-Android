package com.prm391.techstore.features.orders.adapters;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm391.techstore.R;
import com.prm391.techstore.models.UserOrder;


import java.util.List;


public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.ViewHolder> {
    private List<UserOrder> userOrders;
    private Context context;

    public UserOrdersAdapter(Context context, List<UserOrder> userOrders) {
        this.context = context;
        this.userOrders = userOrders;
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
        return userOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userOrderId;
        private TextView userOrderDate;
        private TextView userOrderQuantity;
        private TextView userOrderDeliveryStatus;
        private View view;
        private Handler handler;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            InitializeClassVariables();
        }

        private void InitializeClassVariables() {
            this.userOrderId = (TextView) view.findViewById(R.id.userOrder_orderId);
            this.userOrderQuantity = (TextView) view.findViewById(R.id.userOrder_quantity);
            this.userOrderDate = (TextView) view.findViewById(R.id.userOrder_orderDate);
            this.userOrderDeliveryStatus = (TextView) view.findViewById(R.id.userOrder_deliveryStatus);
            this.handler = new Handler(Looper.getMainLooper());
        }

        public void bind(UserOrder userOrder) {
            this.userOrderId.setText(userOrder.getOrderId());
            this.userOrderQuantity.setText(Integer.toString(userOrder.getAmount()));
            this.userOrderDate.setText(userOrder.getCreatedAt().toString());
            this.userOrderDeliveryStatus.setText(Integer.toString(userOrder.getStatus()));
        }
    }
}
