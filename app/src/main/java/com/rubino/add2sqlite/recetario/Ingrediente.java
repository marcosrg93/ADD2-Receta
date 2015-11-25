package com.rubino.add2sqlite.recetario;

import android.database.Cursor;

import com.rubino.add2sqlite.basedatos.Contrato;

/**
 * Created by marco on 22/11/2015.
 */
public class Ingrediente {

    private long id;
    private String nombre;

    public Ingrediente(){
        this(0,"");
    }

    public Ingrediente(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }


    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaIngrediente._ID)));
        setNombre(c.getString(c.getColumnIndex(Contrato.TablaIngrediente.NOMBRE)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ingrediente that = (Ingrediente) o;

        if (id != that.id) return false;
        return !(nombre != null ? !nombre.equals(that.nombre) : that.nombre != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ingrediente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }


}
