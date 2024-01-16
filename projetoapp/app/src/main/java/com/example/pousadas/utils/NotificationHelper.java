package com.example.pousadas.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pousadas.R;
import com.example.pousadas.activities.ClientActivity;

public class NotificationHelper {

    private static final String CHANNEL_ID = "carrinho";
    private static final int NOTIFICATION_ID = 1;

    @SuppressLint("MissingPermission")
    public void showNotification(Context context, String title, String content) {
        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_menu_shop) // Ícone pequeno à esquerda da notificação
                .setContentTitle(title) // Título da notificação
                .setContentText(content) // Texto da notificação
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // Prioridade da notificação

        Intent intent = new Intent(context, ClientActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);

        createNotificationChannel(context);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal carrinho";
            String description = "Notificar carrinho online";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Registre o canal no sistema
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
