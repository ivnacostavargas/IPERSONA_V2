package com.gob.pgutierrezd.e_personas.views;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarView;
import com.gob.pgutierrezd.e_personas.presenters.ActualizarPresenterImpl;
import com.gob.pgutierrezd.e_personas.utils.CloseKeyboard;
import com.gob.pgutierrezd.e_personas.utils.Connectivity;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;

public class InformacionUsuarioActivity extends AppCompatActivity implements ActualizarView {

    private Toolbar toolbar;
    private EditText mNombre, mApellidos, mTelefono, mFechaNacimiento, mCorreo, mPassword, mPasswordConfirm;
    private Button mBtnActualizarPerfil;
    private CloseKeyboard mCloseKeyboard;
    private Connectivity mConnectivity;
    private ShowMessageDialog mShowMessageDialog;
    private ActualizarPresenter actualizarPresenter;
    private EditText[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_usuario);
        findViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name_actualizar));

        mCloseKeyboard = new CloseKeyboard(this);
        mConnectivity = new Connectivity(this);
        mShowMessageDialog = new ShowMessageDialog(this);
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_LOGIN, MODE_PRIVATE);
        String id = preferences.getString(Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG, Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG);

        EditText[] data = getData();
        actualizarPresenter = new ActualizarPresenterImpl(this,this);
        actualizarPresenter.getData(data,Integer.parseInt(id),this);

        mBtnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConnectivity.conectadoWifi() || mConnectivity.conectadoRedMovil()){

                }else{
                    mShowMessageDialog.showMessageInfo("Error","No cuentas con señal wifi o datos móviles");
                }
            }
        });

    }

    @Override
    public void showProgress() {
        mShowMessageDialog.showMessageLoad();
    }

    @Override
    public void hideProgress() {
        mShowMessageDialog.closeMessage();
    }

    @Override
    public void showData() {

    }

    @Override
    public void update() {

    }

    @Override
    public void errorConnectionServer() {
        mShowMessageDialog.showMessageInfo("Error", "Error al actualizar usuario, por favor vuelve a intentarlo.");
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNombre = (EditText) findViewById(R.id.edittext_nombre);
        mApellidos = (EditText) findViewById(R.id.edittext_apellidos);
        mTelefono = (EditText) findViewById(R.id.edittext_celular);
        mFechaNacimiento = (EditText) findViewById(R.id.edit_fecha_nacimiento);
        mCorreo = (EditText) findViewById(R.id.edittext_email);
        mPassword = (EditText) findViewById(R.id.edittext_password);
        mPasswordConfirm = (EditText) findViewById(R.id.edittext_password_confirm);
        mBtnActualizarPerfil = (Button) findViewById(R.id.btn_actualizar_perfil);
    }

    public EditText[] getData() {

        EditText[] dataFields = new EditText[]{
            this.mNombre,
            this.mApellidos,
            this.mTelefono,
            this.mFechaNacimiento,
            this.mCorreo
        };

        return dataFields;
    }
}
