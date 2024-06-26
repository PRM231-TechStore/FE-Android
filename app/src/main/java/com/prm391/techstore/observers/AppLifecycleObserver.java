package com.prm391.techstore.observers;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.reflect.TypeToken;
import com.prm391.techstore.R;
import com.prm391.techstore.features.main.activities.MainActivity;
import com.prm391.techstore.utils.StorageUtils;

public class AppLifecycleObserver implements DefaultLifecycleObserver {
    private Context context;
    public AppLifecycleObserver(Context context) {
        this.context = context;
    }
    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        int itemAmount = 0;
        itemAmount = StorageUtils.GetFromStorage("itemAmount", itemAmount, new TypeToken<Integer>(){}.getType(), context);

        if (itemAmount > 0) {
            Intent intent = new Intent(context, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "Cart")
                    .setSmallIcon(R.drawable.ic_cart)
                    .setContentTitle("Cart")
                    .setContentText(String.format("Items in cart: %d", itemAmount))
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            notificationManager.notify(1, mBuilder.build());
        }
    }
}
