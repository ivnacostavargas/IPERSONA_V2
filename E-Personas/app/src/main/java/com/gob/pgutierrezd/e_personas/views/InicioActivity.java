package com.gob.pgutierrezd.e_personas.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class InicioActivity extends AppCompatActivity implements InicioView{

    private Button mBtnGoLogin;
    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;
    private ShowMessageDialog mShowMessageDialog;
    private InicioPresenter mInicioPresenter;
    private ShowMessageDialog showMessageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_inicio);
        findViews();
        validateSession();
        mInicioPresenter = new InicioPresenterImpl(this,this);
        mCallbackManager = CallbackManager.Factory.create();
        showMessageDialog = new ShowMessageDialog(this);

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
                mShowMessageDialog.showMessageInfo(getResources().getString(R.string.text_title_error_advertencia), getResources().getString(R.string.text_inicio_activity_cancelar_sesion));
            }

            @Override
            public void onError(FacebookException exception) {
                mShowMessageDialog = new ShowMessageDialog(InicioActivity.this);
                mShowMessageDialog.showMessageInfo(getResources().getString(R.string.text_title_error), getResources().getString(R.string.text_inicio_activity_error_sesion));
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
    public void showProgress() {
        showMessageDialog.showMessageLoad();
    }

    @Override
    public void hideProgress() {
        showMessageDialog.closeMessage();
    }

    @Override
    public void loginFacebook() {
        Intent intent = new Intent(InicioActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
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
        mShowMessageDialog.showMessageInfo(getResources().getString(R.string.text_title_error), getResources().getString(R.string.text_inicio_activity_error_cuenta_no_valida));
    }

    private void findViews() {
        mBtnGoLogin = (Button) findViewById(R.id.btn_go_login);
        mLoginButton = (LoginButton) findViewById(R.id.btn_login_facebook);
    }

    private void validateSession(){
        SharedPreferences preferences = getSharedPreferences(Constants.SHARED_PREFERENCES_LOGIN, MODE_PRIVATE);
        String id = preferences.getString(Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG, Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG);
        if(id.length() > 0 && !id.equals(Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG)){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
