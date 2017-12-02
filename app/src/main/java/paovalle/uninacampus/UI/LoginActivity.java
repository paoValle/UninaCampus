package paovalle.uninacampus.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import business.ControllerUtente;
import entity.Aula;
import entity.Corso;
import entity.CorsoDiLaurea;
import entity.Dettagli;
import entity.Edificio;
import entity.Esame;
import entity.Professore;
import entity.UtenteRegistrato;
import paovalle.uninacampus.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        EditText mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button signInBtn = findViewById(R.id.email_sign_in_button);
        signInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button signUpBtn = findViewById(R.id.bottone_reg);
        signUpBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();


            }
        });

        View mLoginFormView = findViewById(R.id.login_form);
        View mProgressView = findViewById(R.id.login_progress);
    }

    private void goToSignUp() {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        final Toast loginT = Toast.makeText(getApplicationContext(), "Login in corso...", Toast.LENGTH_LONG);
        loginT.show();
        String email = ((TextView)findViewById(R.id.email)).getText().toString();
        String pwd = ((TextView)findViewById(R.id.password)).getText().toString();
        if (isEmailValid(email) && isPasswordValid(pwd)) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
                                            for (DataSnapshot dettagli : s.child("dettagli").getChildren()) {
                                                Dettagli d = new Dettagli();
                                                d.setGiorno(s.child("Giorno").getValue().toString());
                                                d.setOraInizio(s.child("Ora inizio").getValue().toString());
                                                d.setOrafine(s.child("Ora fine").getValue().toString());
                                                //aula
                                                String idAula = s.child("Aula").getValue().toString();
                                                Aula a = new Aula();
                                                a.setCapienza(Integer.parseInt(dataSnapshot.child("Aula").child(idAula).child("Capienza").getValue().toString()));
                                                a.setId(idAula);
                                                a.setPiano(dataSnapshot.child("Aula").child(idAula).child("Piano").getValue().toString());
                                                //edificio
                                                String idEdificio = dataSnapshot.child("Aula").child(idAula).child("Edificio").getValue().toString();
                                                Edificio e = new Edificio();
                                                e.setId(idEdificio);
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
                                        loginT.cancel();
                                        goToLogin();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) { }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast t = Toast.makeText(getApplicationContext(), "Login fallito: dati errati!", Toast.LENGTH_LONG);
                                t.show();
                            }
                        }
                    });
        } else {
            Toast t = Toast.makeText(getApplicationContext(), "Email o password non valide!", Toast.LENGTH_LONG);
            t.show();
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        Log.w("EMAIL", email);
        //TODO: Replace this with your own logic
        return email.contains("@studenti.unina.it");
    }

    private boolean isPasswordValid(String password) {
        Log.w("PWD", password);
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }

}

