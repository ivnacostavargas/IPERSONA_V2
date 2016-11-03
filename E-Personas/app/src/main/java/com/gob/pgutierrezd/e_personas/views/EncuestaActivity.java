package com.gob.pgutierrezd.e_personas.views;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.encuesta.EncuestaView;
import com.gob.pgutierrezd.e_personas.utils.AlarmReceiver;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ConvertBase64;
import com.gob.pgutierrezd.e_personas.utils.TakePhoto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class EncuestaActivity extends AppCompatActivity implements EncuestaView , View.OnClickListener{

    //Campos de encuesta
    //Colonia segura
    private EditText mTPregunta5R2ColSegura,mTPregunta8R2ColSegura, mTPregunta9R1ColSegura;
    private RadioButton mRPregunta1R1ColSegura,mRPregunta1R2ColSegura,mRPregunta1R3ColSegura,mRPregunta1R4ColSegura;
    private RadioButton mRPregunta4R1ColSegura,mRPregunta4R2ColSegura,mRPregunta4R3ColSegura,mRPregunta4R4ColSegura;
    private RadioButton mRPregunta6R1ColSegura, mRPregunta6R2ColSegura,mRPregunta6R3ColSegura,mRPregunta6R4ColSegura;
    private CheckBox mCPregunta3R1ColSegura,mCPregunta3R2ColSegura,mCPregunta3R3ColSegura,mCPregunta3R4ColSegura;
    private CheckBox mCPregunta7R1ColSegura, mCPregunta7R2ColSegura,mCPregunta7R3ColSegura,mCPregunta7R4ColSegura,mCPregunta7R5ColSegura;
    private Switch mSPregunta2R1ColSegura,mSPregunta5R1ColSegura,mSPregunta8R1ColSegura;
    //Induccion de obra
    private EditText mTPregunta1R1IndObra, mTPregunta3R6IndObra;
    private RadioButton mRPregunta1R2IndObra, mRPregunta1R3IndObra, mRPregunta1R4IndObra, mRPregunta1R5IndObra;
    private CheckBox mCPregunta2R1IndObra, mCPregunta2R2IndObra, mCPregunta2R3IndObra, mCPregunta2R4IndObra;
    private CheckBox mCPregunta3R1IndObra, mCPregunta3R2IndObra, mCPregunta3R3IndObra, mCPregunta3R4IndObra,mCPregunta3R5IndObra;
    //Lotes baldios
    private Switch mSPregunta1R1LotesBaldios, mSPregunta2R1LotesBaldios, mSPregunta3R1LotesBaldios, mSPregunta4R1LotesBaldios;
    private RadioButton mRPregunta5R1LotesBaldios,mRPregunta5R2LotesBaldios, mRPregunta5R3LotesBaldios;
    //Sondeo Alameda
    private EditText mTPregunta5R1SondAlameda;
    private Switch mSPregunta1R1SondAlameda, mSPregunta2R1SondAlameda, mSPregunta3R1SondAlameda, mSPregunta6R1SondAlameda;
    private CheckBox mCPregunta4R1SondAlameda, mCPregunta4R2SondAlameda, mCPregunta4R3SondAlameda;
    private Switch mSwcEncuestaDialog;

    private Button mBtnEnviarEncuesta;

    //Campos de dialogo informacion complementaria
    private EditText mTextTelefono, mTextCorreo, mTxtFacebook, mTxtTwitter, mTextEdadAprox, mTextOtro;
    private RadioButton mRbHombre, mRbMujer;
    private Spinner mSpnLugar;
    private ImageView mImgIdentificacion;

    private Uri mPath;
    private SharedPreferences mPreferences;
    private PendingIntent mAlarmIntent;
    private ArrayAdapter<CharSequence> adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);
        context = getApplicationContext();
        findViews();
        //getCoordsPerMinute();
        mPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_COORDS, MODE_PRIVATE);
        mBtnEnviarEncuesta.setOnClickListener(this);
        mSwcEncuestaDialog.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopGetCoords();
    }

    @Override
    public void onClick(View v) {
        if(v == mBtnEnviarEncuesta){

        }else if(v == mSwcEncuestaDialog){
            informacionComplementaria();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public String getCoordsPerMinute() {
        getCoordsAlarm();
        return null;
    }

    public void informacionComplementaria(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View detallesView = inflater.inflate(R.layout.dialog_informacion_complementaria_encuesta, null);

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

        adapter = ArrayAdapter.createFromResource(this, R.array.lugar_entrevista_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnLugar.setAdapter(adapter);

        final TakePhoto takePhoto = new TakePhoto(this);

        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto.getPhoto();
            }
        });

        btnEliminarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImgIdentificacion.setImageDrawable(getResources().getDrawable(R.drawable.userphoto));
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

    private void getCoordsAlarm(){
        int comprobacionIntervaloSegundos = 30;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        mAlarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        service.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), comprobacionIntervaloSegundos * 1000, mAlarmIntent);
    }

    private void stopGetCoords(){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.commit();
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.cancel(mAlarmIntent);
    }

    private void backToMain(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void findViews(){
        mTPregunta5R2ColSegura = (EditText) findViewById(R.id.pregunta5_respuesta2_colonia_segura);
        mTPregunta8R2ColSegura  = (EditText) findViewById(R.id.pregunta8_respuesta2_colonia_segura);
        mTPregunta9R1ColSegura = (EditText) findViewById(R.id.pregunta9_respuesta_colonia_segura);
        mRPregunta1R1ColSegura = (RadioButton) findViewById(R.id.pregunta1_respuesta1_colonia_segura);
        mRPregunta1R2ColSegura = (RadioButton) findViewById(R.id.pregunta1_respuesta2_colonia_segura);
        mRPregunta1R3ColSegura = (RadioButton) findViewById(R.id.pregunta1_respuesta3_colonia_segura);
        mRPregunta1R4ColSegura = (RadioButton) findViewById(R.id.pregunta1_respuesta4_colonia_segura);
        mRPregunta4R1ColSegura = (RadioButton) findViewById(R.id.pregunta4_respuesta1_colonia_segura);
        mRPregunta4R2ColSegura = (RadioButton) findViewById(R.id.pregunta4_respuesta2_colonia_segura);
        mRPregunta4R3ColSegura = (RadioButton) findViewById(R.id.pregunta4_respuesta3_colonia_segura);
        mRPregunta4R4ColSegura = (RadioButton) findViewById(R.id.pregunta4_respuesta4_colonia_segura);
        mRPregunta6R1ColSegura = (RadioButton) findViewById(R.id.pregunta6_respuesta1_colonia_segura);
        mRPregunta6R2ColSegura = (RadioButton) findViewById(R.id.pregunta6_respuesta2_colonia_segura);
        mRPregunta6R3ColSegura = (RadioButton) findViewById(R.id.pregunta6_respuesta3_colonia_segura);
        mRPregunta6R4ColSegura = (RadioButton) findViewById(R.id.pregunta6_respuesta4_colonia_segura);
        mCPregunta3R1ColSegura = (CheckBox) findViewById(R.id.pregunta3_respuesta1_colonia_segura);
        mCPregunta3R2ColSegura = (CheckBox) findViewById(R.id.pregunta3_respuesta2_colonia_segura);
        mCPregunta3R3ColSegura = (CheckBox) findViewById(R.id.pregunta3_respuesta3_colonia_segura);
        mCPregunta3R4ColSegura = (CheckBox) findViewById(R.id.pregunta3_respuesta4_colonia_segura);
        mCPregunta7R1ColSegura = (CheckBox) findViewById(R.id.pregunta7_respuesta1_colonia_segura);
        mCPregunta7R2ColSegura = (CheckBox) findViewById(R.id.pregunta7_respuesta2_colonia_segura);
        mCPregunta7R3ColSegura = (CheckBox) findViewById(R.id.pregunta7_respuesta3_colonia_segura);
        mCPregunta7R4ColSegura = (CheckBox) findViewById(R.id.pregunta7_respuesta4_colonia_segura);
        mCPregunta7R5ColSegura = (CheckBox) findViewById(R.id.pregunta7_respuesta5_colonia_segura);
        mSPregunta2R1ColSegura = (Switch) findViewById(R.id.pregunta2_respuesta_colonia_segura);
        mSPregunta5R1ColSegura = (Switch) findViewById(R.id.pregunta5_respuesta1_colonia_segura);
        mSPregunta8R1ColSegura = (Switch) findViewById(R.id.pregunta8_respuesta1_colonia_segura);

        mTPregunta1R1IndObra = (EditText) findViewById(R.id.pregunta1_respuesta1_induccion_obra);
        mTPregunta3R6IndObra = (EditText) findViewById(R.id.pregunta3_respuesta6_induccion_obra);
        mRPregunta1R2IndObra = (RadioButton) findViewById(R.id.pregunta1_respuesta2_induccion_obra);
        mRPregunta1R3IndObra = (RadioButton) findViewById(R.id.pregunta1_respuesta3_induccion_obra);
        mRPregunta1R4IndObra = (RadioButton) findViewById(R.id.pregunta1_respuesta4_induccion_obra);
        mRPregunta1R5IndObra = (RadioButton) findViewById(R.id.pregunta1_respuesta5_induccion_obra);
        mCPregunta2R1IndObra = (CheckBox) findViewById(R.id.pregunta2_respuesta1_induccion_obra);
        mCPregunta2R2IndObra = (CheckBox) findViewById(R.id.pregunta2_respuesta2_induccion_obra);
        mCPregunta2R3IndObra = (CheckBox) findViewById(R.id.pregunta2_respuesta3_induccion_obra);
        mCPregunta2R4IndObra = (CheckBox) findViewById(R.id.pregunta2_respuesta4_induccion_obra);
        mCPregunta3R1IndObra = (CheckBox) findViewById(R.id.pregunta3_respuesta1_induccion_obra);
        mCPregunta3R2IndObra = (CheckBox) findViewById(R.id.pregunta3_respuesta2_induccion_obra);
        mCPregunta3R3IndObra = (CheckBox) findViewById(R.id.pregunta3_respuesta3_induccion_obra);
        mCPregunta3R4IndObra = (CheckBox) findViewById(R.id.pregunta3_respuesta4_induccion_obra);
        mCPregunta3R5IndObra = (CheckBox) findViewById(R.id.pregunta3_respuesta5_induccion_obra);

        mSPregunta1R1LotesBaldios = (Switch) findViewById(R.id.pregunta1_respuesta_lotes_baldios);
        mSPregunta2R1LotesBaldios = (Switch) findViewById(R.id.pregunta2_respuesta_lotes_baldios);
        mSPregunta3R1LotesBaldios = (Switch) findViewById(R.id.pregunta3_respuesta_lotes_baldios);
        mSPregunta4R1LotesBaldios = (Switch) findViewById(R.id.pregunta4_respuesta_lotes_baldios);
        mRPregunta5R1LotesBaldios = (RadioButton) findViewById(R.id.pregunta5_respuesta1_lotes_baldios);
        mRPregunta5R2LotesBaldios = (RadioButton) findViewById(R.id.pregunta5_respuesta2_lotes_baldios);
        mRPregunta5R3LotesBaldios = (RadioButton) findViewById(R.id.pregunta5_respuesta3_lotes_baldios);

        mTPregunta5R1SondAlameda = (EditText) findViewById(R.id.pregunta5_respuesta_sondeo_alameda);
        mSPregunta1R1SondAlameda = (Switch) findViewById(R.id.pregunta1_respuesta_sondeo_alameda);
        mSPregunta2R1SondAlameda = (Switch) findViewById(R.id.pregunta2_respuesta_sondeo_alameda);
        mSPregunta3R1SondAlameda = (Switch) findViewById(R.id.pregunta3_respuesta_sondeo_alameda);
        mSPregunta6R1SondAlameda = (Switch) findViewById(R.id.pregunta6_respuesta_sondeo_alameda);
        mCPregunta4R1SondAlameda = (CheckBox) findViewById(R.id.pregunta4_respuesta1_sondeo_alameda);
        mCPregunta4R2SondAlameda = (CheckBox) findViewById(R.id.pregunta4_respuesta2_sondeo_alameda);
        mCPregunta4R3SondAlameda = (CheckBox) findViewById(R.id.pregunta4_respuesta3_sondeo_alameda);
        mSwcEncuestaDialog = (Switch)findViewById(R.id.pregunta6_respuesta_sondeo_alameda);

        mBtnEnviarEncuesta = (Button) findViewById(R.id.enviarEncuesta);
    }
}
