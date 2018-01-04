package com.example.mikel_000.testapplicazione;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Prova extends AppCompatActivity {

    private String[] cosaDaMostrare= new String[]{"Esame","Ingegneria", "Software", "Android", "Test"};
    private String[] mostraLista=new String[]{"ciao","gennaio","esami","universita"};
    private Button btnOpenDialog;
    private Button bntChangeActivity;
    private ListView lv;
    private Spinner sp;
    private CheckBox ch;
    private TextView tw;
    private Dialog d;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle drawerToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova);

        bntChangeActivity=findViewById(R.id.changeActivity);
        sp=findViewById(R.id.idspinner);
        ch=findViewById(R.id.idcheckBox);
        tw=findViewById(R.id.idtextView);
        btnOpenDialog=findViewById(R.id.dialogoButton);
        lv=findViewById(R.id.idLista);

        ArrayAdapter<String> adapterl = new ArrayAdapter<>(this, R.layout.row, mostraLista);
        lv.setAdapter(adapterl);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, cosaDaMostrare);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                tw.setText(selectedItem);
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getBaseContext(), "Long Click on List" , Toast.LENGTH_LONG).show();
                return true;
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Toast.makeText(getBaseContext(), "Click su elemento " + position , Toast.LENGTH_LONG).show();
            }
        });

        btnOpenDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        btnOpenDialog.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(getBaseContext(), "Long Click on Button" , Toast.LENGTH_LONG).show();

                return true;
            }
        });

        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    Toast.makeText(getBaseContext(), "Box Checked" , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "Box not checked" , Toast.LENGTH_LONG).show();
                }

            }
        });


        bntChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewActivity();
            }
        });


        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);
        drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);





    }



    private void openDialog(){



        d=new Dialog(this);
        d.setCancelable(true);
        d.setTitle("Dialog prova");
        d.setContentView(R.layout.dialogprova);
        d.show();
        Button back= d.findViewById(R.id.idBottoneIndietro);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cancel();
            }
        });


    }

    private void openNewActivity(){

        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                //fragmentClass = FirstFragment.class;
                Toast.makeText(getBaseContext(), "Cliccato su ItemMenu1" , Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_second_fragment:
                //fragmentClass = SecondFragment.class;
                Toast.makeText(getBaseContext(), "Cliccato su ItemMenu2" , Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_third_fragment:
                //fragmentClass = ThirdFragment.class;
                Toast.makeText(getBaseContext(), "Cliccato su ItemMenu3" , Toast.LENGTH_LONG).show();
                break;
            default:
                //fragmentClass = FirstFragment.class;
                Toast.makeText(getBaseContext(), "Click non riconosciuto" , Toast.LENGTH_LONG).show();
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }



}
