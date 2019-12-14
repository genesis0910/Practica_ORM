package com.facci.genesismendoza.practica_orm.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.facci.genesismendoza.practica_orm.Datos_BD.DatosBD;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = DatosBD.NOMBRE_DB;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DatabaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + DatosBD.TABLA_CLIENTE + "("
                + DatosBD.COLUMNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ DatosBD.COLUMNA_NOMBRE + " TEXT NOT NULL, "
                + DatosBD.COLUMNA_CEDULA + " INTEGER NOT NULL UNIQUE, " + DatosBD.COLUMNA_CELULAR + " TEXT, "
                + DatosBD.COLUMNA_CORREO + " TEXT " + ")";

        db.execSQL(CREATE_TABLE);
        Log.i("CrearTable", "Tabla creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatosBD.TABLA_CLIENTE);
        onCreate(db);
    }

}
