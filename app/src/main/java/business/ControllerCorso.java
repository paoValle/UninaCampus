package business;

import entity.Corso;
import entity.UtenteRegistrato;

public class ControllerCorso {
    private static ControllerCorso instance;
    private ControllerUtente cUser;

    public static synchronized ControllerCorso getInstance() {
        if(instance == null) {
            instance = new ControllerCorso();
        }
        return instance;
    }

    private ControllerCorso() {
        cUser = ControllerUtente.getInstance();
    }

    public String getNameByIdCorso(String idCorso) {
        //System.out.println("[CM] Cerco: "+idCorso);
        UtenteRegistrato user = cUser.getCurrentUser();
        if (user == null) {
            System.out.println("[CM] user null!");
            return "";
        } else {
            Corso c = (Corso) user.getCorso().getCorsi().get(idCorso);
            if (c == null) {//non trovato!
                System.out.println("[CM] c null!");
                return "";
            }else{
                return c.getNome();
            }
        }
    }
}