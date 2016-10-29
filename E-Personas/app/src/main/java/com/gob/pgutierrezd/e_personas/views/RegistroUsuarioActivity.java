package com.gob.pgutierrezd.e_personas.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.gob.pgutierrezd.e_personas.R;

public class RegistroUsuarioActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText mNombre, mApellidos, mTelefono, mFechaNacimiento, mCorreo, mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        findViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name_registro));

    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNombre = (EditText) findViewById(R.id.edittext_nombre);
        mApellidos = (EditText) findViewById(R.id.edittext_apellidos);
        mTelefono = (EditText) findViewById(R.id.edittext_celular);
        mFechaNacimiento = (EditText) findViewById(R.id.edit_fecha_nacimiento);
        mCorreo = (EditText) findViewById(R.id.edittext_email);
        mPassword = (EditText) findViewById(R.id.edittext_password);
        mNombre.setNextFocusRightId(mApellidos.getId());
    }


}
