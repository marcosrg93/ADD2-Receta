package com.rubino.add2sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rubino.add2sqlite.actividades.EditarReceta;
import com.rubino.add2sqlite.recetario.GestorReceta;
import com.rubino.add2sqlite.recetario.GestorRecetaIngrediente;
import com.rubino.add2sqlite.recetario.Ingrediente;
import com.rubino.add2sqlite.recetario.Receta;
import com.rubino.add2sqlite.recetario.RecetaIngrediente;

import java.util.ArrayList;
import java.util.List;

public class InfoReceta extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    private GestorReceta gr;
    private GestorRecetaIngrediente gri;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_receta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void  init(){


        final Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        gr = new GestorReceta(this);
        gr.open();

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();
        loadReceta(intent);
        loadIngredientes(intent);

    }


    //Cargamos la imagen en la cabecera
    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(GestorReceta.getDrawable()).centerCrop().into(imageView);
    }

    //Cargamos el texto de la receta
    private void loadReceta(Intent i){
        TextView textView = (TextView) findViewById(R.id.tvDetReceta);
        textView.setText(i.getExtras().getString("Cursor"));
    }

    //Cargamos el texto de los ingredientes
    private void loadIngredientes(Intent i){
        TextView textView = (TextView) findViewById(R.id.tvDetIngredientes);
        ArrayList<String> res = i.getExtras().getStringArrayList("Cursor2");
        ArrayList<String> res2 = i.getExtras().getStringArrayList("Cursor3");

        System.out.println("EE" + res.toString());
        for (int l = 0; l<res.size(); l++){
                textView.append("\n"+res.get(l)+": "+res2.get(l));

        }
    }

    //Metodo para abrir la actividad de editar
    public void editar(View v,int id){
        Intent i=new Intent(this,EditarReceta.class);
        i.putExtra("idReceta",id);
        startActivity(i);
    }



}
