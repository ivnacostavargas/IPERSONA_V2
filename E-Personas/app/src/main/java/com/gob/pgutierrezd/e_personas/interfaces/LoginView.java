package com.gob.pgutierrezd.e_personas.interfaces;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void setUsernameErrorEmpty();

    void setPasswordErrorEmpty();

    void navigateToHome();
}
