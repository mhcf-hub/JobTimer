package com.example.jobtimer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;


public class ForegroundService extends Service {

    //Timer for timingfunction
    Timer myTimer;

    //NotificationManager for notify about timing
    private NotificationManager mNotificationManager;

    //
    Timing jTiming;

    public void setTiming(Timing timing){
        jTiming = timing;
    }


    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int input = intent.getIntExtra("inputExtra", -1);
        final int seconds = 0;
        myTimer = new Timer();
        TimerTask tt = new TimerTask() {
            int sSeconds = seconds;
            @Override
            public void run() {
                loadNotification(sSeconds);
                System.out.println(sSeconds + " test sseconds");
                if (jTiming.getEnded() != 0) {
                    sSeconds++;
                    currentTimingTextView.setText(minutesHours(secondsCounted) + "");
                    System.out.println(secondsCounted + " testor timing");
                    currentTiming.setSeconds(secondsCounted);
                    timingViewModel.update(currentTiming);
                    //notificationLoad();
                }
            };
        };
        myTimer.scheduleAtFixedRate(tt,0,1000);



        //do heavy work on a background thread
        //stopSelf();
       return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public void loadNotification(int seconds){
        //Declare NotificationCompat mBuilder
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        //Declare intent for Nofitication to return to
        Intent ii = new Intent(this, SingleJobUseActivity.class);
//        ii.putExtra("id", idIn);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(ii);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        //Configure NotificationCompat mBuilder
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("seconds: " + seconds);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
//        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        //mNotificationManager.notify(0, mBuilder.build());
        startForeground(1, mBuilder.build());

//        createNotificationChannel();
//        Intent notificationIntent = new Intent(this, SingleJobUseActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                0, notificationIntent, 0);
//        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
//                .setContentTitle("Foreground Service")
//                .setContentText(seconds + " seconds")
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentIntent(pendingIntent)
//                .setPriority(Notification.PRIORITY_HIGH)
//                .build();
      //  startForeground(1, notification);
    }


}