package com.gob.pgutierrezd.e_personas.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroView;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;
import com.gob.pgutierrezd.e_personas.presenters.RegistroPresenterImpl;
import com.gob.pgutierrezd.e_personas.utils.CloseKeyboard;
import com.gob.pgutierrezd.e_personas.utils.Connectivity;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;
import com.gob.pgutierrezd.e_personas.utils.ValidateFields;

public class RegistroUsuarioActivity extends AppCompatActivity implements RegistroView {

    private Toolbar toolbar;
    private EditText mNombre, mApellidos, mTelefono, mFechaNacimiento, mCorreo, mPassword, mPasswordConfirm;
    private RadioButton mGeneroM,mGeneroF;
    private Button mBtnCrearPerfil;
    private ImageButton mGetCalendar;
    private ImageView mImageView;
    private RegistroPresenter mRegistroPresenter;
    private CloseKeyboard mCloseKeyboard;
    private Connectivity mConnectivity;
    private ShowMessageDialog mShowMessageDialog;
    private ValidateFields mValidateFields;
    private LoginRegister mLoginRegister;

    private boolean mBandera;
    private int myear, mmonth, mday;

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
        mValidateFields = new ValidateFields();
        mBandera = false;

        addListenerOnButton();
        setCurrentDateOnView();

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

    private void addListenerOnButton() {
        mGetCalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(Constants.DATE_DIALOG_ID);
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

    public void setCurrentDateOnView() {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        myear = c.get(java.util.Calendar.YEAR);
        mmonth = c.get(java.util.Calendar.MONTH);
        mday = c.get(java.util.Calendar.DAY_OF_MONTH);
        mFechaNacimiento.setText(new StringBuilder().append(myear).append("-").append(mmonth + 1).append("-").append(mday));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constants.DATE_DIALOG_ID:
                // set date picker as current date
                DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, myear,mmonth,mday){
                    @Override
                    public void onDateChanged(DatePicker view, int yearSelected, int monthOfYearSelected, int dayOfMonthSelected){
                        //calendarHelper.validateDate(view,yearSelected,monthOfYearSelected,dayOfMonthSelected, myear,mmonth,mday);
                    }
                };
                return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;

            mFechaNacimiento.setText(new StringBuilder().append(myear).append("-").append(mmonth + 1)
                    .append("-").append(mday));
        }
    };

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
        mGetCalendar = (ImageButton) findViewById(R.id.btnShowCalendar);
    }
}
