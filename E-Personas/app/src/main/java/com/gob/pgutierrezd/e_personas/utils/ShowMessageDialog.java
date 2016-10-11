package com.gob.pgutierrezd.e_personas.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.gob.pgutierrezd.e_personas.R;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public class ShowMessageDialog {

    private static Context sContext;

    public ShowMessageDialog(Context context){
        this.sContext = context;
    }

    public void showMessageInfo(String title, String message){
        LayoutInflater inflater= LayoutInflater.from(sContext);
        View errorView=inflater.inflate(R.layout.default_error_message, null);

        new AlertDialog.Builder(sContext).setTitle(title)
                .setView(errorView)
                .setMessage(message)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }



}
