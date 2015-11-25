package com.rubino.add2sqlite.adaptador;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubino.add2sqlite.R;
import com.rubino.add2sqlite.recetario.Ingrediente;

/**
 * Created by marco on 22/11/2015.
 */
public class AdaptadorIng extends CursorAdapter{

    public AdaptadorIng(Context co, Cursor cu) {
        super(co, cu, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.item, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1 = (TextView)view.findViewById(R.id.tvItemNomb);
        ImageView iv = (ImageView)view.findViewById(R.id.ivFoto);

        Ingrediente p = new Ingrediente();
        p.set(cursor);
        tv1.setText(p.getNombre());
        iv.setImageResource(R.drawable.avatardef);
    }

}
