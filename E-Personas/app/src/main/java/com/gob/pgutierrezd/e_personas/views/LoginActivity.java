package com.gob.pgutierrezd.e_personas.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.LoginPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.LoginView;
import com.gob.pgutierrezd.e_personas.presenters.LoginPresenterImpl;
import com.gob.pgutierrezd.e_personas.utils.CloseKeyboard;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private EditText mTxtUsername;
    private EditText mTxtPassword;
    private Button mBtnLogin;
    private ProgressBar mProgressBar;
    private LoginPresenter presenter;
    private CloseKeyboard closeKeyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        presenter = new LoginPresenterImpl(this);
        closeKeyboard = new CloseKeyboard(this);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard.closeKeyboard();
                presenter.validateCredentials(mTxtUsername.getText().toString(), mTxtPassword.getText().toString());
            }
        });
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
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

    private void findViews(){
        mTxtUsername = (EditText) findViewById(R.id.edit_username);
        mTxtPassword = (EditText) findViewById(R.id.edit_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mProgressBar = (ProgressBar) findViewById(R.id.login_progressbar);
    }
}
