package com.rubino.add2sqlite.fragmentos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.rubino.add2sqlite.InfoReceta;
import com.rubino.add2sqlite.Principal;
import com.rubino.add2sqlite.R;
import com.rubino.add2sqlite.actividades.Alta;
import com.rubino.add2sqlite.adaptador.Adaptador;
import com.rubino.add2sqlite.recetario.GestorReceta;
import com.rubino.add2sqlite.recetario.GestorRecetaIngrediente;
import com.rubino.add2sqlite.recetario.Ingrediente;
import com.rubino.add2sqlite.recetario.Receta;
import com.rubino.add2sqlite.recetario.RecetaIngrediente;
import com.rubino.add2sqlite.util.Dialogo;
import com.rubino.add2sqlite.util.OnDialogoListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecetaFragment extends Fragment {

    private GestorReceta  gr;
    private GestorRecetaIngrediente gri;
    private Adaptador ad;
    private ListView lv;
    private Cursor c,c2;

    public RecetaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFrag = inflater.inflate(R.layout.fragment_receta, container, false);

        gr = new GestorReceta(this.getActivity());
        gri = new GestorRecetaIngrediente(this.getActivity());

        gr.open();
        gri.open();

        c = gr.getCursor();
        //c2 = gri.getCursor();

        lv = (ListView)viewFrag.findViewById(R.id.lvRecetas);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String instRec = gr.getRow(c).getInstrucciones();
                List<Ingrediente> ingre = gri.getNomIng(id);
                List<RecetaIngrediente> re = gri.getCantidadIng(id);

                ArrayList<String> nIngredientes = new ArrayList<String>();
                ArrayList<String> cantIn = new ArrayList<String>();
                System.out.println("EE OYE"+re.toString());
                System.out.println("EE OYE" + ingre.toString());

                    for (int i=0; i< ingre.size(); i++) {
                        nIngredientes.add(ingre.get(i).getNombre());
                        cantIn.add(re.get(i).getCantidad());
                    }
                    System.out.println(" EE OYE2" + nIngredientes.toString());

                Cursor cursor = (Cursor)lv.getItemAtPosition(position);
                Receta r = gr.getRow(cursor);
                int idre = (int) r.getId();
                detalles(view, instRec,nIngredientes, cantIn,idre);
            }
        });


        ad = new Adaptador(getActivity().getBaseContext(), c);
        registerForContextMenu(lv);
        lv.setAdapter(ad);

        return viewFrag;
    }


   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode){
            case  INFO:
                Intent i = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("Receta", c.get);
                i.putExtras(bundle);
                setResult(Activity.RESULT_OK, i);
                break;
        }

    }*/

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getActivity().getMenuInflater().inflate(R.menu.contextual, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        AdapterView.AdapterContextMenuInfo vistaInfo =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int posicion = vistaInfo.position;
        if(id== R.id.menu_editar){
            editar(getView());
            return true;
        }
        if(id==R.id.menu_borrar){
            borrar(posicion);
            return true;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onResume() {
        gr.open();
        gri.open();
        Log.v("APLICACION", "Resume Principal Open");
        c = gr.getCursor();
        ad = new Adaptador(this.getActivity(), c);
        lv.setAdapter(ad);
        super.onResume();
    }

    @Override
    public void onPause() {
        gr.close();
        Log.v("APLICACION", "Resume Principal Close");
        super.onPause();
    }


    @Override
    public void onDestroy() {
        Log.v("METODO ON DESTROY", "DESTROY");
        super.onDestroy();
    }

    public void borrar(int posicion){
        Cursor cursor = (Cursor)lv.getItemAtPosition(posicion);
        Receta j = gr.getRow(cursor);
        gr.delete(j);
        gri.delete(j.getId());
        ad.changeCursor(gr.getCursor());
        tostada("Receta borrada");
    }

    private void tostada(String i){
        Toast.makeText(this.getContext(), i, Toast.LENGTH_LONG).show();
    }

    public void editar (View v){
        final Receta p = new Receta();
        p.set(c);//
        final OnDialogoListener odl=new OnDialogoListener() {
            @Override
            public void onPreShow(View v) {
                Button b=(Button) v.findViewById(R.id.btEdit);
                b.setVisibility(View.GONE);
                EditText etNombre = (EditText)v.findViewById(R.id.etIngredienteED);
                etNombre.setText(p.getNombre());
                EditText etInstrucciones=(EditText) v.findViewById(R.id.etInstruccionesED);
                etInstrucciones.setText(p.getInstrucciones());
                EditText etFoto=(EditText) v.findViewById(R.id.etFotoED);
                etFoto.setText(p.getFoto());
                EditText etIdCategoria=(EditText) v.findViewById(R.id.etIdCategoriaED);
                etIdCategoria.setText(p.getIdcategoria() + "");

            }

            @Override
            public void onOkSelecter(View v) {
                tostada("Receta Actualizada");
                EditText etNombre = (EditText)v.findViewById(R.id.etIngredienteED);
                EditText etInstrucciones =(EditText) v.findViewById(R.id.etInstruccionesED);
                EditText etFoto =(EditText) v.findViewById(R.id.etFotoED);
                EditText etIdCategoria =(EditText) v.findViewById(R.id.etIdCategoriaED);
                p.setNombre(etNombre.getText().toString());
                p.setInstrucciones(etInstrucciones.getText().toString());
                p.setFoto(etFoto.getText().toString());
                p.setIdcategoria(etIdCategoria.getText().toString());
                int r=gr.update(p);
                //Actualizar cursor
                c=gr.getCursor();
                ad.changeCursor(c);

            }

            @Override
            public void onCancelSelecter(View v) {
                tostada("Cancelado");
            }
        };

        Dialogo d=new Dialogo(this.getActivity(),R.layout.activity_editar_receta,odl);
        d.show();
    }
    public void detalles(View v,String dato, ArrayList<String> ingredientes,ArrayList<String> cantidad,int idre){
        Intent i=new Intent(this.getActivity(),InfoReceta.class);
        i.putExtra("Cursor", dato);
        i.putExtra("Cursor2",ingredientes);
        i.putExtra("Cursor3",cantidad);
        i.putExtra("Cursor4", idre);
        startActivity(i);
    }


}
