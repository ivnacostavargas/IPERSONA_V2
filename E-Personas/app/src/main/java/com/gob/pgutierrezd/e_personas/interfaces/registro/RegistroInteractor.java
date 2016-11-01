package com.gob.pgutierrezd.e_personas.interfaces.registro;

import android.content.Context;

import com.gob.pgutierrezd.e_personas.models.LoginRegister;

/**
 * Created by pgutierrezd on 29/10/2016.
 */
public interface RegistroInteractor {
    interface OnRegisterFinishedListener {
        void setFieldErrorEmpty();

        void navigateToHome();

        void errorConnectionServer();
    }

    void registerUserLogin(LoginRegister loginRegister, OnRegisterFinishedListener listener, Context context);
}
