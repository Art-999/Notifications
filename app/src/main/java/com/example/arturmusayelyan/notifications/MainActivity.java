package com.example.arturmusayelyan.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import static android.graphics.Color.rgb;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final static int FIRST_NOTIF = 1;
    private final static int SECOND_NOTIF = 2;
    private final static int THIRD_NOTIF = 3;
    private final static String GROUP_KEY = "groupKey";

    String g = "G";

    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Notification notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.notify_me_button).setOnClickListener(this);
        findViewById(R.id.update_notif_button).setOnClickListener(this);
        findViewById(R.id.cancel_notif_button).setOnClickListener(this);
    }

    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");
        builder.setNumber(5);//uxaki tiv e avelacnum
        builder.setContentInfo("Content info");// text e avelacnum aj koxmic , verjin versanerum verevic e avelacnum
        builder.setColor(rgb(255, 128, 128)); // iconin guyn e avelacnum
        // builder.setWhen(8);//vochte ir default ayl mer koxmic trvac zamn e avelacnum notificationin
        builder.setShowWhen(true);//zame cuyc ta te che
        builder.setUsesChronometer(false);//true-i depqum zami poxaren chronometr e ashxatum ev cuc e talis te inchqan ancav notificationi galu pahic
        builder.setOngoing(false);//true-i depqum stanum e mec prioritet ev usere chi karox ayn sovorakan swipe-ov jnjel
        builder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
        //builder.setLights(Color.RED,3000,3000);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        //builder.setPriority(NotificationCompat.PRIORITY_MAX);  // -2 <-> 2
        //builder.setTimeoutAfter(3000); //ashxatum e API 26-ic heto: nshvac zamanakic heto cancel e anum mer notificatione

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.smashicons, options);
        builder.setLargeIcon(bitmap);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createNotificationWithProgressBar() {
        builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");
        builder.setSmallIcon(R.drawable.pets);
        builder.setProgress(100, 0, true);
        notification = builder.build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress;
                for (progress = 0; progress <= 100; progress += 10) {
                    builder.setProgress(100, progress, false);
                    builder.setContentText(progress + "%");
                    notification = builder.build();
                    notificationManager.notify(FIRST_NOTIF, notification);
                    SystemClock.sleep(1000);
                }
                builder.setContentText("Download complete");
                builder.setProgress(0, 0, false);
                builder.setAutoCancel(true);
                notification = builder.build();
                notificationManager.notify(FIRST_NOTIF, notification);
            }
        }).start();
    }

    private void updateNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text changed");

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createManyNotifications() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(SECOND_NOTIF, notification);
        notificationManager.notify(THIRD_NOTIF, notification);
        notificationManager.notify(4, notification);
        notificationManager.notify(5, notification);
        notificationManager.notify(6, notification);
    }

    private void cancelNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(FIRST_NOTIF);
    }

    private void cancelAllNotifications() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    private void createClickableNotification() {
        //Create PendingIntent
        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Create Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");
        builder.setContentIntent(pendingIntent);//pending intente drecinq notificationi mej
        builder.setAutoCancel(true); //auto cancel after click

        Notification notification = builder.build();

        //Show notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createNotificationIncreaseTextSize() {
        String longText = "To have a notification appear in an expanded view, " + "first create a NotificationCompat.Builder object " + "with the normal view options you want. " + "Next, call Builder.setStyle() with an " + "expanded layout object as its argument.";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(longText));


        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createNotificationIncreasePictureSize() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.smashicons, options);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }


    private void createNotificationInboxStyle() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");

        builder.setStyle(new NotificationCompat.InboxStyle().addLine("Line 1").addLine("Line 2").addLine("Line 3").setBigContentTitle("Extended title").setSummaryText("+5 more"));

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createNotificationMessagingStyle() {
        NotificationCompat.MessagingStyle messagingStyle = new NotificationCompat.MessagingStyle("You");
        messagingStyle.setConversationTitle("Android chat");
        messagingStyle.addMessage("Всем привет!", System.currentTimeMillis(), "Ivan");
        messagingStyle.addMessage("Кто перешел на новую студию, как оно?", System.currentTimeMillis(), "Ivan");
        messagingStyle.addMessage("Я пока не переходил, жду отзывов", System.currentTimeMillis(), "Andrey");
        messagingStyle.addMessage("Я перешел", System.currentTimeMillis(), null);
        messagingStyle.addMessage("Было несколько проблем, но все решаемо", System.currentTimeMillis(), null);
        messagingStyle.addMessage("Ок, спасибо!", System.currentTimeMillis(), "Ivan");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");
        builder.setStyle(messagingStyle);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createNotificationWithActionButtons() {
        //clicke chi ashxatum
        Intent intent = new Intent(this, SecondActivity.class);
        intent.setAction("com.example.arturmusayelyan.notifications.mainactivity.g");
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");
        builder.addAction(android.R.drawable.btn_plus, "Next activity", pendingIntent);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createCustomViewNotification() {
        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.text_view, "Custom Notification text");
        remoteViews.setOnClickPendingIntent(R.id.liner_notification_layout, pendingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContent(remoteViews);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createCustomExtendedViewNotification() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notifications3);
        remoteViews.setTextViewText(R.id.simple_text_view, "Custom notification text");

        RemoteViews remoteViewsExtended = new RemoteViews(getPackageName(), R.layout.notification3_extended);
        remoteViewsExtended.setTextViewText(R.id.extended_text_view, "Extended custom notification text");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setCustomContentView(remoteViews);
        builder.setCustomBigContentView(remoteViewsExtended);
        builder.setStyle(new NotificationCompat.DecoratedCustomViewStyle());

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createCustomViewNotificationsWithActiveButtons() {
        Intent intent = new Intent(this, SecondActivity.class);
        PendingIntent secondActivity = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent intent2 = new Intent(this, MyClass.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notifications2);
        remoteViews.setTextViewText(R.id.text_view2, "Custom Notification text");
        remoteViews.setOnClickPendingIntent(R.id.second_activity_button, secondActivity);
        remoteViews.setOnClickPendingIntent(R.id.delete_button, pendingIntent);

        builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContent(remoteViews);

        notification = builder.build();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(FIRST_NOTIF, notification);
    }

    private void createGroupOfNotifications() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.pets);
        builder.setContentTitle("Title");
        builder.setContentText("Notification text");

        builder.setContentInfo("user_mail.com");
        builder.setGroup(GROUP_KEY);
        builder.setGroupSummary(true);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(56, notification);
        notificationManager.notify(57,notification);
        notificationManager.notify(58,notification);
        notificationManager.notify(59,notification);
        notificationManager.notify(60,notification);
        notificationManager.notify(61,notification);
        notificationManager.notify(62,notification);
        notificationManager.notify(63,notification);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notify_me_button:
                // createNotification();
                //createManyNotifications();
                //createClickableNotification();
                //createNotificationWithProgressBar();
                //createNotificationIncreaseTextSize();
                //createNotificationIncreasePictureSize();
                //createNotificationInboxStyle();
                //createNotificationMessagingStyle();
                // createNotificationWithActionButtons();
                // createCustomViewNotification();
                //createCustomViewNotificationsWithActiveButtons();
                //createCustomExtendedViewNotification();
                createGroupOfNotifications();
                break;
            case R.id.update_notif_button:
                updateNotification();
                break;
            case R.id.cancel_notif_button:
                //cancelNotification();
                cancelAllNotifications();
                break;
        }
    }

    class MyClass extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.cancel(FIRST_NOTIF);
            Log.d("askljdbahksdb", "askhdbsad");
        }
    }
}
