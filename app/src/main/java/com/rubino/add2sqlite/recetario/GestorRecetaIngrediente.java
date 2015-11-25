package com.rubino.add2sqlite.recetario;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rubino.add2sqlite.basedatos.Ayudante;
import com.rubino.add2sqlite.basedatos.Contrato;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 23/11/2015.
 */
public class GestorRecetaIngrediente {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorRecetaIngrediente(Context c){
        Log.v("SQLAAD", "constructor del gestor de RECETAINGREDIENTE");
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



    public long insert(RecetaIngrediente p) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaIngrediente.IDRECETA, p.getIdreceta());
        valores.put(Contrato.TablaRecetaIngrediente.IDINGREDIENTE, p.getIdigendiente());
        valores.put(Contrato.TablaRecetaIngrediente.CANTIDAD, p.getCantidad());
        long id = bd.insert(Contrato.TablaRecetaIngrediente.TABLA, null, valores);
        return id;
    }

    public int delete(RecetaIngrediente r) {
        return delete(r.getId());
    }

    public int delete(long id){
        String condicion = Contrato.TablaRecetaIngrediente.IDINGREDIENTE + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaRecetaIngrediente.TABLA, condicion, argumentos);
        return cuenta;
    }

    public int update(RecetaIngrediente r){
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaRecetaIngrediente.IDRECETA, r.getIdreceta());
        valores.put(Contrato.TablaRecetaIngrediente.IDINGREDIENTE, r.getIdigendiente());
        valores.put(Contrato.TablaRecetaIngrediente.CANTIDAD, r.getCantidad());
        String condicion = Contrato.TablaRecetaIngrediente._ID + " = ?";
        String[] argumentos = { r.getId() + "" };
        int cuenta = bd.update(Contrato.TablaRecetaIngrediente.TABLA, valores,
                condicion, argumentos);
        return cuenta;
    }

    public RecetaIngrediente getRow(Cursor c) {
        RecetaIngrediente r = new RecetaIngrediente();
        r.setId(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngrediente._ID)));
        r.setIdreceta(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDRECETA)));
        r.setIdigendiente(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDINGREDIENTE)));
        r.setCantidad(c.getString(c.getColumnIndex(Contrato.TablaRecetaIngrediente.CANTIDAD)));
        return r;
    }

    public RecetaIngrediente getRow2(Cursor c) {
        RecetaIngrediente r = new RecetaIngrediente();
        r.setId(c.getLong(c.getColumnIndex(Contrato.TablaRecetaIngrediente._ID)));
        r.setIdreceta(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDRECETA)));
        r.setIdigendiente(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDINGREDIENTE)));
        r.setCantidad(c.getString(c.getColumnIndex(Contrato.TablaRecetaIngrediente.CANTIDAD)));

        return r;
    }

    public Ingrediente getRow3(Cursor c) {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(c.getInt(c.getColumnIndex(Contrato.TablaIngrediente._ID)));
        ingrediente.setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngrediente.NOMBRE)));

        return ingrediente;
    }

    public RecetaIngrediente getRow(long id) {
        Cursor c = getCursor("_id = ?", new String[]{id+""});
        return getRow(c);
    }

    public Cursor getRecetaIngrediente(long id){
        String sql = "select i.*, ri.* from receta_ingrediente ri join ingrediente i on ri.idingrediente = i._id where ri.idreceta = ? order by i.ingnombre ";
        String[] parametros = new String[]{id+""};
        return bd.rawQuery(sql, parametros);
    }

    public Cursor getCursor(){
        return getCursor(null, null);
    }

    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaRecetaIngrediente.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaRecetaIngrediente.IDRECETA+", "+Contrato.TablaRecetaIngrediente.CANTIDAD);
        return cursor;
    }

    public List<RecetaIngrediente> select(String condicion, String[] parametros) {
        List<RecetaIngrediente> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        RecetaIngrediente p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<RecetaIngrediente> getCantidadIng(long id){
        List<RecetaIngrediente> la = new ArrayList<>();
        Cursor cursor = getRecetaIngrediente(id);
        RecetaIngrediente p;
        while (cursor.moveToNext()) {
            p = getRow2(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }

    public List<Ingrediente> getNomIng(long id){
        List<Ingrediente> la = new ArrayList<>();
        Cursor cursor = getRecetaIngrediente(id);
        Ingrediente p;
        while (cursor.moveToNext()) {
            p = getRow3(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }
    public List<RecetaIngrediente> select() {
        return select(null,null);
    }

}
