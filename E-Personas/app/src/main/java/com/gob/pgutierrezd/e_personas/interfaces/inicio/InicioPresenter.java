package com.gob.pgutierrezd.e_personas.interfaces.inicio;

import com.facebook.login.LoginResult;

/**
 * Created by pgutierrezd on 25/10/2016.
 */
public interface InicioPresenter {

    void validateAccessFacebook(LoginResult loginResult);

    void goToLogin();

    void onDestroy();

}
