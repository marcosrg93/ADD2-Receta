package com.rubino.add2sqlite.actividades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rubino.add2sqlite.R;
import com.rubino.add2sqlite.recetario.GestorIngrediente;
import com.rubino.add2sqlite.recetario.GestorReceta;
import com.rubino.add2sqlite.recetario.GestorRecetaIngrediente;
import com.rubino.add2sqlite.recetario.Ingrediente;
import com.rubino.add2sqlite.recetario.Receta;
import com.rubino.add2sqlite.recetario.RecetaIngrediente;

import java.util.ArrayList;
import java.util.List;

public class Alta extends AppCompatActivity {

    private TextView tvTexto;
    private GestorReceta gReceta;
    private GestorIngrediente gIn;
    private GestorRecetaIngrediente gReIn;
    private EditText etReceta;
    private EditText etInstrucciones;
    private EditText etFoto;
    private EditText etIdCatg;
    private EditText etId;
    private EditText et;
    private Button btAdd,btIng;
    private Ingrediente ingrediente;
    private List<String[]> ingredientesReceta;
    private final static int ING=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta);
        init();
    }

    private void init(){
        this.btAdd = (Button) findViewById(R.id.btAddIg);
        this.etReceta = (EditText) findViewById(R.id.etIngredienteED);
        this.etFoto = (EditText) findViewById(R.id.etFotoED);
        this.etInstrucciones = (EditText) findViewById(R.id.etInstruccionesED);
        this.etIdCatg = (EditText) findViewById(R.id.etIdCategoriaED);
        this.tvTexto = (TextView) findViewById(R.id.tvadding);
        this.btIng = (Button) findViewById(R.id.btIngrediente);
        ingredientesReceta = new ArrayList<>();
        gReceta = new GestorReceta(this);
        gIn = new GestorIngrediente(this);
        gReIn = new GestorRecetaIngrediente(this);

        gReceta.open();
        gIn.open();
        gReIn.open();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(requestCode == 1){
                String[] ingredient = data.getExtras().getStringArray("ingreLista");
                tvTexto.setText("");
                for (int i = 0; i < ingredient.length; i+=2) {
                    tvTexto.append(ingredient[i]+" "+ingredient[i+1]+"\n");
                    ingredientesReceta.add(new String[]{ingredient[i],ingredient[i+1]});
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gReceta.open();
        gIn.open();
        gReIn.open();
        Log.v("APLICACION", "Resume Alta Open");
    }

    @Override
    protected void onPause() {
        super.onPause();
        gReceta.close();
        gIn.close();
        gReIn.close();
        Log.v("APLICACION", "Resume Alta Close");
    }

    public void btAgregar(View v){
        Receta receta = new Receta();
        receta.setNombre(etReceta.getText().toString());
        receta.setInstrucciones(etInstrucciones.getText().toString());
        receta.setFoto(etFoto.getText().toString());
        if(!receta.getNombre().isEmpty()||!receta.getInstrucciones().isEmpty()) {
            int idReceta = (int) gReceta.insert(receta);
            for (String[] ing : ingredientesReceta) {
                int idIngrediente = (int) gIn.selectIdIngredienteNombre(ing[0]);
                RecetaIngrediente c = new RecetaIngrediente();
                c.setIdreceta(idReceta);
                c.setIdigendiente(idIngrediente);
                c.setCantidad(ing[1]);
                gReIn.insert(c);

            }
        }
        finish();

    }

    public  void lista (View v){
        Intent i=new Intent(this,Lista.class);
        startActivityForResult(i, ING);
    }




   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v("APLICACION", "Result Alta");
        if (resultCode == Activity.RESULT_OK && requestCode == ING) {
            LinearLayout svl;
            svl = (LinearLayout)findViewById(R.id.addll);
            TextView view = new TextView(this);
            et = new EditText(this);
            view.setText(data.getExtras().getString("Nombre"));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            svl.addView(view, params);
            ViewGroup.LayoutParams params2 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            svl.addView(et, params2);

            ingrediente = new Ingrediente();
            ingrediente.setId(data.getExtras().getInt("ID"));
            ingrediente.setNombre(data.getExtras().getString("Nombre"));
            Log.v("Insertamos Ingrediente", ingrediente.toString());
        }
    }*/

    /*
     private void tostada(String i){
        Toast.makeText(this,i,Toast.LENGTH_LONG).show();
    }

     private void view(){
        List<Receta> l = gReceta.select();
        tvTexto.setText("");
        for(Receta p: l){
            tvTexto.append(p.toString());
        }
    }

       private void tostada(int i){
        tostada(i + "");
    }

    public void add(View v){

        //Creamos el objeto receta
        Receta p = new Receta();
        p.setNombre(etReceta.getText().toString().trim());
        p.setInstrucciones(etInstrucciones.getText().toString().trim());
        p.setFoto(etFoto.getText().toString().trim());
        p.setIdcategoria(etIdCatg.getText().toString().trim());

        //Creamos el objeto recetaIngrediente
        RecetaIngrediente rg =new RecetaIngrediente();
        rg.setIdigendiente((int) ingrediente.getId());
        rg.setCantidad(et.getText().toString());

        if(!p.getNombre().isEmpty()) {
            long r = gReceta.insert(p);
            rg.setIdreceta((int) r);
            long ri = gReIn.insert(rg);
            if (r>0 && ri>0) {
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id", r);
                bundle.putLong("id2", ri);
                i.putExtras(bundle);
                setResult(Activity.RESULT_OK, i);
                finish();
            }else {
                tostada("No se ha podido insertar");
            }
        } else{
            tostada("El nombre es obligatorio");
        }
    }
    public void borrar(View v){
        int id = Integer.parseInt(etId.getText().toString());
        int r = gReceta.delete(id);
        tostada(r);
        view();
    }*/



    }
