package com.facci.genesismendoza.practica_orm.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.facci.genesismendoza.practica_orm.Data.Cliente;
import com.facci.genesismendoza.practica_orm.Data.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CRUD {
    private Context context;

    public CRUD(Context context){
        this.context = context;
    }

    // Insertar ____________________________
    public long insertar(Cliente cliente){

        long id = -1;
        DataBaseHelper databaseHelper = DataBaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Data.COLUMN_NAME, cliente.getNombre());
        contentValues.put(Data.COLUMN_PHONE, cliente.getCelular());
        contentValues.put(Data.COLUMN_EMAIL, cliente.getEmail());

        try {
            id = sqLiteDatabase.insertOrThrow(Data.TABLE_NAME, null, contentValues);
        } catch (SQLiteException e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    // Listar
    public List<Cliente> getAllStudent(){

        DataBaseHelper databaseHelper = DataBaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(Data.TABLE_NAME, null, null, null, null, null, null, null);

            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Cliente> studentList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(Data.COLUMN_ID));
                        String name = cursor.getString(cursor.getColumnIndex(Data.COLUMN_NAME));
                        String email = cursor.getString(cursor.getColumnIndex(Data.COLUMN_EMAIL));
                        String phone = cursor.getString(cursor.getColumnIndex(Data.COLUMN_PHONE));

                        studentList.add(new Cliente(id, name,phone,email));
                    }   while (cursor.moveToNext());

                    return studentList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }
        return Collections.emptyList();
    }


    // Actualizar .....................

    public long Actualizar(Cliente cliente){

        long rowCount = 0;
        DataBaseHelper databaseHelper = DataBaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Data.COLUMN_NAME, cliente.getNombre());
        contentValues.put(Data.COLUMN_PHONE, cliente.getCelular());
        contentValues.put(Data.COLUMN_EMAIL, cliente.getEmail());

        try {
            rowCount = sqLiteDatabase.update(Data.TABLE_NAME, contentValues,
                    Data.COLUMN_ID + " = ? ",
                    new String[] {String.valueOf(cliente.getId())});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }


    /// eliminar ................................

    public long eliminar(int id) {
        long deletedRowCount = -1;
        DataBaseHelper databaseHelper = DataBaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(Data.TABLE_NAME,
                    Data.COLUMN_ID + " = ? ",
                    new String[]{ String.valueOf(id)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }


    public boolean Eliminar_todo(){
        boolean deleteStatus = false;
        DataBaseHelper databaseHelper = DataBaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(Data.TABLE_NAME, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, Data.TABLE_NAME);

            if(count==0)
                deleteStatus = true;

        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deleteStatus;
    }
}
