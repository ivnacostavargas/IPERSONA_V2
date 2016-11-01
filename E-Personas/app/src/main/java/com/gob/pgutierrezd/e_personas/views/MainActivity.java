package com.gob.pgutierrezd.e_personas.views;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
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
        GoogleMap.OnMarkerDragListener, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMapClickListener {

    private Button mBtnContinuar;
    private TextView mTxtInstruccionesMaker;
    private Toolbar toolbar;

    private MapFragment mMapFragment;
    private GoogleMap mMap;

    private Switch mSwitchChangeStatus;
    private String[] coord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name_main));
        getMap();
        coord = new String[2];

        mSwitchChangeStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                coord[0] = null;
                coord[1] = null;
                if(isChecked){
                    mSwitchChangeStatus.setText(getResources().getString(R.string.text_rb_main_bandera_true));
                }else{
                    mSwitchChangeStatus.setText(getResources().getString(R.string.text_rb_main_bandera_false));
                    mMap.clear();
                }
            }
        });

        mBtnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coord[0] != null && coord[1] != null) {
                    startActivity(new Intent(MainActivity.this, EncuestaActivity.class));
                }else{
                    Toast.makeText(MainActivity.this,"No podemos encontrar tu posición, por favor usa el marcador o presiona el boton para encontrar tu posición.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mBtnContinuar = (Button) findViewById(R.id.btnContinuar);
        mTxtInstruccionesMaker = (TextView) findViewById(R.id.textInstrucciones);
        mSwitchChangeStatus = (Switch) findViewById(R.id.switchMap);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Constants.QUERETARO, 16));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
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
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapClickListener(MainActivity.this);
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        coord[0] = String.valueOf(marker.getPosition().latitude);
        coord[1] = String.valueOf(marker.getPosition().longitude);
        Log.d("AA", "Coordenadas al colocar marcador " + marker.getPosition());
    }

    public void getMap() {
        mMapFragment = mMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mapInicioEncuesta, mMapFragment)
                .commit();

        mMapFragment.getMapAsync(this);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        coord[0] = String.valueOf(mMap.getCameraPosition().target.latitude);
        coord[1] = String.valueOf(mMap.getCameraPosition().target.longitude);
        Log.d("AA", "Coordenadas al presionar boton localización " + mMap.getCameraPosition());
        mMap.clear();
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(mSwitchChangeStatus.isChecked()){
            mMap.clear();
            mMap.addMarker(new MarkerOptions()
                    .position(mMap.getCameraPosition().target)
                    .draggable(true));
            coord[0] = String.valueOf(mMap.getCameraPosition().target.latitude);
            coord[1] = String.valueOf(mMap.getCameraPosition().target.longitude);
            Log.d("AA","Coordenadas al colocar marcador "+ mMap.getCameraPosition().target);
        }else{
            mMap.clear();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_LOGIN, MODE_PRIVATE);
        String email = preferences.getString(Constants.SHARED_PREFERENCES_LOGIN_EMAIL_FLAG, Constants.SHARED_PREFERENCES_LOGIN_EMAIL_FLAG);
        menu.getItem(0).setTitle(email);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_LOGIN, MODE_PRIVATE);
        Intent intent;
        if (item.getItemId() == R.id.action_user_information) {
            String id = preferences.getString(Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG, Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG);
            intent = new Intent(this,InformacionUsuarioActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.action_user_encuestas){
            intent = new Intent(this,HistorialActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_close) {
            LoginManager.getInstance().logOut();
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
            intent = new Intent(this, InicioActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Constants.LOCATION_REQUEST_CODE) {
            if (permissions.length > 0 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(this, "Error de permisos", Toast.LENGTH_LONG).show();
            }
        }
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
