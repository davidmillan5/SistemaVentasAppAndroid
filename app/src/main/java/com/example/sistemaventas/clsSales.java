package com.example.sistemaventas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//debe extender de la superclase sqliteOpenHelper
public class clsSales extends SQLiteOpenHelper {
    //Definir las tablas de la base de datos
    String tblSeller = "CREATE TABLE seller(idseller text primary key,fullname text, email text,password text, totcomision integer)";
    String tblSales = "CREATE TABLE sales(idsale integer primary key autoincrement, idseller text, date text, salevalue integer)";
    public clsSales( Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Ejecutar la instrucción para crear la (s) tabla (s)
        db.execSQL(tblSeller);
        db.execSQL(tblSales);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Eliminar y crearlas de nuevo, cada tabla
        db.execSQL("DROP TABLE seller");
        db.execSQL(tblSeller);
        db.execSQL("DROP TABLE sales");
        db.execSQL(tblSales);
    }
}
