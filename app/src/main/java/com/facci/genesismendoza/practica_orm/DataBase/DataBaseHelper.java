package com.facci.genesismendoza.practica_orm.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.facci.genesismendoza.practica_orm.Data.Data;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static DataBaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Data.DATABASE_NAME;

    // Constructor
    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DataBaseHelper getInstance(Context context){
        if(databaseHelper==null){
            databaseHelper = new DataBaseHelper(context);
        }
        return databaseHelper;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Data.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables SQL execution
        String CREATE_STUDENT_TABLE = "CREATE TABLE " + Data.TABLE_NAME + "("
                + Data.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Data.COLUMN_NAME + " TEXT NOT NULL, "
                + Data.COLUMN_PHONE + " TEXT, " //nullable
                + Data.COLUMN_EMAIL + " TEXT " //nullable
                + ")";

        Log.i("Table create SQL: ", CREATE_STUDENT_TABLE);

        db.execSQL(CREATE_STUDENT_TABLE);

        Log.i("DB created!","ok");
    }
}