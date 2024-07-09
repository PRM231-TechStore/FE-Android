package com.prm391.techstore.features.payment_status.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.R;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.databinding.ActivityPaymentStatusBinding;
import com.prm391.techstore.features.main.activities.MainActivity;
import com.prm391.techstore.models.LoginInfo;
import com.prm391.techstore.models.UpdateOrderBody;
import com.prm391.techstore.models.UpdateOrderPayload;
import com.prm391.techstore.models.UpdateOrderResponse;
import com.prm391.techstore.utils.StorageUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentStatusActivity extends AppCompatActivity {
    ActivityPaymentStatusBinding activityPaymentStatusBinding;
    private TechStoreAPIInterface techStoreAPIInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            LoginInfo currentUser = null;
            currentUser = StorageUtils.GetFromStorage("user", currentUser, new TypeToken<LoginInfo>(){}.getType(), getBaseContext());
            activityPaymentStatusBinding = ActivityPaymentStatusBinding.inflate(getLayoutInflater());
            techStoreAPIInterface = TechStoreRetrofitClient.getClient(currentUser.getToken()).create(TechStoreAPIInterface.class);

            setContentView(activityPaymentStatusBinding.getRoot());
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("Transaction Detail");

            Intent intent = getIntent();
            Uri data = intent.getData();
            String responseCode = data.getQueryParameter("vnp_ResponseCode");
            String orderId = data.getQueryParameter("vnp_TxnRef");
            String orderInfo = data.getQueryParameter("vnp_OrderInfo");
            String orderDate = data.getQueryParameter("vnp_PayDate");
            double amount = Double.parseDouble(data.getQueryParameter("vnp_Amount")) / 100;
            ImageView statusLogo = this.findViewById(R.id.status_logo);
            TextView statusText = this.findViewById(R.id.status_text);
            TextView orderIdText = this.findViewById(R.id.payment_status_order_id);
            TextView amountText = this.findViewById(R.id.payment_status_order_amount);
            TextView orderDateText = this.findViewById(R.id.payment_status_order_date);
            SimpleDateFormat parser = new SimpleDateFormat("yyyyMMddHHmmss");
            Date parsedOrderDate = parser.parse(orderDate);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-YYYY");
            String formattedOrderDate = formatter.format(parsedOrderDate);
            if (responseCode.equals("00")) {
                statusLogo.setImageResource(R.drawable.green_checkmark);
                statusText.setText("Success");
                Call<UpdateOrderResponse> call = techStoreAPIInterface.updateOrder(orderId ,new UpdateOrderBody(new UpdateOrderPayload(1)));
                call.enqueue(new Callback<UpdateOrderResponse>() {
                    @Override
                    public void onResponse(Call<UpdateOrderResponse> call, Response<UpdateOrderResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<UpdateOrderResponse> call, Throwable t) {

                    }
                });
            } else if (responseCode.equals("24")) {
                statusLogo.setImageResource(R.drawable.red_cross);
                statusText.setText("Cancelled");
                Call<UpdateOrderResponse> call = techStoreAPIInterface.updateOrder(orderId ,new UpdateOrderBody(new UpdateOrderPayload(3)));
                call.enqueue(new Callback<UpdateOrderResponse>() {
                    @Override
                    public void onResponse(Call<UpdateOrderResponse> call, Response<UpdateOrderResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<UpdateOrderResponse> call, Throwable t) {

                    }
                });
            } else {
                statusLogo.setImageResource(R.drawable.red_cross);
                statusText.setText("Failed");
                Call<UpdateOrderResponse> call = techStoreAPIInterface.updateOrder(orderId ,new UpdateOrderBody(new UpdateOrderPayload(2)));
                call.enqueue(new Callback<UpdateOrderResponse>() {
                    @Override
                    public void onResponse(Call<UpdateOrderResponse> call, Response<UpdateOrderResponse> response) {

                    }

                    @Override
                    public void onFailure(Call<UpdateOrderResponse> call, Throwable t) {

                    }
                });
            }
            orderIdText.setText(orderId);
            amountText.setText(String.format("%1$,.0f VND", amount));
            orderDateText.setText(formattedOrderDate);
            Map<String ,Map<String, Integer>> userProductAmount = new HashMap<>();
            Map<String, Integer> userItemAmount = new HashMap<>();
            userItemAmount = StorageUtils.GetFromStorage("itemAmount", userItemAmount, new TypeToken<Map<String, Integer>>(){}.getType(), getBaseContext());
            userProductAmount = StorageUtils.GetFromStorage("cart", userProductAmount, new TypeToken<Map<String ,Map<String, Integer>>>(){}.getType(), getBaseContext());
            userItemAmount.put(currentUser.getUserId(), 0);
            userProductAmount.put(currentUser.getUserId(), new HashMap<String, Integer>());
            StorageUtils.SaveToStorage("cart", getBaseContext(), userProductAmount);
            StorageUtils.SaveToStorage("itemAmount", getBaseContext(), userItemAmount);
            Button backHomeButton = findViewById(R.id.payment_status_back_home_button);
            backHomeButton.setOnClickListener(v -> {
                Intent intent1 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent1);
            });
        } catch (Exception e) {

        }
    }
}
