package com.gob.pgutierrezd.e_personas.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by pgutierrezd on 12/09/2016.
 */
public class Connectivity {

    private ConnectivityManager conMgr;
    private Context context;

    public Connectivity(Context context){
        this.context = context;
        conMgr = null;
    }

    public Boolean conectadoWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean conectadoRedMovil(){
        ConnectivityManager connectivity = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

}
