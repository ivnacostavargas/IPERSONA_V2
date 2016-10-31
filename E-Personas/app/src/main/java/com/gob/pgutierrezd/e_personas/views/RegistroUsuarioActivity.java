package com.gob.pgutierrezd.e_personas.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroView;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;
import com.gob.pgutierrezd.e_personas.presenters.RegistroPresenterImpl;
import com.gob.pgutierrezd.e_personas.utils.CloseKeyboard;
import com.gob.pgutierrezd.e_personas.utils.Connectivity;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;
import com.gob.pgutierrezd.e_personas.utils.ValidateFields;

public class RegistroUsuarioActivity extends AppCompatActivity implements RegistroView {

    private Toolbar toolbar;
    private EditText mNombre, mApellidos, mTelefono, mFechaNacimiento, mCorreo, mPassword, mPasswordConfirm;
    private RadioButton mGeneroM,mGeneroF;
    private Button mBtnCrearPerfil;
    private ImageView mImageView;
    private RegistroPresenter mRegistroPresenter;
    private CloseKeyboard mCloseKeyboard;
    private Connectivity mConnectivity;
    private ShowMessageDialog mShowMessageDialog;
    private ValidateFields mValidateFields;
    private LoginRegister mLoginRegister;

    private boolean mBandera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        findViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name_registro));

        mRegistroPresenter = new RegistroPresenterImpl(this,this);
        mCloseKeyboard = new CloseKeyboard(this);
        mConnectivity = new Connectivity(this);
        mShowMessageDialog = new ShowMessageDialog(this);
        mBandera = false;

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.equals(mPasswordConfirm.getText().toString()) && text.length() > 0) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.check));
                    mBandera = true;
                } else if (!text.equals(mPasswordConfirm.getText().toString()) && text.length() > 0) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.error));
                    mBandera = false;
                } else if (text.length() == 0) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.info));
                    mBandera = false;
                }
            }
        });

        mPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.equals(mPassword.getText().toString()) && text.length() > 0) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.check));
                    mBandera = true;
                } else if (!text.equals(mPassword.getText().toString()) && text.length() > 0) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.error));
                    mBandera = false;
                } else if (text.length() == 0) {
                    mImageView.setImageDrawable(getResources().getDrawable(R.drawable.info));
                    mBandera = false;
                }
            }
        });

        mBtnCrearPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConnectivity.conectadoRedMovil() || mConnectivity.conectadoWifi()) {
                    if (mBandera) {
                        mLoginRegister = new LoginRegister();
                        mLoginRegister.setmNombre(mNombre.getText().toString());
                        mLoginRegister.setmApellidos(mApellidos.getText().toString());
                        mLoginRegister.setmTelefono(mTelefono.getText().toString());
                        mLoginRegister.setmFechaNacimiento(mFechaNacimiento.getText().toString());
                        mLoginRegister.setmCorreo(mCorreo.getText().toString());
                        mLoginRegister.setmPassword(mPasswordConfirm.getText().toString());
                        if (mGeneroM.isChecked()) {
                            mLoginRegister.setmGenero("male");
                        } else if (mGeneroF.isChecked()) {
                            mLoginRegister.setmGenero("female");
                        }
                        mRegistroPresenter.validateRegister(mLoginRegister, getApplicationContext());
                    } else {
                        mShowMessageDialog.showMessageInfo("Error", "Las contraseñas no coinciden");
                    }
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
    public void setFieldErrorEmpty() {
        mValidateFields = new ValidateFields();
        mValidateFields.validateText(new EditText[]{mNombre, mApellidos, mTelefono, mFechaNacimiento, mCorreo, mPassword, mPasswordConfirm});
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(RegistroUsuarioActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void errorConnectionServer() {
        mShowMessageDialog.showMessageInfo("Error", "Error al registrar usuario, por favor vuelve a intentarlo.");
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
        mImageView = (ImageView) findViewById(R.id.icon_confirm_pass);
        mGeneroM = (RadioButton) findViewById(R.id.rb_genero_m);
        mGeneroF = (RadioButton) findViewById(R.id.rb_genero_f);
        mNombre.setNextFocusRightId(mApellidos.getId());
        mBtnCrearPerfil = (Button) findViewById(R.id.btn_crear_perfil);
    }
}
