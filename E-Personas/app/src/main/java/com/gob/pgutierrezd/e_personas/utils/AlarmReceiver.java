package com.gob.pgutierrezd.e_personas.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by joules on 24/06/16.
 */
public class AlarmReceiver extends BroadcastReceiver{

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "TAG");
        wl.acquire();
        Toast.makeText(context,"Hola",Toast.LENGTH_LONG).show();
        /*if(){
            notification();
            dos horas = 1000*60*60*2 = 7200000
            1 min = 1000 * 60 = 60000
        }*/
        wl.release();
    }

}
