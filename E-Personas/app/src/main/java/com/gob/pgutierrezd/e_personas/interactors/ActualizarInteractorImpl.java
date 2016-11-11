package com.gob.pgutierrezd.e_personas.interactors;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.httpconn.HttpManager;
import com.gob.pgutierrezd.e_personas.httpconn.RequestPackage;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarInteractor;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;
import com.gob.pgutierrezd.e_personas.utils.Constants;
import com.gob.pgutierrezd.e_personas.utils.ShowMessageDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pgutierrezd on 30/10/2016.
 */
public class ActualizarInteractorImpl implements ActualizarInteractor {


    @Override
    public void getUser(EditText[] data, int id, OnUpdateFinishedListener listener, Context context) {
        requestGetDataUser(context.getResources().getString(R.string.url), data, id, listener, context);
    }

    @Override
    public void updateUser(LoginRegister loginRegister, OnUpdateFinishedListener listener, Context context) {
        requestDataUpdate(context.getResources().getString(R.string.url),loginRegister,listener,context);
    }

    private void requestGetDataUser(String url, EditText[] data, int id, OnUpdateFinishedListener listener, Context context){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(url + Constants.CLASS_URL_GETUSERS);
        requestPackage.setMethod(Constants.METHOD_POST);
        requestPackage.setParams("json", "{\"idUsuario\":"+id+"}");
        GetDataUserTask getDataUserTask = new GetDataUserTask(listener, context,data);
        getDataUserTask.execute(requestPackage);
    }

    private void requestDataUpdate(String url, LoginRegister loginRegister, OnUpdateFinishedListener listener, Context context){
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setUri(url+Constants.CLASS_URL_UPDATE);
        requestPackage.setMethod(Constants.METHOD_POST);
        requestPackage.setParams("json", "{\"correo\":\""+loginRegister.getmCorreo()+"\"," +
                "\"telefono\":\""+loginRegister.getmTelefono()+"\"," +
                "\"fechaNacimiento\":\""+loginRegister.getmFechaNacimiento()+"\"}");
        UpdateDataTask updateDataTask = new UpdateDataTask(listener, context);
        updateDataTask.execute(requestPackage);
    }

    private class GetDataUserTask extends AsyncTask<RequestPackage, String, String> {

        private OnUpdateFinishedListener listener;
        private Context context;
        EditText[] data;

        public GetDataUserTask(OnUpdateFinishedListener listener, Context context, EditText[] data){
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
                        data[2].setText(child1.getString("correo"));
                        data[3].setText(child1.getString("telefono"));
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

    private class UpdateDataTask extends AsyncTask<RequestPackage, String, String> {

        private OnUpdateFinishedListener listener;
        private Context context;
        private ShowMessageDialog showMessageDialog;

        public UpdateDataTask(OnUpdateFinishedListener listener, Context context){
            this.listener = listener;
            this.context = context;
            this.showMessageDialog = new ShowMessageDialog(context);
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
                    if (parent.length() > 0) {
                        JSONObject child1 = new JSONObject(parent.getString(Constants.JSON_RESULT_ERROR));
                        if(child1.getString(Constants.JSON_KEY_CLAVE).equals(Constants.JSON_RESULT_CLAVE)) {
                            showMessageDialog.showMessageInfo(context.getResources().getString(R.string.text_title_success),context.getResources().getString(R.string.text_actualizar_usuario));
                            listener.getDataFinish();
                        }else{
                            showMessageDialog.showMessageInfo(context.getResources().getString(R.string.text_title_error), context.getResources().getString(R.string.text_actualizar_usuario_error));
                            listener.errorUpdate();
                        }
                    }
                }else{
                    showMessageDialog.showMessageInfo(context.getResources().getString(R.string.text_title_error),context.getResources().getString(R.string.text_actualizar_usuario_error_server));
                    listener.errorConnectionServer();
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
