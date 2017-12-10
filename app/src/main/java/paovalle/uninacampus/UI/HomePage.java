package paovalle.uninacampus.UI;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import business.ControllerAule;
import business.ControllerLibretto;
import business.ControllerUtente;
import entity.Corso;
import paovalle.uninacampus.R;

public class HomePage extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private Dialog dialogCorsiSeguiti;
    private Dialog dialogGoToAule;
    //la usero per ricavare l'id del corso selezionato
    private List idCorsiSeguiti = new LinkedList<>();
    private String[] idSceltaCorsi;
    private List<Integer> posIdCorsiScelti;

    private ControllerUtente cUser;
    private ControllerLibretto cLibretto;
    private ControllerAule cAule;
    int item_selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        cUser = ControllerUtente.getInstance();
        cLibretto = ControllerLibretto.getInstance();
        cAule = ControllerAule.getInstance();

        posIdCorsiScelti = new LinkedList<>();


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
                if(item_selected != -1){

                }
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

        Button modificaElencoCorsi = findViewById(R.id.modificaElencoCorsi);
        modificaElencoCorsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSceltaCorsiSeguiti();
            }
        });
    }

    private void goToMaps(String id) {
        String[] pos = cAule.getLatLngByIdAula(id);
        if (pos==null) {
            Toast.makeText(getApplicationContext(), "Lat. e long. sconosciute!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, MapsMarkerActivity.class);
            intent.putExtra("IDAULA", id);
            intent.putExtra("LAT", pos[0]);
            intent.putExtra("LNG", pos[1]);
            intent.putExtra("PIANO", cAule.getPianoByIdAula(id));
            startActivity(intent);
        }
    }

    private void showDialogSceltaCorsiSeguiti() {
        //mostro dialog con possibili corsi da scegliere
        dialogCorsiSeguiti = new Dialog(this);
        dialogCorsiSeguiti.setTitle("Quali corsi segui?");
        dialogCorsiSeguiti.setContentView(R.layout.dialog_sceltacorsiseguiti);

        Button dismissDialog = dialogCorsiSeguiti.findViewById(R.id.sceltaCorsiSeguitiCancel);
        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCorsiSeguiti.dismiss();
            }
        });
        final Button okDialog = dialogCorsiSeguiti.findViewById(R.id.sceltaCorsiSeguitiOk);
        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rimuovo attuali corsi seguiti
                cUser.deleteTuttiCorsiSeguiti();
                System.out.println("Corsi da salvare come scelti:");
                for (Integer pos : posIdCorsiScelti) {
                    cUser.addCorsoSeguitoById(idSceltaCorsi[pos]);
                }
                dialogCorsiSeguiti.dismiss();
                showCorsiSeguiti();
            }
        });

        //GridView headerGridView = d.findViewById(R.id.dialogOrariGridHeader);
        ListView lvSceltaCorsi = dialogCorsiSeguiti.findViewById(R.id.listaSceltaCorsi);
        //preparo contenuto tabella
        HashMap<String, Corso> exam = cLibretto.getNomeEsamiDaSvolgere();
        String[] esaminomi=new String[exam.size()];
        idSceltaCorsi = new String[exam.size()];
        int i = 0;
        for (Map.Entry<String, Corso> entry: exam.entrySet()) {
            esaminomi[i] = entry.getValue().getNome();
            idSceltaCorsi[i++]=entry.getKey();
        }
        //resetto corsi scelti
        posIdCorsiScelti = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, esaminomi);
        lvSceltaCorsi.setAdapter(adapter);
        lvSceltaCorsi.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //scopro quali checkare poiche attualmente seguiti
        for (int j=0;j<idSceltaCorsi.length;j++) {
            if (cUser.getListIdCorsiSeguitiCurrentUser().contains(idSceltaCorsi[j])) {
                lvSceltaCorsi.setItemChecked(j, true);
                posIdCorsiScelti.add(j);
            }
        }
        lvSceltaCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View arg1, int arg2, long arg3)
            {
                AppCompatCheckedTextView checkBox = (AppCompatCheckedTextView) arg1;
                Log.i("CHECK",checkBox.isChecked()+""+checkBox.getText().toString()+ " (pos:"+arg2+")");
                if (checkBox.isChecked()) {
                    posIdCorsiScelti.add(Integer.valueOf(arg2));
                } else {

                    posIdCorsiScelti.remove(Integer.valueOf(arg2));
                }
            }
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {  }
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        dialogCorsiSeguiti.show();
    }

    private void showDialogAulaCalend() {
        //mostro dialog con possibili corsi da scegliere
        dialogCorsiSeguiti = new Dialog(this);
        dialogCorsiSeguiti.setTitle("Quali corsi segui?");
        dialogCorsiSeguiti.setContentView(R.layout.dialog_sceltacorsiseguiti);

        Button dismissDialog = dialogCorsiSeguiti.findViewById(R.id.sceltaCorsiSeguitiCancel);
        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCorsiSeguiti.dismiss();
            }
        });
        final Button okDialog = dialogCorsiSeguiti.findViewById(R.id.sceltaCorsiSeguitiOk);
        okDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (posIdCorsiScelti.size()>0) {
                    //aggiungo corsi al calendario
                    //TODO: trovare selezionati e metterli in calendario
                    for (Integer pos : posIdCorsiScelti) {
                        cUser.addCorsoToCalend(HomePage.this, idSceltaCorsi[pos]);
                    }
                    dialogCorsiSeguiti.dismiss();
                    Toast.makeText(getBaseContext(), "Eventi aggiunti al calendario!" , Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Nessun corso selezionato!" , Toast.LENGTH_SHORT).show();
                }


            }
        });

        //GridView headerGridView = d.findViewById(R.id.dialogOrariGridHeader);
        ListView lvSceltaCorsi = dialogCorsiSeguiti.findViewById(R.id.listaSceltaCorsi);
        //preparo contenuto tabella
        HashMap<String, Corso> exam = cLibretto.getNomeEsamiDaSvolgere();
        String[] esaminomi=new String[exam.size()];
        idSceltaCorsi = new String[exam.size()];
        int i = 0;
        for (Map.Entry<String, Corso> entry: exam.entrySet()) {
            esaminomi[i] = entry.getValue().getNome();
            idSceltaCorsi[i++]=entry.getKey();
        }
        //resetto corsi scelti
        posIdCorsiScelti = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, esaminomi);
        lvSceltaCorsi.setAdapter(adapter);

        lvSceltaCorsi.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvSceltaCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View arg1, int arg2, long arg3)
            {
                AppCompatCheckedTextView checkBox = (AppCompatCheckedTextView) arg1;
                Log.i("CHECK",checkBox.isChecked()+""+checkBox.getText().toString()+ " (pos:"+arg2+")");
                if (checkBox.isChecked()) {
                    posIdCorsiScelti.add(Integer.valueOf(arg2));
                } else {

                    posIdCorsiScelti.remove(Integer.valueOf(arg2));
                }
            }
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {  }
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        dialogCorsiSeguiti.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        showCorsiSeguiti();
        //setting up current user infos
        ((TextView)findViewById(R.id.textFName)).setText(cUser.getCurrentUser().getNome());
        ((TextView)findViewById(R.id.textLName)).setText(cUser.getCurrentUser().getCognome());
        ((TextView)findViewById(R.id.textMean)).setText(Double.valueOf(cUser.getCurrentUser().getMedia()).toString());
        ((TextView)findViewById(R.id.textCDL)).setText(cUser.getCurrentUser().getCorso().getNome());
    }

    private void showCorsiSeguiti() {
        item_selected = -1;
        //mostro elenco corsi seguiti
        ListView lv = findViewById(R.id.elencoCorsiSeguiti);
        lv.setAdapter(null);
        List<String> corsi = cUser.getListCorsiSeguitiCurrentUser();
        idCorsiSeguiti = cUser.getListIdCorsiSeguitiCurrentUser();

        ArrayAdapter<String> adapter= new ArrayAdapter<>(this, R.layout.row, corsi);
        lv.setAdapter(adapter);

        //aggiungo listener on item selected
        lv.setClickable(true);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //vuole deselezionare?
                if (item_selected == i) {
                    item_selected = -1;
                    ((ListView)findViewById(R.id.elencoCorsiSeguiti)).clearChoices();
                } else {
                    item_selected = i;
                }
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
                goToQRScanner();
                break;
            case R.id.nav_second_fragment:
                showDialogAulaCalend();
                break;
            case R.id.nav_third_fragment:
                showGoToAule();
                break;
            case R.id.nav_forth_fragment:
                goToUpload();
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
        //gli passo id del corso (se selezionato)
        Intent intent = new Intent(this, MailProf.class);
        try {
            intent.putExtra("IDCORSO", (String) idCorsiSeguiti.get(item_selected));
        } catch (Exception ex) {
            //la lista è vuota
            intent.putExtra("IDCORSO", "");
        }
        startActivity(intent);

    }

    private void goToQRScanner(){
        Intent intent = new Intent(this, QRScannerActivity.class);
        startActivity(intent);

    }

    private void goToRecorder(){
        if(item_selected != -1) {
            Intent intent = new Intent(this, RecorderActivity.class);
            intent.putExtra("IDCORSO", (String) idCorsiSeguiti.get(item_selected));
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Nessun corso selezionato!" , Toast.LENGTH_SHORT).show();

        }

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

    private void goToUpload() {
        Intent recorderManager = new Intent(getApplicationContext(), RecorderManager.class);
        startActivity(recorderManager);
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

    private void showGoToAule() {
        dialogGoToAule = new Dialog(this);
        dialogGoToAule.setContentView(R.layout.dialogo_aula_gps);
        dialogGoToAule.setTitle("A quale aula vuoi andare?");

        String[] elencoAule = cAule.getElencoAule();
        Spinner s= dialogGoToAule.findViewById(R.id.elencoAule);

        for (String es : elencoAule) {
            System.out.println(es);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, elencoAule);

        s.setAdapter(adapter);

        dialogGoToAule.show();

        //mappa aule
        Button btnGoToAula = dialogGoToAule.findViewById(R.id.idGoToAula);
        btnGoToAula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToMaps(((Spinner)dialogGoToAule.findViewById(R.id.elencoAule)).getSelectedItem().toString());
                dialogGoToAule.dismiss();
            }
        });
    }



}
