package com.gob.pgutierrezd.e_personas.views;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.encuesta.EncuestaView;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.googlemaps.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class EncuestaActivity extends AppCompatActivity implements EncuestaView {

    private Switch mAlamedap6OP1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        findViews();

        mAlamedap6OP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAlamedap6OP1.isChecked()){
                    informacionComplementaria();
                }
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private void findViews(){
        mAlamedap6OP1 = (Switch)findViewById(R.id.Alamedap6OP1);
    }

    public void informacionComplementaria(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View detallesView = inflater.inflate(R.layout.informacion_complementaria_encuesta_content,null);

        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.text_dialog_title))
                .setView(detallesView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //LO QUE DEBE DE HACER
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAlamedap6OP1.setChecked(false);
                    }
                })
                .show();
    }
}
