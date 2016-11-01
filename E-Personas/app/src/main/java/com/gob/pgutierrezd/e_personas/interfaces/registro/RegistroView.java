package com.gob.pgutierrezd.e_personas.interfaces.registro;

/**
 * Created by pgutierrezd on 29/10/2016.
 */
public interface RegistroView {

    void showProgress();

    void hideProgress();

    void setFieldErrorEmpty();

    void navigateToHome();

    void errorConnectionServer();

}
