package com.gob.pgutierrezd.e_personas.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.httpconn.HttpManager;
import com.gob.pgutierrezd.e_personas.httpconn.RequestPackage;
import com.gob.pgutierrezd.e_personas.interfaces.inicio.InicioInteractor;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;
import com.gob.pgutierrezd.e_personas.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pgutierrezd on 26/10/2016.
 */
public class InicioInteractorImpl implements InicioInteractor{

    private LoginRegister mLoginRegister;

    @Override
    public void login(LoginResult loginResult, final Context context, final OnLoginFinishedListener listener) {
        if(loginResult != null){
            final Profile profile = Profile.getCurrentProfile();
            // Facebook Email address
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object,GraphResponse response) {
                    response.getJSONObject().toString();
                    try {
                        mLoginRegister = new LoginRegister();
                        mLoginRegister.setmNombre(profile.getFirstName());
                        mLoginRegister.setmApellidos(profile.getLastName());
                        mLoginRegister.setmTelefono("");
                        mLoginRegister.setmFechaNacimiento("");
                        mLoginRegister.setmCorreo(object.getString("email"));
                        mLoginRegister.setmPassword("");
                        mLoginRegister.setmGenero(object.getString("gender"));
                        requestDataLogin(context.getResources().getString(R.string.url), mLoginRegister, listener, context);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }

    private void requestDataLogin(String url, LoginRegister user, OnLoginFinishedListener listener, Context context){
        RequestPackage requestPackage = new RequestPackage();
        String peticion = "{\"nombre\":\""+user.getmNombre()+"\","
                        +"\"apellidos\":\""+user.getmApellidos()+"\","
                        +"\"telefono\":\""+user.getmTelefono()+"\","
                        +"\"fechaNacimiento\":\""+user.getmFechaNacimiento()+"\","
                        +"\"correo\":\""+user.getmCorreo()+"\","
                        +"\"password\":\""+user.getmPassword()+"\","
                        +"\"sexo\":\""+user.getmGenero()+"\","
                        +"\"facebook\":\"true\"}";
        requestPackage.setUri(url);
        requestPackage.setMethod("POST");
        requestPackage.setParams("json", peticion);
        LoginTask loginTask = new LoginTask(listener, context);
        loginTask.execute(requestPackage);
    }

    private class LoginTask extends AsyncTask<RequestPackage, String, String> {

        private OnLoginFinishedListener listener;
        private Context context;

        public LoginTask(OnLoginFinishedListener listener, Context context){
            this.listener = listener;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            if(!content.equals("")){
                return content;
            }else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if(!s.equals("")) {
                    JSONObject parent = new JSONObject(s);
                    if (parent.length() > 1) {
                        JSONObject child1 = new JSONObject(parent.getString("error"));
                        if(child1.getString("clave").equals("OK")){
                            SharedPreferences preferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_LOGIN, context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(Constants.SHARED_PREFERENCES_LOGIN_ID_FLAG, parent.getString("idUsuario"));
                            editor.commit();
                            Toast.makeText(context, child1.getString("mensaje"), Toast.LENGTH_LONG).show();
                            listener.onSuccess();
                        }else{
                            listener.onFail();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }
    }

}
