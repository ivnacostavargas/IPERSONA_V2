package com.gob.pgutierrezd.e_personas.views;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.utils.AlarmReceiver;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.googlemaps.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleMap.OnMarkerDragListener, LocationListener {

    private Button mBtnContinuar;
    private TextView mTxtInstruccionesMaker;

    private MapFragment mMapFragment;
    private GoogleMap mMap;

    private LocationManager mLocationManager;
    private Geocoder mGeocoder;

    private String mBestProvider;
    private LatLng coords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViews();
        getLocationGPS();

        mBtnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,EncuestaActivity.class));
            }
        });
    }

    private void findViews() {
        mBtnContinuar = (Button) findViewById(R.id.btnContinuar);
        mTxtInstruccionesMaker = (TextView) findViewById(R.id.textInstrucciones);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coords, 17));
        mMap.addMarker(new MarkerOptions()
                .position(coords)
                .draggable(true));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //mMap.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Constants.LOCATION_REQUEST_CODE);
            }
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerDragListener(this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.LOCATION_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onPause() throws SecurityException{
        // TODO Auto-generated method stub
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    @Override
    protected void onResume() throws SecurityException{
        // TODO Auto-generated method stub
        super.onResume();
        mLocationManager.requestLocationUpdates(mBestProvider, 1000, 10, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        String texto=String.format("Lat:\t %f\nLong:\t %f\nAlt: \t %f\n",location.getLatitude(),
                location.getLongitude(),location.getAltitude());
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
        getMap();
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

    public void getMap() {
        mMapFragment = mMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mapInicioEncuesta, mMapFragment)
                .commit();

        mMapFragment.getMapAsync(this);
    }

    public void getLocationGPS() {
        mLocationManager=(LocationManager)getSystemService(LOCATION_SERVICE);
        List<String> providers=mLocationManager.getAllProviders();
        for(String prov:providers)
            Log.d("Proveedores: ", prov);
        Criteria criteria=new Criteria();
        mBestProvider=mLocationManager.getBestProvider(criteria, false);
        Log.d("Proveedor seleccionado", mBestProvider);
        try {
            mGeocoder = new Geocoder(this);
            Location lastLocation = mLocationManager.getLastKnownLocation(mBestProvider);
            if (lastLocation != null)
                onLocationChanged(lastLocation);
        }catch (SecurityException ex){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_close)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void notificacion(){
        Context context = getApplicationContext();
        PendingIntent alarmIntent;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 1);

        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 30000, alarmIntent);
    }

}
