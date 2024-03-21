package com.jordanbrakefield.vacationplanner.UI;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.jordanbrakefield.vacationplanner.R;

public class MyReceiver extends BroadcastReceiver {
    String channel_id = "test";
    static int notificationID;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String excursionKey = intent.getStringExtra("key");
        String startingKey = intent.getStringExtra("startingKey");
        String endingKey = intent.getStringExtra("endingKey");


        if (excursionKey != null) {
            handleExcursionNotification(context, excursionKey);
        } else if (startingKey != null) {
            handleVacationNotification(context, startingKey);
        } else if (endingKey != null) {
            handleVacationNotification(context, endingKey);
        }

    }

    private void handleExcursionNotification(Context context, String excursionKey){

        Toast.makeText(context, excursionKey, Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
        .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(excursionKey)
                .setContentTitle("Excursion is starting!").build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);
    }

    private void handleVacationNotification(Context context, String vacationKey){
        Toast.makeText(context, vacationKey, Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(vacationKey)
                .setContentTitle("Vacation Update!").build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID){
        CharSequence name = "mychannelname";
        String description = "mychanneldescription";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}