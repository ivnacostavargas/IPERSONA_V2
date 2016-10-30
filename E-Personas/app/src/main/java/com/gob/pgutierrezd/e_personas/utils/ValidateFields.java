package com.gob.pgutierrezd.e_personas.utils;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by pgutierrezd on 29/10/2016.
 */
public class ValidateFields {

    public void validateText(EditText[] fields){
        for (EditText field: fields) {
            if(TextUtils.isEmpty(field.getText().toString())){
                field.setError("Campo requerido");
            }
        }
    }

}
