package com.example.pousadas.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.pousadas.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MQTT {
    private static final String CLIENT = "cliente";
    private static final String TOPIC = "Carrinho";
    private static final String CHANNEL_ID = "MQTT_CHANNEL";

    private MqttAndroidClient mqttClient;
    private NotificationManager notificationManager;
    private Context context;

    public MQTT(Context context, final String host) {
        this.context = context;

        mqttClient = new MqttAndroidClient(context, host, CLIENT);

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        connectMQTT();
    }

    private void connectMQTT() {
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("--> Connection lost: " + cause.getMessage());
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                System.out.println("--> " + topic + ", " + message.toString());
                showNotification(message.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);

        try {
            IMqttToken token = mqttClient.connect(mqttConnectOptions);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    System.out.println("--> Connected MQTT broker");
                    subscribeToTopic();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    System.out.println("--> Failed");
                }
            });
        }

        catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToTopic() {
        try {
            mqttClient.subscribe(TOPIC, 0);
            System.out.println("--> " + TOPIC);
        }
        catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publish(String topic, String info)
    {
        byte[] encodedInfo = new byte[0];
        try {
            encodedInfo = info.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedInfo);
            mqttClient.publish(topic, message);
            System.out.println("-->publish done");
        } catch (MqttException e) {
            System.out.println("-->deu erro");
        }catch (Exception e) {
            System.out.println("-->deu erro 2");
            System.out.println("-->" + e.getMessage());

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showNotification(String notification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();

            Notification.Builder builder = new Notification.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_admin_finance)
                    .setContentTitle("Nova Mensagem MQTT")
                    .setContentText(notification);

            notificationManager.notify(1, builder.build());
        } else {
            // Em versões mais antigas, você pode usar NotificationCompat
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_admin_finance)
                    .setContentTitle("Nova Mensagem MQTT")
                    .setContentText(notification);

            notificationManager.notify(1, builder.build());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        CharSequence name = "MQTT Channel";
        String description = "Canal para notificações MQTT";

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
    }

}

