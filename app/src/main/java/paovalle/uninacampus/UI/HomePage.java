package paovalle.uninacampus.UI;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entity.Corso;
import entity.UtenteRegistrato;
import paovalle.uninacampus.R;

public class HomePage extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private UtenteRegistrato user;
    //la usero per ricavare l'id del corso selezionato
    LinkedList<String> idCorsiSeguiti = new LinkedList<>();
    int item_selected = -1;

    private Button recBtn;
    private Button mailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //setting up user data
        Intent i = getIntent();
        user = UtenteRegistrato.getIstanza();
        ((TextView)findViewById(R.id.textFName)).setText(user.getNome());
        ((TextView)findViewById(R.id.textLName)).setText(user.getCognome());
        ((TextView)findViewById(R.id.textMean)).setText(new Double(user.getMedia()).toString());
        ((TextView)findViewById(R.id.textCDL)).setText(user.getCorso().getNome());

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set a Toolbar to replace the ActionBar.
                toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        //if not null, this will turn menu icon to grey
        ((NavigationView)findViewById(R.id.nvView)).setItemIconTintList(null);

        recBtn = (Button) findViewById(R.id.recorderBtn);
        recBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRecorder();
            }
        });

        mailBtn = (Button) findViewById(R.id.mailBtn);
        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMail();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //mostro elenco corsi seguiti
        ListView lv = (ListView) findViewById(R.id.elencoCorsiSeguiti);
        lv.setAdapter(null);
        List<String> corsi = new LinkedList<>();
        Iterator it = user.getCorsiScelti().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            corsi.add(((Corso) pair.getValue()).getNome());
            idCorsiSeguiti.add((String)pair.getKey());
            it.remove(); // avoids a ConcurrentModificationException
        }
        System.out.println("RESUMED!");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.row,corsi);
        lv.setAdapter(adapter);

        //aggiungo listener on item selected
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item_selected = i;
                System.out.println(i);
            }
        });
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
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:

                break;
            case R.id.nav_second_fragment:

                break;
            case R.id.nav_third_fragment:

                break;
            case R.id.nav_fifth_fragment:
<<<<<<< refs/remotes/origin/master
                attemptSendMail();
=======
                Log.d("prova","Sto clickando msg" );
                goToMail();
>>>>>>> Fix elenco corsi seguiti
                break;
            case R.id.nav_sixth_fragment:
                Log.d("prova","HocliccatoLibrettooooo" );
                vediLibretto();
                break;

            default:


        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void goToMail(){
        ///prendo corso selezionato
        String sel = (String)((ListView)findViewById(R.id.elencoCorsiSeguiti)).getSelectedItem();
        System.out.println(sel);
        if (item_selected==-1) {
            Toast.makeText(getApplicationContext(), "Selezionare prima un corso!", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("Hai cliccato su: " + idCorsiSeguiti.get(item_selected));
            Intent intent = new Intent(this, MailProf.class);
            startActivity(intent);
        }

    }

    private void goToRecorder(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        /*Intent intent = new Intent(this,RecorderActivity.class);
        startActivity(intent);*/

    }

    private void vediLibretto(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        Log.d("prova","Sono nella funzione" );
        Intent intent = new Intent(this,LibrettoActivity.class);
        startActivity(intent);

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
