package paovalle.uninacampus.UI;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import entity.Aula;
import entity.Corso;
import entity.CorsoDiLaurea;
import entity.Dettagli;
import entity.Professore;
import entity.UtenteRegistrato;
import paovalle.uninacampus.R;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private FirebaseAuth mAuth;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button signUpBtn;
    private Button signInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
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


        signInBtn = (Button) findViewById(R.id.email_sign_in_button);
        signInBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        signUpBtn = (Button) findViewById(R.id.bottone_reg);
        signUpBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();


            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void goToSignUp() {
        Intent intent = new Intent(this,SignUp.class);
        startActivity(intent);
    }
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
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
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("DEBUG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String UID = user.getUid();
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference dbRef = database.getReference();
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    DataSnapshot userDB = dataSnapshot.child("utente").child(UID);
                                    UtenteRegistrato user = UtenteRegistrato.getIstanza();
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
                                        Professore p = dataSnapshot.child("Professore").child(idProf).getValue(Professore.class);
                                        c.setProfessore(p);
                                        //dettagli
                                        for (DataSnapshot dettagli : s.child("dettagli").getChildren()) {
                                            Dettagli d = new Dettagli();
                                            d.setGiorno(s.child("Giorno").getValue().toString());
                                            d.setOraInizio(s.child("Ora inizio").getValue().toString());
                                            d.setOrafine(s.child("Ora fine").getValue().toString());
                                            //aula
                                            String idAula = s.child("Aula").getValue().toString();
                                            Aula a = dataSnapshot.child("Aula").child(idAula).getValue(Aula.class);
                                            d.setAula(a);
                                            c.getDettagli().add(d);
                                        }
                                        cdl.getCorsi().put(c.getCodice(), c);
                                    }
                                    //carico corsi scelti
                                    for (DataSnapshot corsoScelto : userDB.child("corsiScelti").getChildren()) {
                                        //aggiungo oggetto corso dal corso di laurea gia creato
                                        String idCorso = corsoScelto.getValue().toString();
                                        Corso c = (Corso)cdl.getCorsi().get(idCorso);
                                        user.getCorsiScelti().put(idCorso, c);
                                    }
                                    //carico libretto
                                    for (DataSnapshot libretto : userDB.child("libretto").getChildren()) {
                                        //aggiungo oggetto corso dal corso di laurea gia creato
                                        String idCorso = libretto.child("corso").getValue().toString();
                                        Corso c = (Corso)cdl.getCorsi().get(idCorso);
                                        user.getLibretto().put(idCorso, c);
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
                            Toast t = Toast.makeText(getApplicationContext(), "Login fallito: dati errati!", Toast.LENGTH_SHORT);
                            t.show();
                            Log.w("STATUS", "signInWithEmail:failure", task.getException());
                        }
                    }
                });
        showProgress(false);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

}

