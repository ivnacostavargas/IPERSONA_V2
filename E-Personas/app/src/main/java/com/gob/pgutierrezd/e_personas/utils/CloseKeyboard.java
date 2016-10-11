package com.gob.pgutierrezd.e_personas.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by pgutierrezd on 11/10/2016.
 */
public class CloseKeyboard {

    private Activity activity;

    public CloseKeyboard(Activity activity) {
        this.activity = activity;
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.activity.getCurrentFocus().getWindowToken(), 0);
    }

}
