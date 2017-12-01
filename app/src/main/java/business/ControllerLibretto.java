package business;

import android.widget.Spinner;

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


    public String[] getSArrayEsamiSvolti() {
        HashMap<String, Corso> rimanenti = new HashMap<>(cUser.getCurrentUserCorsi());

        Set<Esame> esami = cUser.getCurrentUserEsami().entrySet();
        for (Esame e : esami) {
            rimanenti.remove(e.getCorso().getCodice());
        }

        String[] exam = new String[rimanenti.size()];
        for (Map.Entry<String, Corso> c : rimanenti.entrySet()) {
            exam[exam.length] = c.getValue().getNome();
        }

        return exam;
    }
}
