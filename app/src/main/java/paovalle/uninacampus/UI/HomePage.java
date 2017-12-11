package paovalle.uninacampus.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import business.ControllerAule;
import business.ControllerLibretto;
import business.ControllerUtente;
import paovalle.uninacampus.BuildConfig;
import paovalle.uninacampus.R;

public class HomePage extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private Dialog dialogCorsiSeguiti;
    private Dialog dialogGoToAule;
    //la usero per ricavare l'id del corso selezionato
    private List idCorsiSeguiti = new LinkedList<>();
    private List idSceltaCorsi = new LinkedList<>();
    private List<Integer> posIdCorsiScelti;

    private ControllerUtente cUser;
    private ControllerLibretto cLibretto;
    private ControllerAule cAule;
    int item_selected = -1;

    private File pickedProfileImageFile;
    private Uri imageUri;
    private Bitmap pictureBitmap = null;

    private final int SINGLE_SELECT_GALLERY = 2, RESULT_CROP = 400;

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

        //cambio immagine profilo
        findViewById(R.id.profileImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingleGetImageFromGallery();
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

        //setto immagine se presente online
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference();
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object imgURL = dataSnapshot.child("utente").child(cUser.getCurrentUser().getUID()).child("profileImage").getValue();
                try {
                    if (imgURL != null) {//non presente
                        imageUri = Uri.parse(imgURL.toString());
                        try {
                            InputStream is = HomePage.this.getContentResolver().openInputStream(imageUri);
                            if (is != null) {
                                pictureBitmap = BitmapFactory.decodeStream(is);
                            }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (pictureBitmap != null) {
                            ImageView myImage = findViewById(R.id.profileImage);
                            myImage.setImageBitmap(pictureBitmap);
                        }
                    }
                } catch (SecurityException sE) {
                    //imposterà l'immagine di default
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
                for (Integer pos : posIdCorsiScelti) {
                    cUser.addCorsoSeguitoById((String)idSceltaCorsi.get(pos));
                }
                dialogCorsiSeguiti.dismiss();
                showCorsiSeguiti();
            }
        });

        //GridView headerGridView = d.findViewById(R.id.dialogOrariGridHeader);
        ListView lvSceltaCorsi = dialogCorsiSeguiti.findViewById(R.id.listaSceltaCorsi);
        //preparo contenuto tabella
        List<String> esaminomi = cLibretto.getNomeEsamiDaSvolgere();
        idSceltaCorsi = cLibretto.getIdEsamiDaSvolgere();
        //resetto corsi scelti
        posIdCorsiScelti = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, esaminomi);
        lvSceltaCorsi.setAdapter(adapter);
        lvSceltaCorsi.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        //scopro quali checkare poiche attualmente seguiti
        for (int j=0;j<idSceltaCorsi.size();j++) {
            if (cUser.getListIdCorsiSeguitiCurrentUser().contains(idSceltaCorsi.get(j))) {
                lvSceltaCorsi.setItemChecked(j, true);
                posIdCorsiScelti.add(j);
            }
        }
        lvSceltaCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View arg1, int arg2, long arg3)
            {
                AppCompatCheckedTextView checkBox = (AppCompatCheckedTextView) arg1;
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
        dialogCorsiSeguiti.setTitle("Aggiungi al calendario:");
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
                        cUser.addCorsoToCalend(HomePage.this, (String)idSceltaCorsi.get(pos));
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
        List<String> corsiSeguiti=cUser.getListCorsiSeguitiCurrentUser();
        idSceltaCorsi = cUser.getListIdCorsiSeguitiCurrentUser();
        //resetto corsi scelti
        posIdCorsiScelti = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, corsiSeguiti);
        lvSceltaCorsi.setAdapter(adapter);

        lvSceltaCorsi.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lvSceltaCorsi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapter, View arg1, int arg2, long arg3)
            {
                AppCompatCheckedTextView checkBox = (AppCompatCheckedTextView) arg1;
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

    // single image pick function
    private void SingleGetImageFromGallery() {
        pickedProfileImageFile = new File(Environment.getExternalStorageDirectory(), "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        if (android.os.Build.VERSION.SDK_INT > 23) {
            imageUri = FileProvider.getUriForFile(HomePage.this, BuildConfig.APPLICATION_ID + ".provider", pickedProfileImageFile);
        } else {
            imageUri = Uri.fromFile(pickedProfileImageFile);
        }
        Intent GalIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent, "Select Image From Gallery"), SINGLE_SELECT_GALLERY);
    }

    @SuppressLint("NewApi")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SINGLE_SELECT_GALLERY) {// One Image result
                imageUri = data.getData();
                try {
                    InputStream is = this.getContentResolver().openInputStream(imageUri);
                    if (is != null) {
                        pictureBitmap = BitmapFactory.decodeStream(is);
                        imageUri = getImageUri(this, pictureBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ImageView myImage = findViewById(R.id.profileImage);
                myImage.setImageBitmap(pictureBitmap);

                FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = mDatabase.getReference();
                dbRef.child("utente").child(cUser.getCurrentUser().getUID()).child("profileImage").setValue(imageUri.toString());
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //Scrop function for max SDK 23
    private void ImageCrop() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 3);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(pickedProfileImageFile));
        startActivityForResult(intent, RESULT_CROP);
    }
}
