package com.rubino.add2sqlite.recetario;

import android.database.Cursor;

import com.rubino.add2sqlite.basedatos.Contrato;

/**
 * Created by marco on 17/11/2015.
 */
public class Receta {


    private long id;
    private String nombre;
    private String instrucciones;
    private String foto;
    private int idcategoria;

    public Receta(){
        this(0,"","","",0);
    }

    public Receta(long id, String nombre, String instrucciones, String foto, int idcategoria) {
        this.id = id;
        this.nombre = nombre;
        this.instrucciones = instrucciones;
        this.foto = foto;
        this.idcategoria = idcategoria;
    }

    public void set(Cursor c){
        setId(c.getLong(c.getColumnIndex(Contrato.TablaReceta._ID)));
        setNombre(c.getString(c.getColumnIndex(Contrato.TablaReceta.NOMBRE)));
        setInstrucciones(c.getString(c.getColumnIndex(Contrato.TablaReceta.INSTRUCCIONES)));
        setFoto(c.getString(c.getColumnIndex(Contrato.TablaReceta.FOTO)));
        setIdcategoria(c.getInt(c.getColumnIndex(Contrato.TablaReceta.IDCATEGORIA)));
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

    public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria= idcategoria;
    }
    public void setIdcategoria(String idcategoria) {
        int idcat = 0;
        try{
            idcat = Integer.parseInt(idcategoria);
        }catch (NumberFormatException e) {
        }
        setIdcategoria(idcat);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receta receta = (Receta) o;

        if (id != receta.id) return false;
        if (idcategoria != receta.idcategoria) return false;
        if (nombre != null ? !nombre.equals(receta.nombre) : receta.nombre != null) return false;
        if (instrucciones != null ? !instrucciones.equals(receta.instrucciones) : receta.instrucciones != null)
            return false;
        return !(foto != null ? !foto.equals(receta.foto) : receta.foto != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (instrucciones != null ? instrucciones.hashCode() : 0);
        result = 31 * result + (foto != null ? foto.hashCode() : 0);
        result = 31 * result + idcategoria;
        return result;
    }

    @Override
    public String toString() {
        return "Receta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", instrucciones='" + instrucciones + '\'' +
                ", foto='" + foto + '\'' +
                ", idcategoria=" + idcategoria +
                '}';
    }

}
