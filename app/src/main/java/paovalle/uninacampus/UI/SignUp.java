package paovalle.uninacampus.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import paovalle.uninacampus.R;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Button confBtn = findViewById(R.id.confirmButton);

        confBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });

        Button indietroBtn = findViewById(R.id.buttomBack);

        indietroBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignUp();
            }
        });


    }

    private void attemptSignUp(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        final String email = ((TextView)findViewById(R.id.email)).getText().toString();
        final String pwd = ((TextView)findViewById(R.id.password)).getText().toString();
        final String lname = ((TextView)findViewById(R.id.CognomeText)).getText().toString();
        final String fname = ((TextView)findViewById(R.id.TextNome)).getText().toString();
        final String matr = ((TextView)findViewById(R.id.MatricolaText)).getText().toString();
        final String cdl = ((TextView)findViewById(R.id.textViewCDL)).getText().toString();
        if (pwd.equals("") || lname.equals("") || fname.equals("") || matr.equals("") || email.equals("")) {
            Toast.makeText(SignUp.this, "Compilare tutti i campi!", Toast.LENGTH_SHORT).show();
        } else {
            if (pwd.length()<6) {
                Toast.makeText(SignUp.this, "La password deve essere di almeno 6 caratteri!", Toast.LENGTH_SHORT).show();
            } else {
                if (!isValidEmail(email)) {
                    Toast.makeText(SignUp.this, "Email non valida!", Toast.LENGTH_SHORT).show();
                } else {
                    //create user
                    auth.createUserWithEmailAndPassword(email, pwd)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SignUp.this, "Registrazione fallita!",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUp.this, "Utente registrato!",
                                                Toast.LENGTH_SHORT).show();
                                        //creo record in db
                                        String UID = task.getResult().getUser().getUid();

                                        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
                                        DatabaseReference dbRef = mDatabase.getReference();
                                        dbRef.child("utente").child(UID).child("email").setValue(((TextView) findViewById(R.id.email)).getText().toString());
                                        dbRef.child("utente").child(UID).child("fname").setValue(fname);
                                        dbRef.child("utente").child(UID).child("lname").setValue(lname);
                                        dbRef.child("utente").child(UID).child("matr").setValue(matr);
                                        dbRef.child("utente").child(UID).child("mean").setValue(0);
                                        dbRef.child("utente").child(UID).child("corsoDiLaurea").setValue(cdl);
                                        goToLogin();
                                    }
                                }
                            });
                }
            }
        }


    }

    private void goToLogin() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    private boolean isValidEmail(String e) {
        return e.contains("@studenti.unina.it");
    }

}
