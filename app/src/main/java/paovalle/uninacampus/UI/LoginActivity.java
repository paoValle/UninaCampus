package paovalle.uninacampus.UI;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import business.ControllerLogin;
import business.Util;
import paovalle.uninacampus.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private ProgressBar pbar;

    private ControllerLogin cLogin = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        cLogin = new ControllerLogin();

        setContentView(R.layout.activity_login);

        pbar = findViewById(R.id.loginprogressbar);

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
    }

    private void goToSignUp() {
        if (!Util.isNetworkAvailable(this)) {
            Toast.makeText(getApplicationContext(), "Connessione internet assente!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
        }

    }

    private void attemptLogin() {
        if (!Util.isNetworkAvailable(this)) {
             Toast.makeText(getApplicationContext(), "Connessione internet assente!", Toast.LENGTH_LONG).show();
        } else {
            showProgressbar();
            String email = ((TextView) findViewById(R.id.email)).getText().toString();
            String pwd = ((TextView) findViewById(R.id.password)).getText().toString();
            if (isEmailValid(email) && isPasswordValid(pwd)) {
                cLogin.attemptLogin(this, email, pwd);
            } else {
                Toast.makeText(getApplicationContext(), "Email o password non valide!", Toast.LENGTH_LONG).show();
                hideProgressbar();
            }
        }
    }

    public void showProgressbar() {
        pbar.setVisibility(View.VISIBLE);
    }

    public void hideProgressbar() {
        pbar.setVisibility(View.GONE);
    }

    public void goToLogin() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    private boolean isEmailValid(String email) {
        return email.contains("@studenti.unina.it");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

}
