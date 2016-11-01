package com.gob.pgutierrezd.e_personas.interfaces.registro;

import android.content.Context;

import com.gob.pgutierrezd.e_personas.models.LoginRegister;

/**
 * Created by pgutierrezd on 29/10/2016.
 */
public interface RegistroPresenter {
    void validateRegister(LoginRegister loginRegister, Context context);

    void onDestroy();
}
