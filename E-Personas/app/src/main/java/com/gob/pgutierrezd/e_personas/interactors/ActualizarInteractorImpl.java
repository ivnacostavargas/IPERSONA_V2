package com.gob.pgutierrezd.e_personas.interactors;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.httpconn.HttpManager;
import com.gob.pgutierrezd.e_personas.httpconn.RequestPackage;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarInteractor;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pgutierrezd on 30/10/2016.
 */
public class ActualizarInteractorImpl implements ActualizarInteractor {


    @Override
    public void getUser(EditText[] data, int id, OnUpdateFinishedListener listener, Context context) {
        requestDataLogin(context.getResources().getString(R.string.url),data,id, listener,context);
    }

    @Override
    public void updateUser(LoginRegister loginRegister, OnUpdateFinishedListener listener, Context context) {

    }

    private void requestDataLogin(String url, EditText[] data, int id, OnUpdateFinishedListener listener, Context context){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(url+"getUsuarios.php");
        requestPackage.setMethod("POST");
        requestPackage.setParams("json", "{\"idUsuario\":"+id+"}");
        LoginTask loginTask = new LoginTask(listener, context,data);
        loginTask.execute(requestPackage);
    }

    private class LoginTask extends AsyncTask<RequestPackage, String, String> {

        private OnUpdateFinishedListener listener;
        private Context context;
        EditText[] data;

        public LoginTask(OnUpdateFinishedListener listener, Context context, EditText[] data){
            this.listener = listener;
            this.context = context;
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(RequestPackage... params) {
            String content = HttpManager.getData(params[0]);
            while (content.equals("")){
                content = HttpManager.getData(params[0]);
            }
            return content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if(!s.equals("")) {
                    JSONObject parent = new JSONObject(s);
                    if (parent.length() > 1) {
                        JSONObject child1 = new JSONObject(parent.getString("dto"));
                        data[0].setText(child1.getString("nombre"));
                        data[1].setText(child1.getString("apellidos"));
                        data[2].setText(child1.getString("telefono"));
                        data[3].setText(child1.getString("correo"));
                        data[4].setText(child1.getString("fechaNacimiento"));
                        listener.getDataFinish();
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
