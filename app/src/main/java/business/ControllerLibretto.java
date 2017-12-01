package business;

import android.util.Log;
import android.widget.Spinner;

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

    public ArrayList<Esame> getArrayListEsamiSvolti() {
        return new ArrayList<Esame>(ControllerUtente.getInstance().getCurrentUser().getLibretto().values());
    }

    public HashMap<String, Corso> getArrayNomeEsamiDaSvolgere() {
        HashMap<String, Corso> rimanenti = cUser.getCurrentUser().getCorso().getCorsi();
        HashMap<String, Esame> esamih=cUser.getCurrentUser().getLibretto();

        for(Map.Entry<String, Esame> entry: esamih.entrySet()){
            rimanenti.remove(entry.getKey());
        }

        /*
        Collection<Esame> esami = cUser.getCurrentUser().getLibretto().values();
        for(Esame e: esami){
            rimanenti.remove(e.getCorso().getCodice());
        }

        String[] out = new String[rimanenti.size()];
        int i = 0;
        for (Corso e : rimanenti.values()) {
            out[i++] = e.getNome();
        }*/
        return rimanenti;
    }

    public void addEsame(int Voto, String Data,HashMap<String,Corso> esaminonfatti, int indiceSelezionato,String[]codici){

        Esame e= new Esame();
        e.setData(Data);
        e.setVoto(Voto);
        e.setCorso(esaminonfatti.get(codici[indiceSelezionato]));
        cUser.getCurrentUser().getLibretto().put(codici[indiceSelezionato],e);

        Log.d("prova", "voto "+Voto+" data "+Data+ " esame "+ esaminonfatti.get(codici[indiceSelezionato]).getNome());
    }
}
