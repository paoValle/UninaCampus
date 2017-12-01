package business;

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

    public String[] getArrayNomeEsamiDaSvolgere() {
        HashMap<String, Corso> rimanenti = cUser.getCurrentUser().getCorso().getCorsi();
        Collection<Esame> esami = cUser.getCurrentUser().getLibretto().values();
        for(Esame e: esami){
            rimanenti.remove(e.getCorso().getCodice());
        }
        String[] out = new String[rimanenti.size()];
        int i = 0;
        for (Corso e : rimanenti.values()) {
            out[i++] = e.getNome();
        }
        return out;
    }
}
