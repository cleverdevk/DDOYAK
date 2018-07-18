package org.androidtown.r_ddoalarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver{

    Context context;
    PendingIntent pendingIntent;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        this.context = context;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);

        //PARTIAL_WAKE_LOCK의 경우 유저가 단말을 sleep시켜도 계속 CPU가 돈다.
        //따라서 acquire 후 꼭 release 해줘야 함.
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"");

        wakeLock.acquire();

        Log.d("alarm","gogo");
        Toast.makeText(context,"알람이 울려울려",Toast.LENGTH_LONG).show();
        //Toast toast = Toast.makeText(context,"알람이 울려울려울려", Toast.LENGTH_LONG);
        //toast.setGravity(Gravity.TOP,0,200);
        //toast.show();

        wakeLock.release();

        //try{
        //    intent = new Intent(context, removeActivity.class);
        //    pendingIntent = PendingIntent.getActivity(context,111,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //    Toast.makeText(context,"알람해제화면",Toast.LENGTH_SHORT).show();
        //    Log.d("ServicePending++ :",""+pendingIntent.toString());
        //    pendingIntent.send();
        //} catch (PendingIntent.CanceledException e) {
       //     e.printStackTrace();
        //}
        notification();
    }

    void notification(){

        Intent intent = new Intent();
        //알림 사운드
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //큰 아이콘
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),android.R.drawable.ic_menu_gallery);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_menu_gallery)
                .setLargeIcon(bitmap)
                .setSound(soundUri)
                .setContentTitle("또약")
                .setContentText("복용 시간입니당!")
                .setVibrate(new long[]{200, 200, 500, 300})
                .setContentIntent(pendingIntent);




        NotificationManager notificationMAnager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationMAnager.notify(0,notificationBuilder.build());


    }


}
