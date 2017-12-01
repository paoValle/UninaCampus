package paovalle.uninacampus.UI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;

import business.ControllerLibretto;
import business.ControllerUtente;
import entity.Esame;
import paovalle.uninacampus.R;


public class LibrettoActivity extends AppCompatActivity {
    private GridView lv;
    private GridView lv2;
    private String[] esami;
    private ArrayList<Esame> es;

    private ControllerLibretto cLibretto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libretto);

        cLibretto = ControllerLibretto.getInstance();

        es = cLibretto.getArrayListEsamiSvolti();

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

        lv = findViewById(R.id.idListaEsami);
        lv2 = findViewById(R.id.idhead);
        String[] header = new String[]{"Esame", "CFU", "DATA", "VOTO"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, esami);
        lv.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.rowheader, header);
        lv2.setAdapter(adapter2);

        Button btnback = findViewById(R.id.indietrofromLibretto);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
        Button btnaggiorna = findViewById(R.id.idaggiorna);
        btnaggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AggiungiEsame();

            }
        });
        Button btnelimina = btnback = findViewById(R.id.idElimina);
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
        d.setCancelable(true);
        d.setContentView(R.layout.dialogo_inserimentoesame);
        String[] exam;
        exam = cLibretto.getArrayNomeEsamiDaSvolgere();

        Spinner s = findViewById(R.id.idesamidainiserire);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, exam);
        s.setAdapter(adapter);

        d.show();
    }
}
