package com.rubino.add2sqlite.actividades;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.rubino.add2sqlite.R;
import com.rubino.add2sqlite.adaptador.AdaptadorIng;
import com.rubino.add2sqlite.recetario.GestorIngrediente;
import com.rubino.add2sqlite.recetario.Ingrediente;

import java.util.List;

public class Lista extends AppCompatActivity {


    private AdaptadorIng cl;
    //private List<Ingrediente> lista;

    private GestorIngrediente gr;
    private ListView lv;
    private Cursor c;
    private int idIng;
    private String dato;


    private GestorIngrediente ges;
    private List<Ingrediente> lista;
    private LinearLayout linerVerti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        init();
    }



    private void init() {
        gr = new GestorIngrediente(this);



        gr.open();

        //c = gr.getCursor();
        linerVerti = (LinearLayout) findViewById(R.id.linerVertiIngre);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabingre);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btanadirIngredientes(view);
            }
        });
        //lv = (ListView) findViewById(R.id.listView);
        /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v("ID", "" + id + " " + position);
                dato = gr.getRow(c).getNombre();
                idIng = (int) gr.getRow(c).getId();

                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("ID", idIng);
                bundle.putString("Nombre", dato);
                i.putExtras(bundle);
                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });


        cl = new AdaptadorIng(this, c);
        lv.setAdapter(cl);*/

    }


    @Override
    protected void onResume() {
        super.onResume();
        mostrar();
    }

    public void mostrar() {

        lista = gr.selectIngredientes();
        for (Ingrediente ingrediente : lista) {
            LinearLayout horizontal = new LinearLayout(this);
            horizontal.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            CheckBox check = new CheckBox(this);
            TextView tv = new TextView(this);
            EditText et = new EditText(this);
            et.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            check.setText("");
            tv.setText(ingrediente.getNombre());
            horizontal.addView(check);
            horizontal.addView(tv);
            horizontal.addView(et);
            linerVerti.addView(horizontal);
        }
    }


    public void btanadirIngredientes(View v){
        int children = linerVerti.getChildCount();
        String[] ingredientes = new String[0];

        for (int i = 0; i < children; i++) {
            LinearLayout hl = (LinearLayout) linerVerti.getChildAt(i);
            CheckBox cb = (CheckBox) hl.getChildAt(0);
            TextView tv = (TextView) hl.getChildAt(1);
            EditText et = (EditText) hl.getChildAt(2);
            if (cb.isChecked()) {
                String[] masingredientes = new String[ingredientes.length + 2];
                int j = 0;
                if (ingredientes.length != 0) {
                    for (j = 0; j < ingredientes.length; j++) {
                        masingredientes[j] = ingredientes[j];
                    }
                }
                masingredientes[j] = tv.getText().toString();
                masingredientes[j+1] = et.getText().toString();
                ingredientes= masingredientes;
            }
        }
        gr.close();
        System.out.println("luisguapo" + ingredientes.toString());
        this.getIntent().putExtra("ingreLista", ingredientes);
        setResult(RESULT_OK, this.getIntent());
        finish();
    }

    public void detalles(View v){
        Intent i=new Intent(this,Alta.class);
        i.putExtra("ID", idIng);
        i.putExtra("Nombre",dato);
        i.putExtra("Cant",dato);
        startActivity(i);
    }
}
