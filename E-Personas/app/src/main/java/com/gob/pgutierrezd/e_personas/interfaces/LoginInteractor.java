package com.gob.pgutierrezd.e_personas.interfaces;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public interface LoginInteractor {
    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();

        void onUsernameErrorEmpty();

        void onPasswordErrorEmpty();
    }

    void login(String username, String password, OnLoginFinishedListener listener);
}
