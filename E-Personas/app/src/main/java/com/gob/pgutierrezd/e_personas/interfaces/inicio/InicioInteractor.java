package com.gob.pgutierrezd.e_personas.interfaces.inicio;

import android.content.Context;

import com.facebook.login.LoginResult;

/**
 * Created by pgutierrezd on 25/10/2016.
 */
public interface InicioInteractor {

    interface OnLoginFinishedListener {
        void onSuccess();
        void onFail();
    }

    void login(LoginResult loginResult, Context context, OnLoginFinishedListener listener);

}
