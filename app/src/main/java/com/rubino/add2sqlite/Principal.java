package com.rubino.add2sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

import com.rubino.add2sqlite.actividades.Alta;
import com.rubino.add2sqlite.actividades.AltaIngrediente;
import com.rubino.add2sqlite.fragmentos.Adapter;
import com.rubino.add2sqlite.fragmentos.IngredienteFragment;
import com.rubino.add2sqlite.fragmentos.RecetaFragment;


public class Principal extends AppCompatActivity {
    /***************************************************/
    private final static int ALTA = 1;
    private final static int ALTAIG = 2;
    private final static int INFO = 3;

    /***************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar(view);
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public void insertar(View v) {
        Intent i = new Intent(this, Alta.class);
        startActivityForResult(i, ALTA);
    }

    /********************************************************************************************************************************/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        View v = getCurrentFocus();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //detalles(v);
            return true;
        }
        if (id == R.id.insertig) {
            insertarIg(v);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertarIg(View v) {
        Intent i = new Intent(this, AltaIngrediente.class);
        startActivityForResult(i, ALTAIG);
    }


    /********************************************* TAB HOST **********************************************************************/
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new RecetaFragment(), "Recetas");
        adapter.addFragment(new IngredienteFragment(), "Ingredientes");
        viewPager.setAdapter(adapter);
    }
}