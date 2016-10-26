package com.gob.pgutierrezd.e_personas.interactors;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.gob.pgutierrezd.e_personas.interfaces.inicio.InicioInteractor;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pgutierrezd on 26/10/2016.
 */
public class InicioInteractorImpl implements InicioInteractor{

    @Override
    public void login(LoginResult loginResult, final Context context, OnLoginFinishedListener listener) {
        if(loginResult != null){
            Profile profile = Profile.getCurrentProfile();
            // Facebook Email address
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object,GraphResponse response) {
                    response.getJSONObject().toString();
                    try {
                        Log.d("AAA", " " + object.getString("name") + ", " + object.getString("email"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
}
