package com.gob.pgutierrezd.e_personas.interfaces.actualizar;

import android.content.Context;
import android.widget.EditText;

import com.gob.pgutierrezd.e_personas.models.LoginRegister;

/**
 * Created by pgutierrezd on 30/10/2016.
 */
public interface ActualizarInteractor {

    interface OnUpdateFinishedListener {

        void getDataFinish();

        void update();

        void errorConnectionServer();
    }

    void getUser(EditText[] data, int id, OnUpdateFinishedListener listener, Context context);
    void updateUser(LoginRegister loginRegister, OnUpdateFinishedListener listener, Context context);
}
