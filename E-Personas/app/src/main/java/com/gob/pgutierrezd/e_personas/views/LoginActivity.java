package com.gob.pgutierrezd.e_personas.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.login.LoginPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.login.LoginView;
import com.gob.pgutierrezd.e_personas.presenters.LoginPresenterImpl;
import com.gob.pgutierrezd.e_personas.utils.CloseKeyboard;
import com.gob.pgutierrezd.e_personas.utils.Connectivity;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText mTxtUsername;
    private EditText mTxtPassword;
    private Button mBtnLogin;
    private Button mBtnSingUp;
    private LoginPresenter presenter;
    private CloseKeyboard closeKeyboard;
    private Connectivity connectivity;
    private ShowMessageDialog showMessageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        presenter = new LoginPresenterImpl(this);
        closeKeyboard = new CloseKeyboard(this);
        connectivity = new Connectivity(this);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mBtnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singUp();
            }
        });

        mTxtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showProgress() {
        showMessageDialog.showMessageLoad();
    }

    @Override
    public void hideProgress() {
        showMessageDialog.closeMessage();
    }

    @Override
    public void setUsernameError() {
        mTxtUsername.setError("Error de usuario");
    }

    @Override
    public void setPasswordError() {
        mTxtPassword.setError("Error de contraseña");
    }

    @Override
    public void setUsernameErrorEmpty() {
        mTxtUsername.setError("Error, campo vacio!");
    }

    @Override
    public void setPasswordErrorEmpty() {
        mTxtPassword.setError("Error, campo vacio!");
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    public void login(){
        showMessageDialog = new ShowMessageDialog(LoginActivity.this);
        closeKeyboard.closeKeyboard();
        if(connectivity.conectadoRedMovil() || connectivity.conectadoWifi()) {
            presenter.validateCredentials(mTxtUsername.getText().toString(), mTxtPassword.getText().toString());
        }else{
            showMessageDialog.showMessageInfo("Error","No cuentas con conexion a internet");
        }
    }

    public void singUp(){
        Intent intent = new Intent(getApplicationContext(),RegistroUsuarioActivity.class);
        startActivity(intent);
    }

    private void findViews(){
        mTxtUsername = (EditText) findViewById(R.id.edit_username);
        mTxtPassword = (EditText) findViewById(R.id.edit_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnSingUp = (Button)findViewById(R.id.btnSingUp);
    }
}
