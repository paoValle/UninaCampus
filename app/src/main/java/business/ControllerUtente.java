package business;

import android.provider.ContactsContract;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
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

/**
 * Created by paolo on 01/12/2017.
 */

public class ControllerUtente {

    private UtenteRegistrato user;
    private static ControllerUtente instance;

    public static synchronized ControllerUtente getInstance() {
        if(instance==null) {
            instance=new ControllerUtente();
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

    public void calcolaMedia(){
        ArrayList<Esame> appoggio=new ArrayList<Esame>(user.getLibretto().values());
        int somma=0;
        if(appoggio.size()!=0) { //nel caso di una eliminazione che elimina tutti gli esami non devo calcolare la media, la devo solo azzerare
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
        }
        else{
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

}
