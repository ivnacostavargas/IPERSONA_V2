package com.gob.pgutierrezd.e_personas.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by pgutierrezd on 17/10/2016.
 */
public class Constants {

    //Google Maps API
    public static final int LOCATION_REQUEST_CODE = 1;
    public static final LatLng QUERETARO = new LatLng(20.5872194, -100.387161);

    //Facebook API
    public static final List<String> PERMISSIONS_FACEBOOK =
            Collections.unmodifiableList(Arrays.asList("email", "public_profile"));

    //SharedPreferences
    public static final String SHARED_PREFERENCES_LOGIN = "login";
    public static final String SHARED_PREFERENCES_LOGIN_ID_FLAG = "id";
    public static final String SHARED_PREFERENCES_LOGIN_EMAIL_FLAG = "email";

    //Encuesta
    public static final int CAMARA_REQUEST = 3;
    public static final int MEDIA_TYPE_IMAGE = 1;

    public final static String IMAGE_PROFILE="base64.txt";


    public static final int DATE_DIALOG_ID = 999;

}
