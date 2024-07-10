package com.prm391.techstore.features.cart.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.clients.TechStoreAPIInterface;
import com.prm391.techstore.clients.TechStoreRetrofitClient;
import com.prm391.techstore.features.cart.adapters.CartAdapter;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prm391.techstore.R;
import com.prm391.techstore.models.CartProduct;
import com.prm391.techstore.models.CreateOrderPayload;
import com.prm391.techstore.models.CreateOrderBody;
import com.prm391.techstore.models.CreateOrderResponse;
import com.prm391.techstore.models.LoginInfo;
import com.prm391.techstore.models.Product;
import com.prm391.techstore.models.ProductByIdResponse;
import com.prm391.techstore.models.RequestProductObj;
import com.prm391.techstore.utils.StorageUtils;
import com.vnpay.authentication.VNP_AuthenticationActivity;
import com.vnpay.authentication.VNP_SdkCompletedCallback;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private List<CartProduct> cartProductList;
    private double total;
    private View view;
    private boolean firstView;

    private RecyclerView cartRecyclerView;

    private CartAdapter cartAdapter;

    private TechStoreAPIInterface techStoreAPIInterface;

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.productList_searchMenuItem);
        if (item != null)
            item.setVisible(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cart, container, false);
        try {
            super.onCreate(savedInstanceState);
            firstView = true;
            InitializeClassVariables();
            AddOnClickForCheckout();
            GetProductsFromAPI();
            InitializeProductsRecyclerView();
            UpdateCheckoutView();
        } catch (Exception e) {
            e.getMessage();
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        try {
            super.onResume();
            if (!firstView) {
                cartProductList = new ArrayList<CartProduct>();
                total = 0;
                GetProductsFromAPI();
                InitializeProductsRecyclerView();
                UpdateCheckoutView();
            }
            firstView = false;
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void GetProductsFromAPI() throws Exception {
        LoginInfo currentUser = null;
        currentUser = StorageUtils.GetFromStorage("user", currentUser, new TypeToken<LoginInfo>() {
        }.getType(), getContext());
        Map<String, Map<String, Integer>> userProductAmount = new HashMap<>();
        userProductAmount = StorageUtils.GetFromStorage("cart", userProductAmount, new TypeToken<Map<String, Map<String, Integer>>>() {
        }.getType(), getContext());
        Map<String, Integer> productAmount = userProductAmount.get(currentUser.getUserId());

        for (Map.Entry<String, Integer> entry : productAmount.entrySet()) {
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

                    total += cartProduct.getPrice() * cartProduct.getAmount();
                    InitializeProductsRecyclerView();
                    UpdateCheckoutView();
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
        total = 0;
        LoginInfo currentUser = null;
        currentUser = StorageUtils.GetFromStorage("user", currentUser, new TypeToken<LoginInfo>() {
        }.getType(), getContext());
        techStoreAPIInterface = TechStoreRetrofitClient.getClient(currentUser.getToken()).create(TechStoreAPIInterface.class);
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
        Activity activity = getActivity();
        if (activity == null) return;
        Context context = activity.getApplicationContext();
        if (context == null) return;
        cartRecyclerView = view.findViewById(R.id.cartRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        cartRecyclerView.setLayoutManager(gridLayoutManager);
        cartAdapter = new CartAdapter(context, cartProductList, (TextView) view.findViewById(R.id.totalPrice), total);
        cartRecyclerView.setAdapter(cartAdapter);

    }

    private void UpdateCheckoutView() {
        TextView totalPriceText = (TextView) view.findViewById(R.id.totalPrice);
        totalPriceText.setText(String.format("%1$,.0f VND", total));
    }

    private void AddOnClickForCheckout() {
        FlexboxLayout checkoutButton = (FlexboxLayout) view.findViewById(R.id.checkoutButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (total > 0) {
                        CreateOrderAndDoCheckout();
                    }
                } catch (UnsupportedEncodingException e) {

                }
            }
        });
    }

    private void CreateOrderAndDoCheckout() throws UnsupportedEncodingException {
        try {
            LoginInfo currentUser = null;
            currentUser = StorageUtils.GetFromStorage("user", currentUser, new TypeToken<LoginInfo>() {
            }.getType(), getContext());
            List<RequestProductObj> requestProductObjs = new ArrayList<>();
            for (CartProduct product : cartProductList) {
                requestProductObjs.add(new RequestProductObj(product.getId(), product.getAmount()));
            }
            CreateOrderBody createOrderBody = new CreateOrderBody(new CreateOrderPayload(currentUser.getUserId(), (long) total, requestProductObjs));
            Call<CreateOrderResponse> call = techStoreAPIInterface.createOrder(createOrderBody);
            call.enqueue(new Callback<CreateOrderResponse>() {
                @Override
                public void onResponse(Call<CreateOrderResponse> call, Response<CreateOrderResponse> response) {

                    CreateOrderResponse body = response.body();
                    String orderId = body.getData().getOrderId();
                    try {
                        DoCheckout(orderId);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onFailure(Call<CreateOrderResponse> call, Throwable t) {

                }
            });
        } catch (Exception e) {

        }
    }

    private void DoCheckout(String orderId) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = String.format("Thanh toán đơn hàng %s", orderId);
        String orderType = "other";
        String vnp_TxnRef = orderId;
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = "206SKOZH";

        long amount = (long) total * 100;
        Map vnp_Params = new HashMap();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = "";
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = "";
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", "resultactivity://sdk.merchantbackapp");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Billing
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512("XGT6C8NAP5HJ2BX500YJ4X09KA7VORHM", hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html" + "?" + queryUrl;
        Intent intent = new Intent(getContext(), VNP_AuthenticationActivity.class);
        intent.putExtra("url", paymentUrl); //bắt buộc, VNPAY cung cấp
        intent.putExtra("tmn_code", "206SKOZH"); //bắt buộc, VNPAY cung cấp
        intent.putExtra("scheme", "resultactivity://"); //bắt buộc, scheme để mở lại app khi có kết quả thanh toán từ mobile banking
        intent.putExtra("is_sandbox", true); //bắt buộc, true <=> môi trường test, true <=> môi trường live
        VNP_AuthenticationActivity.setSdkCompletedCallback(new VNP_SdkCompletedCallback() {
            @Override
            public void sdkAction(String action) {
                Log.wtf("SplashActivity", "action: " + action);
                //action == AppBackAction
                //Người dùng nhấn back từ sdk để quay lại

                //action == CallMobileBankingApp
                //Người dùng nhấn chọn thanh toán qua app thanh toán (Mobile Banking, Ví...)
                //lúc này app tích hợp sẽ cần lưu lại cái PNR, khi nào người dùng mở lại app tích hợp thì sẽ gọi kiểm tra trạng thái thanh toán của PNR Đó xem đã thanh toán hay chưa.

                //action == WebBackAction
                //Người dùng nhấn back từ trang thanh toán thành công khi thanh toán qua thẻ khi url có chứa: cancel.sdk.merchantbackapp

                //action == FaildBackAction
                //giao dịch thanh toán bị failed

                //action == SuccessBackAction
                //thanh toán thành công trên webview
                if (action.equals("AppBackAction")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("resultactivity://sdk.merchantbackapp"));
                    startActivity(intent);
                }
            }
        });
        startActivity(intent);
    }

    private static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
}