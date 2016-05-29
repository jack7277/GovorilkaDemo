package edu.cmu.pocketsphinx.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class lockScreenReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        // если экран выключен то запускаем наш лок скрин
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            wasScreenOn = false;
            Intent intent1 = new Intent(context, GovorilkaActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }
    }
}
