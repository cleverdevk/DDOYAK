package com.example.caucse.ddoyak;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

public class NotificationHelper extends ContextWrapper {

    public static final String CHANNEL_ID = "Channel ID";
    public static final String CHANNEL_NAME = "ddoyak Channel";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    private void createChannels(){
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("복용 알림용 채널");
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        // 알림 채널 설정
        channel.enableLights(true);
        // 기기가 이 기능을 지원한다면, 이 채널에 게시되는 알림에 대한 알림 불빛 색상을 설정
        channel.setLightColor(Color.RED);
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager(){
        if(manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getChannelNotification(String title, String body){
        return new Notification.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentText(body)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true);
    }
}