package paovalle.uninacampus.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import business.ControllerMail;
import paovalle.uninacampus.R;

public class MailProf extends AppCompatActivity {

    Button btnsend;
    Button btnback;
    TextInputEditText destinatario;
    TextInputEditText soggetto;
    EditText testoMail;
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

        soggetto=findViewById(R.id.idsubject);
        destinatario=findViewById(R.id.IdTo);
        testoMail=findViewById(R.id.idCorpoMail);

        //vedo se mi è stato passato un id corso
        Intent i = getIntent();
        String idCorso = i.getExtras().getString("IDCORSO");
        if (idCorso!=null && idCorso!="") {//ho un id corso, cerco mail prof associato
            String mail = cMail.getMailByIdCorso(idCorso);
            ((TextView)findViewById(R.id.IdTo)).setText(mail);
        }
    }


    private void attemptSendMail(){

        if (destinatario.getText().toString().equals("") ){
            Toast.makeText(this, "Destinatario obbligatorio.", Toast.LENGTH_SHORT).show();
        }else {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{destinatario.getText().toString()});
            i.putExtra(Intent.EXTRA_SUBJECT, soggetto.getText().toString());
            i.putExtra(Intent.EXTRA_TEXT, testoMail.getText().toString());
            try {
                startActivity(Intent.createChooser(i, "Scegli un client"));
            } catch (android.content.ActivityNotFoundException ex) {

                Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
            }

            super.onBackPressed();
        }

    }


    private void goToHome(){
        super.onBackPressed();
    }
}
