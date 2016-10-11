package com.gob.pgutierrezd.e_personas.interfaces;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public interface LoginPresenter {
    void validateCredentials(String username, String password);

    void onDestroy();
}
