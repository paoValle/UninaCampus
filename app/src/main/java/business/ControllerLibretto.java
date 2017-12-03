package business;

import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import entity.Corso;
import entity.Esame;
import entity.UtenteRegistrato;
import paovalle.uninacampus.R;

/**
 * Created by paolo on 01/12/2017.
 */

public class ControllerLibretto {

    private ControllerUtente cUser;
    private static ControllerLibretto instance;

    public static synchronized ControllerLibretto getInstance() {
        if(instance==null) {
            instance=new ControllerLibretto();
        }
        return instance;
    }


    private ControllerLibretto() {
        cUser = ControllerUtente.getInstance();
    }

    public HashMap<String, Esame> getEsamiSvolti() {
        return ControllerUtente.getInstance().getCurrentUser().getLibretto();
    }

    public HashMap<String, Corso> getNomeEsamiDaSvolgere() {

        HashMap<String, Corso> rimanenti = cUser.getCurrentUser().getCorso().getCorsi();
        HashMap<String, Esame> esamih=cUser.getCurrentUser().getLibretto();

        Collection<Esame> esame= esamih.values();
        for(Esame e: esame){
            rimanenti.remove(e.getCorso().getCodice());
        }
   /*
        for(Map.Entry<String, Esame> entry: esamih.entrySet()){

            rimanenti.remove(entry.getKey());
        }


        Collection<Esame> esami = cUser.getCurrentUser().getLibretto().values();


        String[] out = new String[rimanenti.size()];
        int i = 0;
        for (Corso e : rimanenti.values()) {
            out[i++] = e.getNome();
        }*/
        return rimanenti;
    }

    public void addEsame(int Voto, String Data,HashMap<String,Corso> esaminonfatti, int indiceSelezionato,String[]codici){


        Esame e= new Esame();
        Log.d("addesame", "Devo inserire la data "+ Data );
        e.setData(Data);
        Log.d("addesame", "data settata "+ e.getData() );
        e.setVoto(Voto);
        e.setCorso(esaminonfatti.get(codici[indiceSelezionato]));


        FirebaseAuth auth = FirebaseAuth.getInstance();

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();

        String UID = cUser.getCurrentUser().getUID();

        String element= dbRef.child("utente").child(UID).child("libretto").push().getKey();
        dbRef.child("utente").child(UID).child("libretto").child(element).child("corso").setValue(e.getCorso().getCodice());
        dbRef.child("utente").child(UID).child("libretto").child(element).child("voto").setValue(e.getVoto());
        dbRef.child("utente").child(UID).child("libretto").child(element).child("data").setValue(e.getData());
        dbRef.child("utente").child(UID).child("libretto").child(element).child("codice").setValue(element);
        e.setUID(element);
        Log.d("addesame", "esameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: data "+ e.getData()+ " codice "+ e.getUID() +" corso " + e.getCorso().getCodice());
        cUser.getCurrentUser().getLibretto().put(element,e); //esame creato in locale
        cUser.calcolaMedia();
    }

    public void deleteExam(int pos,String codice ){
        cUser.getCurrentUser().getLibretto().remove(codice);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();

        String UID = cUser.getCurrentUser().getUID();
        Log.d("prova", UID + " "+ codice);

        dbRef.child("utente").child(UID).child("libretto").child(codice).child("data").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).child("corso").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).child("voto").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).child("codice").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).removeValue();
        cUser.calcolaMedia();
    }
}
