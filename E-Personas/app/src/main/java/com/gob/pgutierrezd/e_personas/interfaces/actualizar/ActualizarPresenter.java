package com.gob.pgutierrezd.e_personas.interfaces.actualizar;

import android.content.Context;
import android.widget.EditText;

import com.gob.pgutierrezd.e_personas.models.LoginRegister;

/**
 * Created by pgutierrezd on 30/10/2016.
 */
public interface ActualizarPresenter {

    void getData(EditText[] data, int id, Context context);

    void updateData(LoginRegister loginRegister, Context context);

    void onDestroy();

}
