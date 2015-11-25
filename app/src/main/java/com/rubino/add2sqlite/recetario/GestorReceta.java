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

public class GestorReceta {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorReceta(Context c){
        Log.v("SQLAAD","constructor del gestor de películas");
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

    public long insert(Receta p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaReceta.NOMBRE, p.getNombre());
        valores.put(Contrato.TablaReceta.INSTRUCCIONES, p.getInstrucciones());
        valores.put(Contrato.TablaReceta.FOTO, p.getFoto());
        valores.put(Contrato.TablaReceta.IDCATEGORIA, p.getIdcategoria());
        long id = bd.insert(Contrato.TablaReceta.TABLA, null, valores);
        return id;
    }

    public int delete(Receta r) {
        return delete(r.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaReceta._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaReceta.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(Receta r){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaReceta.NOMBRE, r.getNombre());
        valores.put(Contrato.TablaReceta.INSTRUCCIONES, r.getInstrucciones());
        valores.put(Contrato.TablaReceta.FOTO, r.getFoto());
        valores.put(Contrato.TablaReceta.IDCATEGORIA, r.getIdcategoria());
        String condicion = Contrato.TablaReceta._ID + " = ?";
        String[] argumentos = { r.getId() + "" };
        int cuenta = bd.update(Contrato.TablaReceta.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public Receta getRow(Cursor c) {
        Receta r = new Receta();
        r.setId(c.getLong(c.getColumnIndex(Contrato.TablaReceta._ID)));
        r.setNombre(c.getString(c.getColumnIndex(Contrato.TablaReceta.NOMBRE)));
        r.setInstrucciones(c.getString(c.getColumnIndex(Contrato.TablaReceta.INSTRUCCIONES)));
        r.setFoto(c.getString(c.getColumnIndex(Contrato.TablaReceta.FOTO)));
        r.setIdcategoria(c.getInt(c.getColumnIndex(Contrato.TablaReceta.IDCATEGORIA)));
        return r;
    }

    public Receta getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaReceta.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaReceta.NOMBRE+", "+Contrato.TablaReceta.INSTRUCCIONES);
        return cursor;
    }

    public List<Receta> select(String condicion, String[] parametros) {
        List<Receta> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Receta p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Receta> select() {
        return select(null,null);
    }


    public static  int getDrawable(){
        return R.drawable.avatardef;
    }
}