package paovalle.uninacampus.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import business.ControllerUtente;
import paovalle.uninacampus.R;

public class HomePage extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    //la usero per ricavare l'id del corso selezionato
    List idCorsiSeguiti = new LinkedList<>();

    ControllerUtente cUser;
    int item_selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cUser = ControllerUtente.getInstance();

        //setting up current user infos
        ((TextView)findViewById(R.id.textFName)).setText(cUser.getCurrentUser().getNome());
        ((TextView)findViewById(R.id.textLName)).setText(cUser.getCurrentUser().getCognome());
        ((TextView)findViewById(R.id.textMean)).setText(Double.valueOf(cUser.getCurrentUser().getMedia()).toString());
        ((TextView)findViewById(R.id.textCDL)).setText(cUser.getCurrentUser().getCorso().getNome());

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
         drawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        // Find our drawer view
        NavigationView nvDrawer = findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

        //if not null, this will turn menu icon to grey
        ((NavigationView)findViewById(R.id.nvView)).setItemIconTintList(null);

        Button recBtn = findViewById(R.id.recorderBtn);
        recBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRecorder();
            }
        });

        Button mailBtn = findViewById(R.id.mailBtn);
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
        ListView lv = findViewById(R.id.elencoCorsiSeguiti);
        lv.setAdapter(null);
        List<String> corsi = new LinkedList<>();
        corsi = cUser.getListCorsiSeguitiCurrentUser();
        idCorsiSeguiti = cUser.getListIdCorsiSeguitiCurrentUser();

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.row,corsi);
        lv.setAdapter(adapter);

        //aggiungo listener on item selected
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item_selected = i;
                System.out.println("selected=>"+i);
                Log.w("selected", Integer.valueOf(i).toString());
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
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
                goToMail();
                break;
            case R.id.nav_sixth_fragment:
                goToLibretto();
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
        //se item non selezionato, sel="" altrimenti sara il codice del corso
        System.out.println("Gli passero => " +idCorsiSeguiti.get(item_selected) );
        Intent intent = new Intent(this, MailProf.class);
        intent.putExtra("IDCORSO", (String)idCorsiSeguiti.get(item_selected));
        startActivity(intent);

    }

    private void goToRecorder(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        Intent intent = new Intent(this,RecorderActivity.class);
        startActivity(intent);

    }

    private void goToLibretto(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
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

    //ascolta pressione tasto back => chiede per logout
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Sei sicuro di volerti disconnettere?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //logout
                            FirebaseAuth.getInstance().signOut();
                            goToLogin();
                        }})
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //resta in home
                        }}).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goToLogin() {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

}
