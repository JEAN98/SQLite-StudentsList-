package com.example.jeancarlo.learn;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


//SqLite Class, we called like OpenHelper

public  class OpenHelper extends SQLiteOpenHelper {

    //Open Helper parameters
    public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //we create the dataTable and your attributes
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table estudiantes (ced integer primary key, nombre text, colegio text, secc text)");
    }

    //Delete and create new table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists estudiantes"); //Delete
        db.execSQL("create table estudiantes(ced integer primary key, nombre text, colegio text, secc text)");//Create
    }


    //Send Students Info
    public Cursor getStudents()
    {
        String  columns[]={"ced","nombre","colegio"};
        Cursor cursor=this.getReadableDatabase().query("estudiantes",columns,null,null,null,null,null);

        return cursor;
    }



}
