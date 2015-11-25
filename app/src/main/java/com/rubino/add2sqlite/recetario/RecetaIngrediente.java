package com.rubino.add2sqlite.recetario;

import android.database.Cursor;

import com.rubino.add2sqlite.basedatos.Contrato;

/**
 * Created by marco on 23/11/2015.
 */
public class RecetaIngrediente {


    private long id;
    private  int idreceta;
    private  int idigendiente;
    private  String cantidad;


    public  RecetaIngrediente(){
            this(0,0,0,"");
    }

    public RecetaIngrediente(long id, int idreceta, int idigendiente, String cantidad) {
        this.id = id;
        this.idreceta = idreceta;
        this.idigendiente = idigendiente;
        this.cantidad = cantidad;
    }


    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaReceta._ID)));
        setIdreceta(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDRECETA)));
        setIdigendiente(c.getInt(c.getColumnIndex(Contrato.TablaRecetaIngrediente.IDINGREDIENTE)));
        setCantidad(c.getString(c.getColumnIndex(Contrato.TablaRecetaIngrediente.CANTIDAD)));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdreceta() {
        return idreceta;
    }

    public void setIdreceta(int idreceta) {
        this.idreceta = idreceta;
    }

    public int getIdigendiente() {
        return idigendiente;
    }

    public void setIdigendiente(int idigendiente) {
        this.idigendiente = idigendiente;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RecetaIngrediente that = (RecetaIngrediente) o;

        if (id != that.id) return false;
        if (idreceta != that.idreceta) return false;
        if (idigendiente != that.idigendiente) return false;
        return !(cantidad != null ? !cantidad.equals(that.cantidad) : that.cantidad != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + idreceta;
        result = 31 * result + idigendiente;
        result = 31 * result + (cantidad != null ? cantidad.hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "RecetaIngrediente{" +
                "id=" + id +
                ", idreceta=" + idreceta +
                ", idigendiente=" + idigendiente +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}
