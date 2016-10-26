package com.gob.pgutierrezd.e_personas.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.interfaces.inicio.InicioPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.inicio.InicioView;
import com.gob.pgutierrezd.e_personas.presenters.InicioPresenterImpl;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;

public class InicioActivity extends AppCompatActivity implements InicioView{

    private Button mBtnGoLogin;
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private String email,name,first_name,last_name;

    private ShowMessageDialog mShowMessageDialog;
    private InicioPresenter mInicioPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_inicio);
        findViews();
        mInicioPresenter = new InicioPresenterImpl(this,this);
        mCallbackManager = CallbackManager.Factory.create();

        mBtnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInicioPresenter.goToLogin();
            }
        });

        mLoginButton.setReadPermissions(Constants.PERMISSIONS_FACEBOOK);
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mInicioPresenter.validateAccessFacebook(loginResult);
            }

            @Override
            public void onCancel() {
                mShowMessageDialog = new ShowMessageDialog(InicioActivity.this);
                mShowMessageDialog.showMessageInfo("Advertencia", "Cancelaste inicio de sesion.");
            }

            @Override
            public void onError(FacebookException exception) {
                mShowMessageDialog = new ShowMessageDialog(InicioActivity.this);
                mShowMessageDialog.showMessageInfo("Erro", "Error al iniciar sesion.");
            }
        });
        new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(final Profile oldProfile,final Profile currentProfile) {
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void loginFacebook() {
        Intent intent = new Intent(InicioActivity.this, EncuestaActivity.class);
        startActivity(intent);
    }

    @Override
    public void goLogin() {
        mShowMessageDialog = new ShowMessageDialog(InicioActivity.this);
        mShowMessageDialog.showMessageLoad();
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mShowMessageDialog.closeMessage();
                startActivity(new Intent(InicioActivity.this, LoginActivity.class));
            }
        }, 1000);
    }

    @Override
    public void onFailLogin() {
        mShowMessageDialog = new ShowMessageDialog(InicioActivity.this);
        mShowMessageDialog.showMessageInfo("Error", "No se pudo iniciar sesion con esta cuenta de facebook.");
    }

    private void findViews() {
        mBtnGoLogin = (Button) findViewById(R.id.btn_go_login);
        mLoginButton = (LoginButton) findViewById(R.id.btn_login_facebook);
    }
}
