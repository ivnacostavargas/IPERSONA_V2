package com.gob.pgutierrezd.e_personas.utils;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by pgutierrezd on 24/10/2016.
 */
public class GpsLocation implements LocationListener {

    private LocationManager mLocationManager;
    private Geocoder mGeocoder;
    private Context context;
    private String mBestProvider;
    public LatLng coords;

    public GpsLocation(Context context) {
        this.context = context;
        this.coords = null;
    }

    @Override
    public void onLocationChanged(Location location) {
        coords = new LatLng(location.getLatitude(),location.getLongitude());
        try {
            List<Address> addresses=mGeocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 10);
            for(Address address:addresses){
                //textOut.append("\n"+address.getAddressLine(0));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void removeUpdates() throws SecurityException{
        mLocationManager.removeUpdates(this);
    }

    public void requestLocationUpdate() throws SecurityException{
        mLocationManager.requestLocationUpdates(mBestProvider, 1000, 10, this);
    }

    public String[] getLocationGPS() {
        mLocationManager=(LocationManager)context.getSystemService(context.LOCATION_SERVICE);
        List<String> providers=mLocationManager.getAllProviders();
        for(String prov:providers) {
            Log.d("Proveedores: ", prov);
        }
        Criteria criteria=new Criteria();
        mBestProvider=mLocationManager.getBestProvider(criteria, false);
        Log.d("Proveedor seleccionado", mBestProvider);
        try {
            mGeocoder = new Geocoder(context);
            Location lastLocation = mLocationManager.getLastKnownLocation(mBestProvider);
            if (lastLocation != null) {
                onLocationChanged(lastLocation);
            }
        }catch (SecurityException ex){}

        String[] data = new String[2];
        try{
            data[0] = String.valueOf(coords.latitude);
            data[1] = String.valueOf(coords.longitude);
        }catch (Exception e){
            data[0] = "";
            data[1] = "";
        }
        return data;
    }

}
