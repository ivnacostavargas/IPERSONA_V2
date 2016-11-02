package com.gob.pgutierrezd.e_personas.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;

import com.gob.pgutierrezd.e_personas.utils.Constants;

import static com.gob.pgutierrezd.e_personas.utils.Constants.COMENTARIOS;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_ACCIONES_MUNICIPIO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_DEPORTES_BOOL;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_DEPORTES_COM;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_ESPERAR_MARCOS;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_GOBIERNO_MARCOS_AGUILAR;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_HORARIO_PATRULLAJE;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_NECESIDAD_COLONIA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_OPINION_POLICIA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_PATRULLAJE;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_TRAMITE_PENDIENTE_BOOL;
import static com.gob.pgutierrezd.e_personas.utils.Constants.CS_TRAMITE_PENDIENTE_COM;
import static com.gob.pgutierrezd.e_personas.utils.Constants.EDAD;
import static com.gob.pgutierrezd.e_personas.utils.Constants.FACEBOOK;
import static com.gob.pgutierrezd.e_personas.utils.Constants.FECHA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.FOTO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IB_CONSIDERA_RIESGOS;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IB_DEBEN_ATENDERSE;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IB_DESMALEZADO_LOTES;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IB_HAY_LOTES_BALDIOS;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IB_REALIZADO_LIMPIEZA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IDENCUESTA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IDINFO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.IDUSUARIO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.ID_HORARIO_MAYOR_RIESGO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.ID_OPINION_REALIZAR_OBRA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.ID_OPINION_REALIZAR_OBRA_NOMBRE;
import static com.gob.pgutierrezd.e_personas.utils.Constants.ID_RIESGOS_FRACCIONAMIENTO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.LATITUD;
import static com.gob.pgutierrezd.e_personas.utils.Constants.LONGITUD;
import static com.gob.pgutierrezd.e_personas.utils.Constants.LUGAR_ENCUESTA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.SA_ALAMEDA_RECREO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.SA_APARIENZA_ALAMEDA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.SA_MEJORAR_IMAGEN;
import static com.gob.pgutierrezd.e_personas.utils.Constants.SA_PROBLEMA_ALAMEDA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.SA_SEGURO_AL_TRANSITAR;
import static com.gob.pgutierrezd.e_personas.utils.Constants.SEXO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.STATUS;
import static com.gob.pgutierrezd.e_personas.utils.Constants.TABLE_ENCUESTAS;
import static com.gob.pgutierrezd.e_personas.utils.Constants.TABLE_INFORMACION_COMPLEMENTARIA;
import static com.gob.pgutierrezd.e_personas.utils.Constants.TELEFONO;
import static com.gob.pgutierrezd.e_personas.utils.Constants.TWITTER;

/**
 * Created by pgutierrezd on 10/08/2016.
 */
public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "epersonas.db";
    private static final int DATABASE_VERSION = 1;

    //tabla de encuestas
    public static final String CREATE_TABLE_ENCUESTAS =
            "CREATE TABLE " + TABLE_ENCUESTAS + "(" +
                    IDENCUESTA + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    IDUSUARIO + " int(11) DEFAULT NULL," +
                    IDINFO + " int(11) DEFAULT NULL," +
                    CS_OPINION_POLICIA + " varchar(250) DEFAULT NULL," +
                    CS_PATRULLAJE + " varchar(250) DEFAULT NULL," +
                    CS_HORARIO_PATRULLAJE + " varchar(250) DEFAULT NULL," +
                    CS_ACCIONES_MUNICIPIO + " varchar(250) DEFAULT NULL," +
                    CS_TRAMITE_PENDIENTE_BOOL + " tinyint(1) DEFAULT NULL," +
                    CS_TRAMITE_PENDIENTE_COM + " varchar(250) DEFAULT NULL," +
                    CS_GOBIERNO_MARCOS_AGUILAR + " varchar(250) DEFAULT NULL," +
                    CS_ESPERAR_MARCOS + " varchar(250) DEFAULT NULL," +
                    CS_DEPORTES_BOOL + " tinyint(1) DEFAULT NULL," +
                    CS_DEPORTES_COM + " varchar(250) DEFAULT NULL," +
                    CS_NECESIDAD_COLONIA + " varchar(250) DEFAULT NULL," +
                    ID_OPINION_REALIZAR_OBRA_NOMBRE + " varchar(80) DEFAULT NULL," +
                    ID_OPINION_REALIZAR_OBRA + " int(11) DEFAULT NULL," +
                    ID_RIESGOS_FRACCIONAMIENTO + " varchar(250) DEFAULT NULL," +
                    ID_HORARIO_MAYOR_RIESGO + " varchar(250) DEFAULT NULL," +
                    IB_HAY_LOTES_BALDIOS + " tinyint(1) DEFAULT NULL," +
                    IB_CONSIDERA_RIESGOS + " tinyint(1) DEFAULT NULL," +
                    IB_DEBEN_ATENDERSE + " tinyint(1) DEFAULT NULL," +
                    IB_REALIZADO_LIMPIEZA + " tinyint(1) DEFAULT NULL," +
                    IB_DESMALEZADO_LOTES + " varchar(250) DEFAULT NULL," +
                    SA_APARIENZA_ALAMEDA + " tinyint(1) DEFAULT NULL," +
                    SA_SEGURO_AL_TRANSITAR + " tinyint(1) DEFAULT NULL," +
                    SA_ALAMEDA_RECREO + " tinyint(1) DEFAULT NULL," +
                    SA_PROBLEMA_ALAMEDA + " varchar(250) DEFAULT NULL," +
                    SA_MEJORAR_IMAGEN + " varchar(250) DEFAULT NULL," +
                    LUGAR_ENCUESTA + " varchar(100) DEFAULT NULL," +
                    FECHA + " date DEFAULT NULL," +
                    STATUS + " tinyint(1) DEFAULT NULL," +
                    LATITUD + " varchar(30) DEFAULT NULL," +
                    LONGITUD + " varchar(30) DEFAULT NULL," +
                    COMENTARIOS + " varchar(300) DEFAULT NULL" +
    ");";

    //TABLA DE INFORMACION COMPLEMENTARIA
    public static final String CREATE_TABLE_INFORMACION =
            "CREATE TABLE " + TABLE_INFORMACION_COMPLEMENTARIA + "("+
                    IDINFO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TELEFONO + " varchar(20) DEFAULT NULL, " +
                    FACEBOOK + " varchar(40) DEFAULT NULL, " +
                    TWITTER + " varchar(40) DEFAULT NULL, " +
                    SEXO + " int(3) DEFAULT NULL, " +
                    EDAD + " int(5) DEFAULT NULL, " +
                    FECHA + " date DEFAULT NULL, " +
                    FOTO + " varchar(40) DEFAULT NULL " +
                    ");";

    public DataBaseOpenHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ENCUESTAS);
        db.execSQL(CREATE_TABLE_INFORMACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        /* *
        * Cada vez que actualice el proyecto hacer...
        * */
        onCreate(db);
    }
}