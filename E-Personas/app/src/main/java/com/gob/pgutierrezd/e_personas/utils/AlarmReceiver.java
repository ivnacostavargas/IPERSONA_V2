package com.gob.pgutierrezd.e_personas.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by joules on 24/06/16.
 */
public class AlarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "TAG");
        wl.acquire();
        SharedPreferences preferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_COORDS, context.MODE_PRIVATE);
        String flag_coords = preferences.getString(Constants.SHARED_PREFERENCES_COORDS_FLAG, Constants.SHARED_PREFERENCES_COORDS_FLAG);
        String[] coords = new String[2];
        Log.d("AAA", "Entra a coords Alarm");

        if(flag_coords.equals("true")){
            Log.d("AAA",flag_coords);
            GpsLocation gpsLocation = new GpsLocation(context);
            coords = gpsLocation.getLocationGPS();

            Toast.makeText(context,"Coordenadas: "+coords[0]+","+coords[1],Toast.LENGTH_LONG).show();
            Log.d("AAA", "Coordenadas: "+coords[0]+","+coords[1]);
        }
        wl.release();
    }

}
