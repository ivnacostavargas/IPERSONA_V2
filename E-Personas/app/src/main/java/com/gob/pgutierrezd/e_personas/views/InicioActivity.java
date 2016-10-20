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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.utils.Connectivity;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class InicioActivity extends AppCompatActivity{

    private Button btnGoLogin;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String email,name,first_name,last_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_inicio);
        findViews();
        callbackManager = CallbackManager.Factory.create();

        final Connectivity connectivity = new Connectivity(getApplicationContext());
        final ShowMessageDialog showMessageDialog = new ShowMessageDialog(getApplicationContext());

        btnGoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InicioActivity.this,LoginActivity.class));
            }
        });

        //loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d("AA","Login onSuccess.");
                if(connectivity.conectadoRedMovil() || connectivity.conectadoWifi()) {
                    loginWithFacebookSuccess(loginResult);
                }else{
                    showMessageDialog.showMessageInfo("Erro de conexion","No cuentas con conexion a internet.");
                }
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("AA","Login attempt canceled.");
                showMessageDialog.showMessageInfo("Erro de conexion", "No cuentas con conexion a internet.");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d("AA", "Login attempt failed.");
                showMessageDialog.showMessageInfo("Erro de conexion", "No cuentas con conexion a internet.");
            }
        });
    }

    private void findViews() {
        btnGoLogin = (Button) findViewById(R.id.btn_go_login);
        loginButton = (LoginButton) findViewById(R.id.btn_login_facebook);
    }

    private void loginWithFacebookSuccess(final LoginResult loginResult){
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", "" + response.getJSONObject().toString());

                try {
                    email = object.getString("email");
                    name = object.getString("name");
                    first_name = object.optString("first_name");
                    last_name = object.optString("last_name");
                    Log.d("AA","Email " + email
                                    +", Nombre " + name
                                    +", Primer nombre " + first_name
                                    +", Apellido " + last_name
                    );
                    //LoginManager.getInstance().logOut();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
