package paovalle.uninacampus.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import paovalle.uninacampus.R;

public class MailProf extends AppCompatActivity {

    Button btnsend;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_prof);
        btnsend=(Button)findViewById(R.id.Sendbutton);
        btnback=(Button)findViewById(R.id.idbackfrommail);

        btnsend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSendMail();
            }
        });
        btnback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
    }


    private void attemptSendMail(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        Log.w("prova","Sto clickando conferma" );
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }


    private void goToHome(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        Log.w("prova","Sto clickando conferma" );
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }
}
