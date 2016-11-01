package com.gob.pgutierrezd.e_personas.interactors;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.httpconn.HttpManager;
import com.gob.pgutierrezd.e_personas.httpconn.RequestPackage;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroInteractor;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;
import com.gob.pgutierrezd.e_personas.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pgutierrezd on 29/10/2016.
 */
public class RegisterInteractorImpl implements RegistroInteractor {

    @Override
    public void registerUserLogin(LoginRegister loginRegister, OnRegisterFinishedListener listener, Context context) {
        if(loginRegister.getmNombre().isEmpty() || loginRegister.getmApellidos().isEmpty() || loginRegister.getmTelefono().isEmpty()
                || loginRegister.getmFechaNacimiento().isEmpty() || loginRegister.getmCorreo().isEmpty()
                || loginRegister.getmPassword().isEmpty() || loginRegister.getmGenero().isEmpty()){
            listener.setFieldErrorEmpty();
        }else{
            requestDataRegister(context.getResources().getString(R.string.url), loginRegister, listener, context, loginRegister.getmCorreo());
        }
    }

    private void requestDataRegister(String url, LoginRegister user, OnRegisterFinishedListener listener, Context context, String email){
        RequestPackage requestPackage = new RequestPackage();
        String peticion = "{\"nombre\":\""+user.getmNombre()+"\","
                +"\"apellidos\":\""+user.getmApellidos()+"\","
                +"\"telefono\":\""+user.getmTelefono()+"\","
                +"\"fechaNacimiento\":\""+user.getmFechaNacimiento()+"\","
                +"\"correo\":\""+user.getmCorreo()+"\","
                +"\"password\":\""+user.getmPassword()+"\","
                +"\"sexo\":\""+user.getmGenero()+"\","
                +"\"facebook\":\"false\"}";
        requestPackage.setUri(url + "login.php");
        requestPackage.setMethod("POST");
        requestPackage.setParams("json", peticion);
        RegisterUserTask registerUserTask = new RegisterUserTask(listener, context, email);
        registerUserTask.execute(requestPackage);
    }

    private class RegisterUserTask extends AsyncTask<RequestPackage, String, String> {

        private OnRegisterFinishedListener listener;
        private Context context;
        private String email;

        public RegisterUserTask(OnRegisterFinishedListener listener, Context context, String email){
            this.listener = listener;
            this.context = context;
            this.email = email;
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
                            editor.putString(Constants.SHARED_PREFERENCES_LOGIN_EMAIL_FLAG, email);
                            editor.commit();
                            Toast.makeText(context, child1.getString("mensaje"), Toast.LENGTH_LONG).show();
                            listener.navigateToHome();
                        }else{
                            listener.errorConnectionServer();
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
