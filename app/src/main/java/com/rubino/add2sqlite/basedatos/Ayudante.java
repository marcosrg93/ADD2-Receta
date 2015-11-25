package com.rubino.add2sqlite.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by marco on 21/08/2015.
 */
public class Ayudante extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "recetario.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql, sql1,sql2;


        sql = "create table " + Contrato.TablaReceta.TABLA + "( " +
                Contrato.TablaReceta._ID + " integer primary key autoincrement, " +
                Contrato.TablaReceta.NOMBRE + " text, " +
                Contrato.TablaReceta.INSTRUCCIONES + " text, " +
                Contrato.TablaReceta.FOTO + " text, " +
                Contrato.TablaReceta.IDCATEGORIA + " integer " +
                ")";
        Log.v("CREA LA BD: ", sql);
        db.execSQL(sql);

        sql1 = "create table " + Contrato.TablaIngrediente.TABLA + "( " +
                Contrato.TablaIngrediente._ID + " integer primary key autoincrement, " +
                Contrato.TablaIngrediente.NOMBRE + " text" +
                ")";
        Log.v("CREA LA TABLA2: ", sql1);
        db.execSQL(sql1);

        sql2 = "create table " + Contrato.TablaRecetaIngrediente.TABLA + "( " +
                Contrato.TablaRecetaIngrediente._ID + " integer primary key autoincrement, " +
                Contrato.TablaRecetaIngrediente.IDRECETA + " integer, " +
                Contrato.TablaRecetaIngrediente.IDINGREDIENTE + " integer, " +
                Contrato.TablaRecetaIngrediente.CANTIDAD + " text " +
                ")";
        Log.v("CREA LA TABLA3: ", sql2);
        db.execSQL(sql2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        Log.v("SQLAAD","on upgrade");
        String sql="drop table if exists "
                + Contrato.TablaReceta.TABLA;
        String sql2="drop table if exists "
                + Contrato.TablaIngrediente.TABLA;
        String sql3="drop table if exists "
                + Contrato.TablaRecetaIngrediente.TABLA;
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        onCreate(db);
    }//onUpgrade
}//Ayudante