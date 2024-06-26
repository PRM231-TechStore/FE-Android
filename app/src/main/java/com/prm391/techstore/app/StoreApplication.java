package com.prm391.techstore.app;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.lifecycle.ProcessLifecycleOwner;

import com.prm391.techstore.features.main.activities.MainActivity;
import com.prm391.techstore.observers.AppLifecycleObserver;

public class StoreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new AppLifecycleObserver(getApplicationContext()));
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Cart";
            String description = "Cart amount";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel("Cart", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
