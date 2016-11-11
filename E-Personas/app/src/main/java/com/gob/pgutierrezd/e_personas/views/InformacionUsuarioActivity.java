package com.gob.pgutierrezd.e_personas.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarView;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;
import com.gob.pgutierrezd.e_personas.presenters.ActualizarPresenterImpl;
import com.gob.pgutierrezd.e_personas.utils.CloseKeyboard;
import com.gob.pgutierrezd.e_personas.utils.Connectivity;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;

public class InformacionUsuarioActivity extends AppCompatActivity implements ActualizarView {

    private Toolbar mToolbar;
    private EditText mNombre, mApellidos, mTelefono, mFechaNacimiento, mCorreo, mPassword, mPasswordConfirm;
    private Button mBtnActualizarPerfil;
    private ImageButton mGetCalendar;
    private CloseKeyboard mCloseKeyboard;
    private Connectivity mConnectivity;
    private ShowMessageDialog mShowMessageDialog;
    private ActualizarPresenter mActualizarPresenter;
    private EditText[] mData;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_usuario);
        findViews();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name_actualizar));

        mCloseKeyboard = new CloseKeyboard(this);
        mConnectivity = new Connectivity(this);
        mShowMessageDialog = new ShowMessageDialog(this);
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_LOGIN, MODE_PRIVATE);
        String id = preferences.getString(Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG, Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG);

        addListenerOnButton();
        setCurrentDateOnView();

        final EditText[] data = getData();
        mActualizarPresenter = new ActualizarPresenterImpl(this, this);
        mActualizarPresenter.getData(data, Integer.parseInt(id));

        mBtnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mConnectivity.conectadoWifi() || mConnectivity.conectadoRedMovil()){
                LoginRegister datosActualizar = new LoginRegister();
                datosActualizar.setmCorreo(mCorreo.getText().toString());
                datosActualizar.setmTelefono(mTelefono.getText().toString());
                datosActualizar.setmFechaNacimiento(mFechaNacimiento.getText().toString());
                mActualizarPresenter.updateData(datosActualizar);
            }else{
                mShowMessageDialog.showMessageInfo(getResources().getString(R.string.text_title_error),getResources().getString(R.string.text_error_wifi));
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
    public void showData() {

    }

    @Override
    public void update() {

    }

    @Override
    public void errorConnectionServer() {
        mShowMessageDialog.showMessageInfo(getResources().getString(R.string.text_title_error), getResources().getString(R.string.text_error_actualizar_usuario));
    }

    public void setCurrentDateOnView() {
        final java.util.Calendar c = java.util.Calendar.getInstance();
        mYear = c.get(java.util.Calendar.YEAR);
        mMonth = c.get(java.util.Calendar.MONTH);
        mDay = c.get(java.util.Calendar.DAY_OF_MONTH);
        mFechaNacimiento.setText(new StringBuilder().append(mYear).append(getResources().getString(R.string.text_guion)).append(mMonth + 1).append(getResources().getString(R.string.text_guion)).append(mDay));
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case Constants.DATE_DIALOG_ID:
                DatePickerDialog _date = new DatePickerDialog(this, datePickerListener, mYear, mMonth, mDay){
                    @Override
                    public void onDateChanged(DatePicker view, int yearSelected, int monthOfYearSelected, int dayOfMonthSelected){
                    }
                };
                return _date;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;

            mFechaNacimiento.setText(new StringBuilder().append(mYear).append("-").append(mMonth + 1)
                    .append("-").append(mDay));
        }
    };

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mNombre = (EditText) findViewById(R.id.edittext_nombre);
        mApellidos = (EditText) findViewById(R.id.edittext_apellidos);
        mTelefono = (EditText) findViewById(R.id.edittext_celular);
        mFechaNacimiento = (EditText) findViewById(R.id.edit_fecha_nacimiento);
        mCorreo = (EditText) findViewById(R.id.edittext_email);
        mPassword = (EditText) findViewById(R.id.edittext_password);
        mPasswordConfirm = (EditText) findViewById(R.id.edittext_password_confirm);
        mBtnActualizarPerfil = (Button) findViewById(R.id.btn_actualizar_perfil);
        mGetCalendar = (ImageButton) findViewById(R.id.btnShowCalendar);
    }

    public EditText[] getData() {

        EditText[] dataFields = new EditText[]{
            this.mNombre,
            this.mApellidos,
            this.mCorreo,
            this.mTelefono,
            this.mFechaNacimiento
        };

        return dataFields;
    }
}
