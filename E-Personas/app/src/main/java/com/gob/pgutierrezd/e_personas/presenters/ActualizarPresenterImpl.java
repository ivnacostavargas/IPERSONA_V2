package com.gob.pgutierrezd.e_personas.presenters;

import android.content.Context;
import android.widget.EditText;

import com.gob.pgutierrezd.e_personas.interactors.ActualizarInteractorImpl;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarInteractor;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.actualizar.ActualizarView;
import com.gob.pgutierrezd.e_personas.models.LoginRegister;

/**
 * Created by pgutierrezd on 30/10/2016.
 */
public class ActualizarPresenterImpl implements ActualizarPresenter, ActualizarInteractor.OnUpdateFinishedListener {

    private ActualizarView mActualizarView;
    private ActualizarInteractor mActualizarInteractor;
    private Context context;

    public ActualizarPresenterImpl(ActualizarView actualizarView, Context context){
        mActualizarView = actualizarView;
        mActualizarInteractor = new ActualizarInteractorImpl();
        this.context = context;
    }

    @Override
    public void getData(EditText[] data, int id, Context context) {
        if(mActualizarView != null){
            mActualizarView.showProgress();
            mActualizarInteractor.getUser(data,id,this,context);
        }
    }

    @Override
    public void updateData(LoginRegister loginRegister, Context context) {
        if(mActualizarView != null){
            mActualizarView.showProgress();
        }
    }

    @Override
    public void onDestroy() {
        if(mActualizarView != null){
            mActualizarView = null;
        }
    }

    @Override
    public void getDataFinish() {
        if(mActualizarView != null){
            mActualizarView.hideProgress();
        }
    }

    @Override
    public void update() {
        if(mActualizarView != null){
            mActualizarView.hideProgress();
        }
    }

    @Override
    public void errorConnectionServer() {
        if(mActualizarView != null){
            mActualizarView.hideProgress();
        }
    }
}
