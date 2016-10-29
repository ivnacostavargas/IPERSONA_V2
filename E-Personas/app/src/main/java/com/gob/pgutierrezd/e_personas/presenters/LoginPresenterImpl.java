package com.gob.pgutierrezd.e_personas.presenters;

import android.content.Context;

import com.gob.pgutierrezd.e_personas.interactors.LoginInteractorImpl;
import com.gob.pgutierrezd.e_personas.interfaces.login.LoginInteractor;
import com.gob.pgutierrezd.e_personas.interfaces.login.LoginPresenter;
import com.gob.pgutierrezd.e_personas.interfaces.login.LoginView;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;
    private Context context;

    public LoginPresenterImpl(LoginView loginView, Context context) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
        this.context = context;
    }

    @Override public void validateCredentials(String username, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }

        loginInteractor.login(username, password, this, context);
    }

    @Override public void onDestroy() {
        loginView = null;
    }

    @Override public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override
    public void onUsernameErrorEmpty() {
        if (loginView != null) {
            loginView.setUsernameErrorEmpty();
            loginView.hideProgress();
        }
    }

    @Override
    public void onPasswordErrorEmpty() {
        if (loginView != null) {
            loginView.setPasswordErrorEmpty();
            loginView.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }

    @Override
    public void onErrorConnectionServer() {
        if (loginView != null) {
            loginView.errorConnectionServer();
        }
    }
}
