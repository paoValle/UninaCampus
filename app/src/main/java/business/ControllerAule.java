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

public class ControllerAule {

    private static ControllerAule instance;
    private static ControllerUtente cUser;

    public static synchronized ControllerAule getInstance() {
        if(instance==null) {
            instance=new ControllerAule();
        }
        return instance;
    }

    private ControllerAule() {
        cUser = ControllerUtente.getInstance();
    }

    public String[] getElencoAule() {
        HashSet<String> elencoAule = new HashSet<>();
        Collection<Corso> corsi = cUser.getCurrentUser().getCorso().getCorsi().values();
        for (Corso c : corsi) {
            for (Dettagli d: c.getDettagli()) {
                elencoAule.add(d.getAula().getId());
            }
        }
        return elencoAule.toArray(new String[elencoAule.size()]);
    }

    public String[] getLatLngByIdAula(String idaula) {
        String[] out = new String[2];
        //trovo lat e long aula dal suo codice
        Collection<Corso> corsi = cUser.getCurrentUser().getCorso().getCorsi().values();
        for (Corso c : corsi) {
            for (Dettagli d: c.getDettagli()) {
                if (d.getAula().getId().equals(idaula)) {
                    out[0] = d.getAula().getLat();
                    out[1] = d.getAula().getLng();
                    return out;
                }
            }
        }
        return null;
    }
    public String getPianoByIdAula(String idaula) {
        String[] out = new String[2];
        //trovo lat e long aula dal suo codice
        Collection<Corso> corsi = cUser.getCurrentUser().getCorso().getCorsi().values();
        for (Corso c : corsi) {
            for (Dettagli d: c.getDettagli()) {
                if (d.getAula().getId().equals(idaula)) {
                    return d.getAula().getPiano();
                }
            }
        }
        return null;
    }
}
