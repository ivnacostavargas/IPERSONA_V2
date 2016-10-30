package com.gob.pgutierrezd.e_personas.presenters;

import android.content.Context;

import com.gob.pgutierrezd.e_personas.interactors.RegisterInteractorImpl;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroInteractor;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.registro.RegistroView;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;

/**
 * Created by pgutierrezd on 29/10/2016.
 */
public class RegistroPresenterImpl implements RegistroPresenter, RegistroInteractor.OnRegisterFinishedListener{

    private RegistroView mRegistroView;
    private RegistroInteractor mRegistroInteractor;
    private Context context;

    public RegistroPresenterImpl(RegistroView registroView, Context context){
        this.mRegistroView = registroView;
        this.mRegistroInteractor = new RegisterInteractorImpl();
        this.context = context;
    }

    @Override
    public void validateRegister(LoginRegister loginRegister, Context context) {
        if(mRegistroView != null){
            mRegistroView.showProgress();
            mRegistroInteractor.registerUserLogin(loginRegister,this,context);
        }
    }

    @Override
    public void onDestroy() {
        if(mRegistroView != null){
            mRegistroView = null;
        }
    }

    @Override
    public void setFieldErrorEmpty() {
        if(mRegistroView != null){
            mRegistroView.hideProgress();
            mRegistroView.setFieldErrorEmpty();
        }
    }

    @Override
    public void navigateToHome() {
        if(mRegistroView != null){
            mRegistroView.hideProgress();
            mRegistroView.navigateToHome();
        }
    }

    @Override
    public void errorConnectionServer() {
        if(mRegistroView != null){
            mRegistroView.hideProgress();
        }
    }
}
