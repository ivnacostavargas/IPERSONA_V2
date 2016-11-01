package com.gob.pgutierrezd.e_personas.interfaces.actualizar;

/**
 * Created by pgutierrezd on 30/10/2016.
 */
public interface ActualizarView {

    void showProgress();

    void hideProgress();

    void showData();

    void update();

    void errorConnectionServer();

}
