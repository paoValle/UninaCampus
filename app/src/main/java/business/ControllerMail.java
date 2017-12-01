package business;

import entity.Corso;
import entity.Professore;
import entity.UtenteRegistrato;

public class ControllerMail {
    private static ControllerMail instance;
    private ControllerUtente cUser;

    public static synchronized ControllerMail getInstance() {
        if(instance==null) {
            instance=new ControllerMail();
        }
        return instance;
    }

    private ControllerMail() {
        cUser = ControllerUtente.getInstance();
    }
    //ritorna la mail del prof, se trova corso relativo, altrimenti una stringa vuota
    public String getMailByIdCorso(String idCorso) {
        System.out.println("[CM] Cerco: "+idCorso);
        UtenteRegistrato user = cUser.getCurrentUser();
        if (user == null) {
            System.out.println("[CM] user null!");
            return "";
        } else {
            Corso c = (Corso)user.getCorso().getCorsi().get(idCorso);
            if (c == null) {//non trovato!
                System.out.println("[CM] c null!");
                return "";
            }
            Professore p = c.getProfessore();
            if (p == null) {
                System.out.println("[CM] p null!");
                return "";
            } else {
                System.out.println("[CM] trovata => " + p.getEmail());
                return p.getEmail();
            }
        }
    }
}
