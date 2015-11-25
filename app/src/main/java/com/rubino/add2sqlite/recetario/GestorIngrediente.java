package com.rubino.add2sqlite.recetario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rubino.add2sqlite.R;
import com.rubino.add2sqlite.basedatos.Ayudante;
import com.rubino.add2sqlite.basedatos.Contrato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 22/11/2015.
 */
public class GestorIngrediente {


    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorIngrediente(Context c){
        Log.v("SQLAAD", "constructor del gestor de Ingrediente");
        abd = new Ayudante(c);
    }
    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    public long insert(Ingrediente p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaIngrediente.NOMBRE, p.getNombre());
        long id = bd.insert(Contrato.TablaIngrediente.TABLA, null, valores);
        return id;
    }

    public int delete(Ingrediente r) {
        return delete(r.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaIngrediente._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaIngrediente.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Ingrediente r){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaIngrediente.NOMBRE, r.getNombre());
        String condicion = Contrato.TablaIngrediente._ID + " = ?";
        String[] argumentos = { r.getId() + "" };
        int cuenta = bd.update(Contrato.TablaIngrediente.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public Ingrediente getRow(Cursor c) {
        Ingrediente r = new Ingrediente();
        r.setId(c.getLong(c.getColumnIndex(Contrato.TablaIngrediente._ID)));
        r.setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngrediente.NOMBRE)));
        return r;
    }

    public Ingrediente getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaIngrediente.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaIngrediente.NOMBRE);
        return cursor;
    }

    public List<Ingrediente> select(String condicion, String[] parametros) {
        List<Ingrediente> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Ingrediente p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Ingrediente> select() {
        return select(null,null);
    }


    public long selectIdIngredienteNombre(String nombre){
        String[] columnas = {Contrato.TablaIngrediente._ID};
        String condicion = Contrato.TablaIngrediente.NOMBRE + " = ?";
        String[] argumentos = { nombre };
        Cursor cursor = bd.query(Contrato.TablaIngrediente.TABLA, columnas, condicion, argumentos, null, null, null);
        cursor.moveToFirst();
        return cursor.getLong(cursor.getColumnIndex(Contrato.TablaIngrediente._ID));
    }

    public List<Ingrediente> selectIngredientes() {
        List<Ingrediente> lista;
        lista = new ArrayList<>();
        Cursor cursor = bd.query(Contrato.TablaIngrediente.TABLA, null,
                null, null, null, null, null);
        cursor.moveToFirst();
        Ingrediente ingre;
        while (!cursor.isAfterLast()) {
            ingre = getRow(cursor);
            lista.add(ingre);
            cursor.moveToNext();
        }
        cursor.close();
        if(lista==null){
            lista.add(new Ingrediente());
            return lista;
        }
        return lista;
    }

    public static  int getDrawable(){
        return R.drawable.avatardef;
    }
}
