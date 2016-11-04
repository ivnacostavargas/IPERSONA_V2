package com.gob.pgutierrezd.e_personas.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by pgutierrezd on 02/11/2016.
 */
public class TakePhoto {

    private Activity activity;
    private Uri mPath;

    public TakePhoto(Activity activity){
        this.activity = activity;
    }

    public void getPhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPath = getOutputMediaFileUri(Constants.MEDIA_TYPE_IMAGE);
        activity.startActivityForResult(intent, Constants.CAMARA_REQUEST);
    }

    private Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"E-Personas");
        if( !mediaStorageDir.exists()){
            if( !mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if(type == Constants.MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        }else{
            return null;
        }
        return mediaFile;
    }

}
