package com.gob.pgutierrezd.e_personas.views;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EncuestaActivity extends AppCompatActivity implements EncuestaView {

    //Campos de dialogo informacion complementaria
    private EditText telefono,correo,txtFacebook,txtTwitter,edadAprox,otro;
    private RadioButton hombre, mujer;
    private Spinner lugar;
    private ImageView imgIdentificacion;

    private Switch mAlamedap6OP1;
    private Uri pathMM;
    private final int CAMARA_REQUEST = 3;

    public static final int MEDIA_TYPE_IMAGE = 1;

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
        View detallesView = inflater.inflate(R.layout.informacion_complementaria_encuesta_content, null);

        telefono = (EditText) detallesView.findViewById(R.id.telefono);
        correo = (EditText) detallesView.findViewById(R.id.correo);
        txtFacebook = (EditText) detallesView.findViewById(R.id.fecebook);
        txtTwitter = (EditText) detallesView.findViewById(R.id.twitter);
        hombre = (RadioButton) detallesView.findViewById(R.id.rb_hombre);
        mujer = (RadioButton) detallesView.findViewById(R.id.rb_mujer);
        edadAprox = (EditText) detallesView.findViewById(R.id.edadAprox);
        lugar = (Spinner) detallesView.findViewById(R.id.spnLugar);
        otro = (EditText) detallesView.findViewById(R.id.otro);
        imgIdentificacion = (ImageView) detallesView.findViewById(R.id.imgIdentificacion);
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
                        mAlamedap6OP1.setChecked(false);
                    }
                })
                .show();
    }

    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        pathMM = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        startActivityForResult(intent, CAMARA_REQUEST);
    }

    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"E-Personas");
        //Create the storage directory if it does not exist
        if( !mediaStorageDir.exists()){
            if( !mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        //Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == MEDIA_TYPE_IMAGE){
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
            case CAMARA_REQUEST:
                if(resultCode == Activity.RESULT_OK){
                    bm = (Bitmap) data.getExtras().get("data");
                    pathMM = data.getData();
                    imgIdentificacion.setImageBitmap(bm);
                    String myBase64Image = encodeToBase64(bm,Bitmap.CompressFormat.JPEG, 100);
                    try {
                        File tarjeta = Environment.getExternalStorageDirectory();
                        File file = new File(tarjeta.getAbsolutePath(), Constants.IMAGE_PROFILE);
                        OutputStreamWriter osw = new OutputStreamWriter(
                                new FileOutputStream(file));
                        osw.write(myBase64Image);
                        osw.flush();
                        osw.close();
                    }catch(Exception e){

                    }
                }
                break;
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

}
