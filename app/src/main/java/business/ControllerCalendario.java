package business;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import entity.Corso;
import entity.Dettagli;
import paovalle.uninacampus.UI.HomePage;

/**
 * Created by paolo on 10/12/2017.
 */

public class ControllerCalendario {

    private static ControllerCalendario instance;
    private ControllerUtente cUser;
    private static final int MY_PERMISSIONS_REQUEST_CALENDAR = 364;

    //mappo giorno settimana del corso con il corrispettivo inglese
    HashMap<String, String> converterGiorniSettimana = new HashMap<>();
    HashMap<String, String> primoGiornoSettimanaSemestre = new HashMap<>();

    public static synchronized ControllerCalendario getInstance() {
        if (instance == null) {
            instance = new ControllerCalendario();
        }
        return instance;
    }

    private ControllerCalendario() {
        cUser = ControllerUtente.getInstance();
        converterGiorniSettimana.put("lun", "MO");
        converterGiorniSettimana.put("mar", "TU");
        converterGiorniSettimana.put("mer", "WE");
        converterGiorniSettimana.put("gio", "TH");
        converterGiorniSettimana.put("ven", "FR");
        primoGiornoSettimanaSemestre.put("lun", "2017-09-04");
        primoGiornoSettimanaSemestre.put("mar", "2017-09-05");
        primoGiornoSettimanaSemestre.put("mer", "2017-09-06");
        primoGiornoSettimanaSemestre.put("gio", "2017-09-07");
        primoGiornoSettimanaSemestre.put("ven", "2017-09-01");
    }

    public boolean addCorsoToCalend(HomePage context, Corso c) {
        //permessi
        if ((ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED)) {
            //vedo se vi sono calendari presenti sul dispositivo
            String projection[] = {"_id", "calendar_displayName"};
            Uri calendars;
            calendars = Uri.parse("content://com.android.calendar/calendars");
            ContentResolver contentResolver = context.getContentResolver();
            Cursor managedCursor = contentResolver.query(calendars, projection, null, null, null);
            int calID;
            if (managedCursor.moveToFirst()) {
                int idCol = managedCursor.getColumnIndex(projection[0]);
                calID = Integer.parseInt(managedCursor.getString(idCol));
                managedCursor.close();
                /* calendar local */
                for (Dettagli d : ((Corso) cUser.getCurrentUser().getCorso().getCorsi().get(c.getCodice())).getDettagli()) {
                    String descrizione = "Prof: " + c.getProfessore().getCognome() + " " + c.getProfessore().getNome() + "\nAula:" + d.getAula().getId() + " [Edificio " + d.getAula().getEd().getId() + "]";
                    //starttime e endtime format: yyyy-MM-dd HH:mm
                    String startTime = getPrimoGiornoSettimanaSemestre(d.getGiorno()) + " " + d.getOraInizio();
                    String dayRepeated = getGiornoSettimanaInglese(d.getGiorno());
                    if (dayRepeated == null) {
                        Toast.makeText(context, "ERRORE: Giorno della settimana non riconosciuto!", Toast.LENGTH_LONG).show();
                        return false;
                    } else {
                        addEvents(calID, c.getNome(), startTime, descrizione, dayRepeated, context);
                    }
                }
            } else {
                Toast.makeText(context, "ERRORE: Nessun calendario disponibile sul dispositivo!", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            //Request calendar Permission
            checkCalendarPermission(context);
            return false;
        }
    return true;
}

    public void checkCalendarPermission(final HomePage context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALENDAR)
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
                                        MY_PERMISSIONS_REQUEST_CALENDAR);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.WRITE_CALENDAR, Manifest.permission.READ_CALENDAR},
                        MY_PERMISSIONS_REQUEST_CALENDAR);
            }
        }
    }


    public void addEvents(int calID, String EventName, String Stime, String Description, String dayRepeated, Context cnt) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+2"));
        Date dt = null;
        try {
            dt = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(Stime);

            Calendar beginTime = Calendar.getInstance();
            cal.setTime(dt);

            /*beginTime.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                    cal.get(Calendar.DATE), cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE));*/

            beginTime.set(2017, 8, 1, 8, 0, 0);

            //fine semestre
            Calendar endTime = Calendar.getInstance();
            endTime.set(2017, 11, 22, 23, 0, 0);

            ContentResolver cr = cnt.getContentResolver();
            ContentValues values = new ContentValues();

            //aggiungo eventi per tutto il semestre
            while (beginTime.getTimeInMillis()<endTime.getTimeInMillis()) {

                values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
                //dura 2 ore
                values.put(CalendarContract.Events.DTEND, beginTime.getTimeInMillis() + 2 * 60 * 60 * 1000);
                values.put(CalendarContract.Events.TITLE, EventName);
                values.put(CalendarContract.Events.DESCRIPTION, Description);
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().getID());

                if (ActivityCompat.checkSelfPermission(cnt, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                cr.insert(CalendarContract.Events.CONTENT_URI, values);

                //evento 1 settimana dopo
                beginTime.setTimeInMillis(beginTime.getTimeInMillis()+7*24*60*60*1000);
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getGiornoSettimanaInglese(String it) {
        String en = converterGiorniSettimana.get(it.substring(0, 3).toLowerCase());
        return en;
    }
    //ritorna il primo giorno di settembre di un corso, dato il suo giorno settimanale
    public String getPrimoGiornoSettimanaSemestre(String it) {
        String gg = primoGiornoSettimanaSemestre.get(it.substring(0, 3).toLowerCase());
        return gg;
    }
}
