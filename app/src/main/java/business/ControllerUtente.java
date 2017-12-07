package business;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entity.Corso;
import entity.Dettagli;
import entity.Esame;
import entity.UtenteRegistrato;
import paovalle.uninacampus.UI.HomePage;

/**
 * Created by paolo on 01/12/2017.
 */

public class ControllerUtente {

    private UtenteRegistrato user;
    private static ControllerUtente instance;
    private static final int MY_PERMISSIONS_REQUEST_CALENDAR = 364;

    public static synchronized ControllerUtente getInstance() {
        if (instance == null) {
            instance = new ControllerUtente();
        }
        return instance;
    }

    public void setCurrentUser(UtenteRegistrato u) {
        this.user = u;
    }

    public UtenteRegistrato createUser(String UID) {
        user = new UtenteRegistrato(UID);
        return user;
    }

    public UtenteRegistrato getCurrentUser() {
        return this.user;
    }

    public HashMap getCurrentUserCorsi() {
        return user.getCorso().getCorsi();
    }

    public HashMap getCurrentUserEsami() {
        return user.getLibretto();
    }

    public List getListCorsiSeguitiCurrentUser() {
        List<String> corsi = new LinkedList<>();
        Iterator it = user.getCorsiScelti().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            corsi.add(((Corso) pair.getValue()).getNome());
        }
        return corsi;
    }

    public List getListIdCorsiSeguitiCurrentUser() {
        List<String> idCorsi = new LinkedList<>();
        Iterator it = user.getCorsiScelti().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            idCorsi.add(pair.getKey().toString());
        }
        return idCorsi;
    }

    public void calcolaMedia() {
        ArrayList<Esame> appoggio = new ArrayList<Esame>(user.getLibretto().values());
        int somma = 0;
        if (appoggio.size() != 0) { //nel caso di una eliminazione che elimina tutti gli esami non devo calcolare la media, la devo solo azzerare
            for (Esame e : appoggio) {

                somma = somma + e.getVoto();
                if (e.getVoto() == 31) {
                    somma = somma - 1; //devo togliere il +1 del 31. la lode non fa media
                }

            }
            double media = (double) (somma / appoggio.size());
            user.setMedia(media);
            //aggiorna la media su database
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference dbRef = mDatabase.getReference();
            String UID = getCurrentUser().getUID();
            dbRef.child("utente").child(UID).child("mean").setValue(Double.toString(media));
        } else {
            user.setMedia(0);
        }
    }

    public void deleteTuttiCorsiSeguiti() {
        //locale
        user.getCorsiScelti().clear();
        //online
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();
        dbRef.child("utente").child(user.getUID()).child("corsiScelti").removeValue();
    }

    public void addCorsoSeguitoById(String s) {
        //locale
        user.getCorsiScelti().put(s, user.getCorso().getCorsi().get(s));
        //online
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();
        String key = dbRef.child("utente").child(user.getUID()).child("corsiScelti").push().getKey();
        dbRef.child("utente").child(user.getUID()).child("corsiScelti").child(key).setValue(s);
    }

    public void addCorsoToCalend(HomePage context, String idCorsi) {
        Corso c = (Corso) user.getCorso().getCorsi().get(idCorsi);
        System.out.println("Aggiungo al calendario: " + c.getNome());

                        /* calendar */
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            for (Dettagli d : ((Corso)user.getCorso().getCorsi().get(c.getCodice())).getDettagli()) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(2017, 9, 1, d.getStartHour(), d.getStartMin(), 0);
                System.out.println("Aggiungo evento il giorno:");
                long startMillis = calendar.getTimeInMillis();
                //durata: 2 ore
                long endMillis = startMillis + 2 * 60 * 60 * 1000;

                ContentResolver cr = context.getContentResolver();

                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.CALENDAR_ID, 0);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, c.getNome());
                values.put(CalendarContract.Events.DESCRIPTION, c.getNome()+" del prof. "+c.getProfessore().getCognome());
                values.put(CalendarContract.Events.EVENT_TIMEZONE, d.getAula().getId()+" ["+d.getAula().getEd().getIndirizzo()+"]");
                values.put(CalendarContract.Events.RRULE, "FREQ=WEEKLY;BYDAY=TU;UNTIL=20172012");
                Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

                // get the event ID that is the last element in the Uri
                long eventID = Long.parseLong(uri.getLastPathSegment());
            }
        } else {
            //Request Location Permission
            checkCalendarPermission(context);
        }


    }

    private void checkCalendarPermission(final HomePage context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED
                &&  ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.WRITE_CALENDAR)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(context)
                        .setTitle("Accesso al calendario richiesto")
                        .setMessage("Questa funzionalità richiede l'accesso al calendario. Se vuoi utilizzarla, accetta la richiesta di permesso. ")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(context,
                                        new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR},
                                        MY_PERMISSIONS_REQUEST_CALENDAR );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CALENDAR );
            }
        }
    }

}
