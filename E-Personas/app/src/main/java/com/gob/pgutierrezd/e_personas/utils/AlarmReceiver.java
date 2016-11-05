package com.gob.pgutierrezd.e_personas.utils;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.gob.pgutierrezd.e_personas.sqlite.DataBaseOpenHelper;

/**
 * Created by joules on 24/06/16.
 */
public class AlarmReceiver extends BroadcastReceiver{

    protected SQLiteOpenHelper dbHelper;
    protected SQLiteDatabase database;

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "TAG");
        wl.acquire();
            dbHelper = new DataBaseOpenHelper(context);
            saveCoords(context);
        wl.release();
    }

    private void saveCoords(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_COORDS, context.MODE_PRIVATE);
        String flag_coords = preferences.getString(Constants.SHARED_PREFERENCES_COORDS_FLAG, Constants.SHARED_PREFERENCES_COORDS_FLAG);
        String[] coords = new String[2];

        if(flag_coords.equals("true")){
            database = dbHelper.getWritableDatabase();
            GpsLocation gpsLocation = new GpsLocation(context);
            coords = gpsLocation.getLocationGPS();

            ContentValues values = new ContentValues();
            values.put(Constants.LATITUD, coords[0]);
            values.put(Constants.LONGITUD, coords[1]);
            database.insert("coords_prueba", null,values);
        }
    }

}
