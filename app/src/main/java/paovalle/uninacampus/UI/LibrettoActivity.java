package paovalle.uninacampus.UI;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;

import entity.Corso;
import entity.UtenteRegistrato;
import paovalle.uninacampus.R;
import entity.Esame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.widget.Button;
import android.widget.Spinner;


public class LibrettoActivity extends AppCompatActivity {
    private UtenteRegistrato user = UtenteRegistrato.getIstanza();
    private Button btnback;
    private Button btnaggiorna;
    private Button btnelimina;
    private Button btndone;
    private AlertDialog inserimentoEsame;
    private GridView lv;
    private GridView lv2;
    private String[] esami;
    private ArrayList<Esame> es;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  Log.d("prova","sono nell'activity del libretto" );
        super.onCreate(savedInstanceState);
        // Log.d("prova","sono nell'activity del libretto22" );
        setContentView(R.layout.activity_libretto);


        es = new ArrayList<Esame>();

        if (es == null || es.size() == 0) {

            esami = new String[]{"Non", "ci", "sono", "esami"};
        } else {
            esami = new String[es.size() * 4];
            int iterator = 0;
            for (Esame e : es) {
                esami[iterator++] = e.getCorso().getNome();
                esami[iterator++] = Integer.toString(e.getCorso().getCFU());
                esami[iterator++] = e.getData().toString();
                esami[iterator++] = Integer.toString(e.getVoto());
            }
        }

        lv = (GridView) findViewById(R.id.idListaEsami);
        lv2 = (GridView) findViewById(R.id.idhead);
        String[] header = new String[]{"Esame", "CFU", "DATA", "VOTO"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, esami);
        lv.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.rowheader, header);
        lv2.setAdapter(adapter2);

        btnback = (Button) findViewById(R.id.indietrofromLibretto);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
        btnaggiorna = (Button) findViewById(R.id.idaggiorna);
        btnaggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AggiungiEsame();

            }
        });
        btnelimina = btnback = (Button) findViewById(R.id.idElimina);
    }

    private void goToHome() {
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        Log.w("prova", "Sto clickando conferma");
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    private void AggiungiEsame() {
        //TODO: FINIRE

        Dialog d = new Dialog(this);
        d.setTitle("Inserimento nuovo esame");
        Log.d("prova","sono nella funzione aggiungi esame");
        d.setCancelable(true);
        d.setContentView(R.layout.dialogo_inserimentoesame);
        HashMap<String, Corso> rimanenti = new HashMap<>(user.getCorso().getCorsi());

        Log.d("prova","prima di rimuovere gli esami "+ rimanenti.size() );
        Set<Esame> esami = user.getLibretto().entrySet();
        for(Esame e: esami){
           Log.d("prova","vedo il libretto "+ user.getLibretto().size() );
           rimanenti.remove(e.getCorso().getCodice());
       }
        Log.d("prova","ho rimosso gli esami dimensione dell'array rimanenti "+ rimanenti.size() );

        String[] exam=new String[rimanenti.size()];
        int iterator=0;

        //for (Map.Entry<String, Corso> entry : rimanenti.entrySet()) {
          //  exam[iterator++]=entry.getValue().getNome();
        //}


        Spinner s=(Spinner) findViewById(R.id.idesamidainiserire);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, new String[]{"ciao", "wau"});
        s.setAdapter(adapter);

        d.show();
    }
    }
