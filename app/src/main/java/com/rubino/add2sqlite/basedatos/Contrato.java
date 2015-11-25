package com.rubino.add2sqlite.basedatos;

import android.provider.BaseColumns;

/**
 * Created by marco on 21/08/2015.
 */
public class Contrato{

    private Contrato (){
    }

    public static abstract  class TablaReceta implements BaseColumns{
        public static final String TABLA = "receta";
        public static final String NOMBRE = "nombre";
        public static final String FOTO = "foto";
        public static final String INSTRUCCIONES = "instrucciones";
        public static final String IDCATEGORIA = "idcategoria";

    }
    public static abstract class TablaIngrediente implements BaseColumns {
        public static final String TABLA = "ingrediente";
        public static final String NOMBRE = "ingnombre";

    }

    public static abstract class TablaRecetaIngrediente implements BaseColumns {
        public static final String TABLA = "receta_ingrediente";
        public static final String IDRECETA = "idReceta";
        public static final String IDINGREDIENTE = "idIngrediente";
        public static final String CANTIDAD = "cantidad";

    }
}
