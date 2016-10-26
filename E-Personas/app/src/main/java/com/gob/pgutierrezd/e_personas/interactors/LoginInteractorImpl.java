package com.gob.pgutierrezd.e_personas.interactors;

import android.os.Handler;
import android.text.TextUtils;

import com.gob.pgutierrezd.e_personas.interfaces.login.LoginInteractor;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public class LoginInteractorImpl implements LoginInteractor {
    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                boolean error = false;

                if (TextUtils.isEmpty(username)){
                    listener.onUsernameErrorEmpty();
                    error = true;
                }else if (TextUtils.isEmpty(password)){
                    listener.onPasswordErrorEmpty();
                    error = true;
                }else if(!username.equals("admin")){
                    listener.onUsernameError();
                    error = true;
                }else if(!password.equals("123")){
                    listener.onPasswordError();
                    error = true;
                }
                if (!error){
                    listener.onSuccess();
                }
            }
        }, 1500);
    }
}
