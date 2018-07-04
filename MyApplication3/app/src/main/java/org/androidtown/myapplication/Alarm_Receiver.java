package org.androidtown.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by HeeSu on 2018-07-04.
 */

public class Alarm_Receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("we are in the receiver","Yeah!");
    }
}
