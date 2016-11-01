package com.gob.pgutierrezd.e_personas.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.httpconn.HttpManager;
import com.gob.pgutierrezd.e_personas.httpconn.RequestPackage;
import com.gob.pgutierrezd.e_personas.interfaces.login.LoginInteractor;
import com.gob.pgutierrezd.e_personas.models.Login;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public class LoginInteractorImpl implements LoginInteractor {

    private Login mLoginModel;
    private String TAG = "LoginInteractor";

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener, Context context) {
        boolean error = true;

        if(TextUtils.isEmpty(username)){
            listener.onUsernameErrorEmpty();
            error = false;
        }
        if(TextUtils.isEmpty(password)) {
            listener.onPasswordErrorEmpty();
            error = false;
        }
        if(error){
            mLoginModel = new Login();
            mLoginModel.setEmail(username);
            mLoginModel.setPassword(password);
            requestDataLogin(context.getResources().getString(R.string.url), mLoginModel, listener, context,username);
        }
    }

    private void requestDataLogin(String url, Login user, OnLoginFinishedListener listener, Context context,String email){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(url+"login.php");
        requestPackage.setMethod("POST");
        requestPackage.setParams("json", "{\"correo\":\""+user.getEmail()+"\",\"password\":\""+user.getPassword()+"\"}");
        LoginTask loginTask = new LoginTask(listener, context, email);
        loginTask.execute(requestPackage);
    }

    private class LoginTask extends AsyncTask<RequestPackage, String, String> {

        private OnLoginFinishedListener listener;
        private ShowMessageDialog showMessageDialog;
        private Context context;
        private String email;

        public LoginTask(OnLoginFinishedListener listener, Context context, String email){
            this.listener = listener;
            this.context = context;
            this.showMessageDialog = new ShowMessageDialog(context);
            this.email = email;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            int aux = 0;
            String content = "";
            do{
                content = HttpManager.getData(params[0]);
                if(!content.equals("")){
                    aux = 2;
                }else{
                    aux++;
                }
            }while(aux != 2);
            return content;
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
                            editor.putString(Constants.SHARED_PREFERENCES_LOGIN_EMAIL_FLAG, email);
                            editor.commit();
                            Toast.makeText(context,child1.getString("mensaje"),Toast.LENGTH_LONG).show();
                            listener.onSuccess();
                        }else{
                            showMessageDialog.showMessageInfo("Error", child1.getString("mensaje"));
                            listener.onUsernameError();
                            listener.onPasswordError();
                        }
                    }
                }else{
                    showMessageDialog.showMessageInfo("Error", "No se ha podido establecer conexión");
                    listener.onErrorConnectionServer();
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