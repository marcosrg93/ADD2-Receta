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
import android.widget.ListView;
import android.widget.Toast;

import com.rubino.add2sqlite.InfoReceta;
import com.rubino.add2sqlite.R;
import com.rubino.add2sqlite.actividades.Alta;
import com.rubino.add2sqlite.adaptador.AdaptadorIng;
import com.rubino.add2sqlite.recetario.GestorIngrediente;
import com.rubino.add2sqlite.recetario.Ingrediente;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredienteFragment extends Fragment {

    private GestorIngrediente gi;
    private AdaptadorIng adi;
    private ListView lvI;
    private Cursor c;

    public IngredienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFrag = inflater.inflate(R.layout.fragment_ingrediente, container, false);

        gi = new GestorIngrediente(this.getActivity());
        gi.open();
        c = gi.getCursor();

        lvI = (ListView)viewFrag.findViewById(R.id.lvIngredientes);
        adi = new AdaptadorIng(getActivity().getBaseContext(), c);
        lvI.setAdapter(adi);

        return viewFrag;
    }

    @Override
    public void onResume() {
        gi.open();
        Log.v("APLICACION", "Resume Fragmen Ingrediente Open");
        c = gi.getCursor();
        adi = new AdaptadorIng(this.getActivity(), c);
        lvI.setAdapter(adi);
        super.onResume();
    }

    @Override
    public void onPause() {
        gi.close();
        Log.v("APLICACION", "Resume Fragmen Ingrediente Close");
        super.onPause();
    }

}
