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
import com.rubino.add2sqlite.recetario.GestorReceta;
import com.rubino.add2sqlite.recetario.Receta;

import java.util.List;

public class Alta2 extends AppCompatActivity {

    private TextView tvTexto;
    private GestorReceta gestor;
    private EditText etReceta;
    private EditText etInstrucciones;
    private EditText etFoto;
    private EditText etIdCatg;
    private EditText etId;
    private Button btAdd;

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
        this.tvTexto = (TextView) findViewById(R.id.tvTexto);
        gestor = new GestorReceta(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gestor.open();
        Log.v("APLICACION", "Resume Alta Open");
    }

    @Override
    protected void onPause() {
        super.onPause();
        gestor.close();
        Log.v("APLICACION", "Resume Alta Close");
    }

    public void add(View v){
        Receta p = new Receta();
        p.setNombre(etReceta.getText().toString().trim());
        p.setInstrucciones(etInstrucciones.getText().toString().trim());
        p.setFoto(etFoto.getText().toString().trim());
        p.setIdcategoria(etIdCatg.getText().toString().trim());
        if(!p.getNombre().isEmpty()) {
            long r = gestor.insert(p);
            if (r>0) {
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putLong("id", r);
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

    private void tostada(int i){
        tostada(i + "");
    }
    private void tostada(String i){
        Toast.makeText(this,i,Toast.LENGTH_LONG).show();
    }


    private void view(){
        List<Receta> l = gestor.select();
        tvTexto.setText("");
        for(Receta p: l){
            tvTexto.append(p.toString());
        }
    }

    public void borrar(View v){
        int id = Integer.parseInt(etId.getText().toString());
        int r = gestor.delete(id);
        tostada(r);
        view();
    }


}