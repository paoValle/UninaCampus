package business;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import entity.Corso;
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
}
