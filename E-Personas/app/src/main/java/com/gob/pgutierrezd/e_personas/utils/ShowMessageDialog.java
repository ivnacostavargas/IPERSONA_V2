package com.gob.pgutierrezd.e_personas.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.gob.pgutierrezd.e_personas.R;
import com.gob.pgutierrezd.e_personas.views.LoginActivity;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public class ShowMessageDialog {

    private static Context sContext;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;

    private View errorView, loadView;

    public ShowMessageDialog(Context context){
        this.sContext = context;
        this.alertDialog = null;
        this.inflater = null;
        this.errorView = null;
        this.loadView = null;
    }

    public void showMessageInfo(String title, String message){
        this.inflater= LayoutInflater.from(sContext);
        this.errorView=inflater.inflate(R.layout.message_default_error, null);

        this.alertDialog = new AlertDialog.Builder(sContext).setTitle(title)
                .setView(errorView)
                .setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public void showMessageLoad(){
        this.inflater= LayoutInflater.from(sContext);
        this.loadView=inflater.inflate(R.layout.message_load, null);
        this.alertDialog = new AlertDialog.Builder(sContext)
                .setView(loadView)
                .setCancelable(false)
                .show();
        this.alertDialog.setCanceledOnTouchOutside(false);
    }

    public void closeMessage(){
        if(alertDialog != null) {
            //alertDialog.hide();
            //alertDialog = null;
            alertDialog.dismiss();
            loadView = null;
            errorView = null;
        }
    }
}
