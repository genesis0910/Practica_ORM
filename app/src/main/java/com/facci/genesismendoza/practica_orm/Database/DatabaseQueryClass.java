package com.facci.genesismendoza.practica_orm.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.facci.genesismendoza.practica_orm.Caracteristicas.CrearCliente.Cliente;
import com.facci.genesismendoza.practica_orm.Datos_BD.DatosBD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseQueryClass {

    private Context context;

    public DatabaseQueryClass(Context context){
        this.context = context;
    }

    public long insertCliente(Cliente cliente){

        long id = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatosBD.COLUMNA_NOMBRE, cliente.getName());
        contentValues.put(DatosBD.COLUMNA_CEDULA, cliente.getCedula());
        contentValues.put(DatosBD.COLUMNA_CELULAR, cliente.getPhoneNumber());
        contentValues.put(DatosBD.COLUMNA_CORREO, cliente.getEmail());

        try {
            id = sqLiteDatabase.insertOrThrow(DatosBD.TABLA_CLIENTE, null, contentValues);
        } catch (SQLiteException e){
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return id;
    }

    public List<Cliente> getAllCliente(){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        try {

            cursor = sqLiteDatabase.query(DatosBD.TABLA_CLIENTE, null, null, null, null, null, null, null);
            if(cursor!=null)
                if(cursor.moveToFirst()){
                    List<Cliente> clienteList = new ArrayList<>();
                    do {
                        int id = cursor.getInt(cursor.getColumnIndex(DatosBD.COLUMNA_ID));
                        String name = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_NOMBRE));
                        String cedula = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_CEDULA));
                        String email = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_CORREO));
                        String phone = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_CELULAR));

                        clienteList.add(new Cliente(id, name, cedula, email, phone));
                    }   while (cursor.moveToNext());

                    return clienteList;
                }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return Collections.emptyList();
    }

    public Cliente getClienteByRegNum(String registrationNum){

        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        Cliente cliente = null;
        try {

            cursor = sqLiteDatabase.query(DatosBD.TABLA_CLIENTE, null,
                    DatosBD.COLUMNA_CEDULA + " = ? ", new String[]{String.valueOf(registrationNum)},
                    null, null, null);

            /**
                 // If you want to execute raw query then uncomment below 2 lines. And comment out above sqLiteDatabase.query() method.

                 String SELECT_QUERY = String.format("SELECT * FROM %s WHERE %s = %s", DatosBD.TABLA_CLIENTE, DatosBD.COLUMNA_CEDULA, String.valueOf(registrationNum));
                 cursor = sqLiteDatabase.rawQuery(SELECT_QUERY, null);
             */

            if(cursor.moveToFirst()){
                int id = cursor.getInt(cursor.getColumnIndex(DatosBD.COLUMNA_ID));
                String name = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_NOMBRE));
                String cedula = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_CEDULA));
                String phone = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_CELULAR));
                String email = cursor.getString(cursor.getColumnIndex(DatosBD.COLUMNA_CORREO));

                cliente = new Cliente(id, name, cedula, phone, email);
            }
        } catch (Exception e){
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show();
        } finally {
            if(cursor!=null)
                cursor.close();
            sqLiteDatabase.close();
        }

        return cliente;
    }

    public long updateClienteInfo(Cliente cliente){

        long rowCount = 0;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DatosBD.COLUMNA_NOMBRE, cliente.getName());
        contentValues.put(DatosBD.COLUMNA_CEDULA, cliente.getCedula());
        contentValues.put(DatosBD.COLUMNA_CELULAR, cliente.getPhoneNumber());
        contentValues.put(DatosBD.COLUMNA_CORREO, cliente.getEmail());

        try {
            rowCount = sqLiteDatabase.update(DatosBD.TABLA_CLIENTE, contentValues,
                    DatosBD.COLUMNA_ID + " = ? ",
                    new String[] {String.valueOf(cliente.getId())});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return rowCount;
    }

    public long deleteClienteByRegNum(String registrationNum) {
        long deletedRowCount = -1;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            deletedRowCount = sqLiteDatabase.delete(DatosBD.TABLA_CLIENTE,
                                    DatosBD.COLUMNA_CEDULA + " = ? ",
                                    new String[]{ String.valueOf(registrationNum)});
        } catch (SQLiteException e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }

        return deletedRowCount;
    }

    public boolean deleteAllCliente(){
        boolean deleteStatus = false;
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        try {
            //for "1" delete() method returns number of deleted rows
            //if you don't want row count just use delete(TABLE_NAME, null, null)
            sqLiteDatabase.delete(DatosBD.TABLA_CLIENTE, null, null);

            long count = DatabaseUtils.queryNumEntries(sqLiteDatabase, DatosBD.TABLA_CLIENTE);

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