package business;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import entity.Aula;
import entity.Corso;
import entity.CorsoDiLaurea;
import entity.Dettagli;
import entity.Edificio;
import entity.Esame;
import entity.Professore;
import entity.UtenteRegistrato;
import paovalle.uninacampus.UI.LoginActivity;

/**
 * Created by paolo on 08/01/2018.
 */

public class ControllerLogin {
    private static ControllerLogin instance;
    private FirebaseAuth mAuth;

    public static synchronized ControllerLogin getInstance() {
        if(instance == null) {
            instance = new ControllerLogin();
        }
        return instance;
    }

    public void attemptLogin(final LoginActivity context, String email, String pwd) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String UID = user.getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference dbRef = database.getReference();
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @SuppressWarnings("ConstantConditions")
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    DataSnapshot userDB = dataSnapshot.child("utente").child(UID);
                                    UtenteRegistrato user = ControllerUtente.getInstance().createUser(UID);
                                    user.setCognome(userDB.child("lname").getValue().toString());
                                    user.setNome(userDB.child("fname").getValue().toString());
                                    user.setEmail(userDB.child("email").getValue().toString());
                                    user.setMatricola(userDB.child("matr").getValue().toString());
                                    user.setMedia(Double.parseDouble(userDB.child("mean").getValue().toString()));
                                    String corsoDiLaurea = userDB.child("corsoDiLaurea").getValue().toString();
                                    //scarico il corso di laurea
                                    CorsoDiLaurea cdl = new CorsoDiLaurea();
                                    cdl.setId(corsoDiLaurea);
                                    cdl.setNome(dataSnapshot.child("CorsoDiLaurea").child(corsoDiLaurea).child("Nome").getValue().toString());
                                    //scarico corsi
                                    for (DataSnapshot s : dataSnapshot.child("CorsoDiLaurea").child(corsoDiLaurea).child("Corso").getChildren()) {
                                        Corso c = new Corso();
                                        c.setCodice(s.child("Id").getValue().toString());
                                        c.setCFU(Integer.valueOf(s.child("Cfu").getValue().toString()));
                                        c.setCodice(s.child("Id").getValue().toString());
                                        c.setNome(s.child("Nome").getValue().toString());
                                        c.setSemestre(s.child("Semestre").getValue().toString());
                                        //professore
                                        String idProf = s.child("Professore").getValue().toString();
                                        Professore p = new Professore();
                                        p.setCognome(dataSnapshot.child("Professore").child(idProf).child("Cognome").getValue().toString());
                                        p.setNome(dataSnapshot.child("Professore").child(idProf).child("Nome").getValue().toString());
                                        p.setTelefono(dataSnapshot.child("Professore").child(idProf).child("Tel").getValue().toString());
                                        p.setEmail(dataSnapshot.child("Professore").child(idProf).child("Email").getValue().toString());
                                        p.setId(idProf);
                                        System.out.println("mail: "+p.getEmail());
                                        c.setProfessore(p);
                                        //dettagli
                                        for (DataSnapshot dettagli : s.child("Dettagli").getChildren()) {
                                            Dettagli d = new Dettagli();
                                            d.setGiorno(dettagli.child("Giorno").getValue().toString());
                                            d.setOraInizio(dettagli.child("Ora inizio").getValue().toString());
                                            d.setOrafine(dettagli.child("Ora fine").getValue().toString());
                                            //aula
                                            String idAula = dettagli.child("Aula").getValue().toString();
                                            Aula a = new Aula();
                                            a.setCapienza(Integer.parseInt(dataSnapshot.child("Aula").child(idAula).child("Capienza").getValue().toString()));
                                            a.setId(idAula);
                                            a.setPiano(dataSnapshot.child("Aula").child(idAula).child("Piano").getValue().toString());
                                            a.setLat(dataSnapshot.child("Aula").child(idAula).child("lat").getValue().toString());
                                            a.setLng(dataSnapshot.child("Aula").child(idAula).child("long").getValue().toString());
                                            //edificio
                                            String idEdificio = dataSnapshot.child("Aula").child(idAula).child("Edificio").getValue().toString();
                                            Edificio e = new Edificio();
                                            e.setId(idEdificio);
                                            System.out.println("idEdificio=>"+idEdificio);
                                            e.setIndirizzo(dataSnapshot.child("Edificio").child(idEdificio).child("Indirizzo").getValue().toString());
                                            a.setEd(e);
                                            d.setAula(a);
                                            c.getDettagli().add(d);
                                        }
                                        cdl.getCorsi().put(c.getCodice(), c);
                                    }
                                    //carico corsi scelti
                                    for (DataSnapshot corsoScelto : userDB.child("corsiScelti").getChildren()) {
                                        //aggiungo oggetto corso dal corso di laurea gia creato
                                        String idCorso = corsoScelto.getValue().toString();
                                        Corso c = (Corso) cdl.getCorsi().get(idCorso);
                                        user.getCorsiScelti().put(idCorso, c);
                                    }
                                    //carico libretto
                                    for (DataSnapshot libretto : userDB.child("libretto").getChildren()) {
                                        //aggiungo oggetto corso dal corso di laurea gia creato
                                        String idCorso = libretto.child("corso").getValue().toString();
                                        Corso c = (Corso) cdl.getCorsi().get(idCorso);
                                        Esame e = new Esame();
                                        e.setCorso(c);

                                        e.setData(libretto.child("data").getValue().toString());
                                        e.setVoto(Integer.parseInt(libretto.child("voto").getValue().toString()));
                                        String codice=libretto.child("codice").getValue().toString();
                                        user.getLibretto().put(codice, e);
                                    }
                                    user.setCorso(cdl);
                                    //loginT.cancel();
                                    context.goToLogin();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast t = Toast.makeText(context.getApplicationContext(), "Login fallito, c'è stato un errore.", Toast.LENGTH_LONG);
                                    t.show();
                                    context.hideProgressbar();
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast t = Toast.makeText(context.getApplicationContext(), "Login fallito: dati errati!", Toast.LENGTH_LONG);
                            t.show();
                            context.hideProgressbar();
                        }
                    }
                });
    }
}
