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
import com.rubino.add2sqlite.recetario.Ingrediente;

import java.util.List;

public class AltaIngrediente extends AppCompatActivity {

    private TextView tvTexto;
    private GestorIngrediente gestor;
    private EditText etIngrediente;
    private Button btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_ingrediente);
        init();
    }

    private void init(){
        this.btAdd = (Button) findViewById(R.id.btAddIg);
        this.etIngrediente = (EditText) findViewById(R.id.etIngredienteED);
        this.tvTexto = (TextView) findViewById(R.id.tvTexto);
        gestor = new GestorIngrediente(this);
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

    public void addIg(View v){
        Ingrediente p = new Ingrediente();
        p.setNombre(etIngrediente.getText().toString().trim());
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
        List<Ingrediente> l = gestor.select();
        tvTexto.setText("");
        for(Ingrediente p: l){
            tvTexto.append(p.toString());
        }
    }



    public void editar(View v){
        Ingrediente p = new Ingrediente();
        p.setNombre(etIngrediente.getText().toString());
        int r = gestor.update(p);
        tostada(r);
        view();
    }
}