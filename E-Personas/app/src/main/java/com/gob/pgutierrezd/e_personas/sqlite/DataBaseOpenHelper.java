package com.gob.pgutierrezd.e_personas.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pgutierrezd on 10/08/2016.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "epersonas.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        /* *
        * Cada vez que actualice el proyecto hacer...
        * */
        onCreate(db);
    }
}