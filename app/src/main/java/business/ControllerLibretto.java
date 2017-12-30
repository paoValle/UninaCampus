package business;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entity.Corso;
import entity.Esame;

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

    public HashMap<String, Corso> getEsamiDaSvolgere() {

        HashMap<String, Corso> rimanenti = new HashMap<>(cUser.getCurrentUser().getCorso().getCorsi()); //mi serve per copia!!!
        HashMap<String, Esame> esamih=cUser.getCurrentUser().getLibretto();

        Collection<Esame> esame= esamih.values();
        for(Esame e: esame){
            rimanenti.remove(e.getCorso().getCodice());
        }
        return rimanenti;
    }

    public List<String> getNomeEsamiDaSvolgere() {
        HashMap<String, Corso> exam = getEsamiDaSvolgere();
        List<String> nomeEsamiDaSvolgere = new LinkedList<>();
        for (Map.Entry<String, Corso> entry: exam.entrySet()) {
            nomeEsamiDaSvolgere.add(entry.getValue().getNome());
        }
        return nomeEsamiDaSvolgere;
    }

    public List<String> getIdEsamiDaSvolgere() {

        HashMap<String, Corso> exam = getEsamiDaSvolgere();
        List<String> idEsamiDaSvolgere = new LinkedList<>();
        for (Map.Entry<String, Corso> entry: exam.entrySet()) {
            idEsamiDaSvolgere.add(entry.getKey());
        }
        return idEsamiDaSvolgere;
    }

    public void addEsame(int Voto, String Data,HashMap<String,Corso> esaminonfatti, int indiceSelezionato,List<String> codici){


        Esame e= new Esame();
        e.setData(Data);
        e.setVoto(Voto);
        e.setCorso(esaminonfatti.get(codici.get(indiceSelezionato)));

        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();

        String UID = cUser.getCurrentUser().getUID();

        String element= dbRef.child("utente").child(UID).child("libretto").push().getKey();
        dbRef.child("utente").child(UID).child("libretto").child(element).child("corso").setValue(e.getCorso().getCodice());
        dbRef.child("utente").child(UID).child("libretto").child(element).child("voto").setValue(e.getVoto());
        dbRef.child("utente").child(UID).child("libretto").child(element).child("data").setValue(e.getData());
        dbRef.child("utente").child(UID).child("libretto").child(element).child("codice").setValue(element);
        e.setUID(element);
        cUser.getCurrentUser().getLibretto().put(element,e); //esame creato in locale
        cUser.getCurrentUser().getCorsiScelti().remove(e.getCorso().getCodice());
        cUser.calcolaMedia();
    }

    public void deleteExam(String codice ){
        cUser.getCurrentUser().getLibretto().remove(codice);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = mDatabase.getReference();

        String UID = cUser.getCurrentUser().getUID();

        dbRef.child("utente").child(UID).child("libretto").child(codice).child("data").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).child("corso").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).child("voto").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).child("codice").removeValue();
        dbRef.child("utente").child(UID).child("libretto").child(codice).removeValue();
        cUser.calcolaMedia();
    }
}
