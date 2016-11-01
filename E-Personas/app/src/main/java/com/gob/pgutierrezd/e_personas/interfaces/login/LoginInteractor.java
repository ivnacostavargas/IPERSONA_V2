package com.gob.pgutierrezd.e_personas.interfaces.login;

import android.content.Context;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public interface LoginInteractor {
    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onUsernameErrorEmpty();

        void onPasswordErrorEmpty();

        void onSuccess();

        void onErrorConnectionServer();
    }

    void login(String username, String password, OnLoginFinishedListener listener, Context context);
}
