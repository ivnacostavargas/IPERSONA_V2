package com.gob.pgutierrezd.e_personas.presenters;

import android.content.Context;

import com.facebook.login.LoginResult;
import com.gob.pgutierrezd.e_personas.interactors.InicioInteractorImpl;
import com.gob.pgutierrezd.e_personas.interfaces.inicio.InicioInteractor;
import com.gob.pgutierrezd.e_personas.interfaces.inicio.InicioPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.inicio.InicioView;

/**
 * Created by pgutierrezd on 26/10/2016.
 */
public class InicioPresenterImpl implements InicioPresenter, InicioInteractor.OnLoginFinishedListener {

    private InicioView inicioView;
    private InicioInteractor inicioInteractor;
    private Context context;

    public InicioPresenterImpl(InicioView inicioView, Context context){
        this.inicioView  = inicioView;
        this.inicioInteractor = new InicioInteractorImpl();
        this.context = context;
    }

    @Override
    public void validateAccessFacebook(LoginResult loginResult) {
        if (inicioView != null) {
            inicioView.showProgress();
            inicioInteractor.login(loginResult,context,this);
        }
    }

    @Override
    public void goToLogin() {
        if (inicioView != null) {
            inicioView.goLogin();
        }
    }

    @Override
    public void onDestroy() {
        inicioView = null;
    }

    @Override
    public void onSuccess() {
        if (inicioView != null) {
            inicioView.loginFacebook();
            inicioView.hideProgress();
        }
    }

    @Override
    public void onFail() {
        if (inicioView != null) {
            inicioView.onFailLogin();
        }
    }
}
