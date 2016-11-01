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

    private Toolbar toolbar;
    private EditText mNombre, mApellidos, mTelefono, mFechaNacimiento, mCorreo, mPassword, mPasswordConfirm;
    private Button mBtnActualizarPerfil;
    private ImageButton mGetCalendar;
    private CloseKeyboard mCloseKeyboard;
    private Connectivity mConnectivity;
    private ShowMessageDialog mShowMessageDialog;
    private ActualizarPresenter actualizarPresenter;
    private EditText[] data;
    private int myear, mmonth, mday;

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

        addListenerOnButton();
        setCurrentDateOnView();

        final EditText[] data = getData();
        actualizarPresenter = new ActualizarPresenterImpl(this, this);
        actualizarPresenter.getData(data,Integer.parseInt(id));

        mBtnActualizarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mConnectivity.conectadoWifi() || mConnectivity.conectadoRedMovil()){
                LoginRegister datosActualizar = new LoginRegister();
                datosActualizar.setmCorreo(mCorreo.getText().toString());
                datosActualizar.setmTelefono(mTelefono.getText().toString());
                datosActualizar.setmFechaNacimiento(mFechaNacimiento.getText().toString());
                actualizarPresenter.updateData(datosActualizar);
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
    public void showData() {

    }

    @Override
    public void update() {

    }

    @Override
    public void errorConnectionServer() {
        mShowMessageDialog.showMessageInfo("Error", "Error al actualizar usuario, por favor vuelve a intentarlo.");
    }

    // display current date
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
