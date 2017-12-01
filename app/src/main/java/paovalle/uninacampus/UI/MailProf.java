package paovalle.uninacampus.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import business.ControllerMail;
import paovalle.uninacampus.R;

public class MailProf extends AppCompatActivity {

    Button btnsend;
    Button btnback;

    ControllerMail cMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail_prof);

        cMail = ControllerMail.getInstance();

        btnsend= findViewById(R.id.Sendbutton);
        btnback= findViewById(R.id.idbackfrommail);

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

        //vedo se mi è stato passato un id corso
        Intent i = getIntent();
        String idCorso = i.getExtras().getString("IDCORSO");
        Log.w("IDCORSO", idCorso);
        System.out.println("IDCORSO=>"+idCorso);
        if (idCorso!="") {//ho un id corso, cerco mail prof associato
            String mail = cMail.getMailByIdCorso(idCorso);
            ((TextView)findViewById(R.id.IdTo)).setText(mail);
        }
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
