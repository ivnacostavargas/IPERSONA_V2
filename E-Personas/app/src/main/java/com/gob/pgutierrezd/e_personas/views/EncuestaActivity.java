package com.gob.pgutierrezd.e_personas.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.encuesta.EncuestaView;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ConvertBase64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EncuestaActivity extends AppCompatActivity implements EncuestaView {

    //Campos de encuesta

    //Campos de dialogo informacion complementaria
    private EditText mTextTelefono, mTextCorreo, mTxtFacebook, mTxtTwitter, mTextEdadAprox, mTextOtro;
    private RadioButton mRbHombre, mRbMujer;
    private Spinner mSpnLugar;
    private ImageView mImgIdentificacion;

    private Switch mSwcEncuestaDialog;
    private Uri mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        findViews();

        mSwcEncuestaDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwcEncuestaDialog.isChecked()) {
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
        mSwcEncuestaDialog = (Switch)findViewById(R.id.Alamedap6OP1);
    }

    public void informacionComplementaria(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View detallesView = inflater.inflate(R.layout.informacion_complementaria_encuesta_content, null);

        mTextTelefono = (EditText) detallesView.findViewById(R.id.telefono);
        mTextCorreo = (EditText) detallesView.findViewById(R.id.correo);
        mTxtFacebook = (EditText) detallesView.findViewById(R.id.fecebook);
        mTxtTwitter = (EditText) detallesView.findViewById(R.id.twitter);
        mRbHombre = (RadioButton) detallesView.findViewById(R.id.rb_hombre);
        mRbMujer = (RadioButton) detallesView.findViewById(R.id.rb_mujer);
        mTextEdadAprox = (EditText) detallesView.findViewById(R.id.edadAprox);
        mSpnLugar = (Spinner) detallesView.findViewById(R.id.spnLugar);
        mTextOtro = (EditText) detallesView.findViewById(R.id.otro);
        mImgIdentificacion = (ImageView) detallesView.findViewById(R.id.imgIdentificacion);
        ImageButton btnAgregarFoto = (ImageButton) detallesView.findViewById(R.id.btnAgregarFoto);
        ImageButton btnEliminarFoto = (ImageButton) detallesView.findViewById(R.id.btnBorrarFoto);

        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        btnEliminarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.text_dialog_title))
                .setView(detallesView)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSwcEncuestaDialog.setChecked(false);
                    }
                })
                .show();
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPath = getOutputMediaFileUri(Constants.MEDIA_TYPE_IMAGE);
        startActivityForResult(intent, Constants.CAMARA_REQUEST);
    }

    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"E-Personas");
        if( !mediaStorageDir.exists()){
            if( !mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == Constants.MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        }else{
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bm;
        switch(requestCode){
            case Constants.CAMARA_REQUEST:
                if(resultCode == Activity.RESULT_OK){
                    bm = (Bitmap) data.getExtras().get("data");
                    mPath = data.getData();
                    mImgIdentificacion.setImageBitmap(bm);
                    String myBase64Image = ConvertBase64.encodeToBase64(bm, Bitmap.CompressFormat.JPEG, 100);
                    try {
                        File tarjeta = Environment.getExternalStorageDirectory();
                        File file = new File(tarjeta.getAbsolutePath(), Constants.IMAGE_PROFILE);
                        OutputStreamWriter osw = new OutputStreamWriter(
                                new FileOutputStream(file));
                        osw.write(myBase64Image);
                        osw.flush();
                        osw.close();
                    }catch(Exception e){}
                }
                break;
        }
    }

}
