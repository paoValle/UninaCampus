package paovalle.uninacampus.UI;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.ControllerLibretto;
import business.ControllerUtente;
import entity.Corso;
import entity.Esame;
import paovalle.uninacampus.R;


public class LibrettoActivity extends AppCompatActivity {
    private GridView lv;
    private GridView lv2;
    private String[] esami;
    private ArrayList<Esame> es;
    private String selezionato;
    private int selectIndex;
    private EditText votoscritto;
    private HashMap<String, Corso> exam;
    private String[] codici;
    private DatePicker datePicker;
    private String dataesame;
    private Dialog d;
    private ControllerLibretto cLibretto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libretto);

        cLibretto = ControllerLibretto.getInstance();
        lv = findViewById(R.id.idListaEsami);
        lv2 = findViewById(R.id.idhead);
        mostraElementi();
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


    private void mostraElementi(){
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


        String[] header = new String[]{"Esame", "CFU", "DATA", "VOTO"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, esami);
        lv.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.rowheader, header);
        lv2.setAdapter(adapter2);
    }
    private void AggiungiEsame() {
        //TODO: FINIRE

        d = new Dialog(this);
        d.setTitle("Inserimento nuovo esame");
        d.setCancelable(true);
        d.setContentView(R.layout.dialogo_inserimentoesame);

        exam = cLibretto.getArrayNomeEsamiDaSvolgere();
        String[] esaminomi=new String[exam.size()];
        codici=new String[exam.size()];
        int i = 0;
        for (Map.Entry<String, Corso> entry: exam.entrySet()) {
            esaminomi[i] = entry.getValue().getNome();
            codici[i++]=entry.getKey();

        }

        Spinner s= (Spinner) d.findViewById(R.id.idaggiungiesame);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, esaminomi);

        s.setAdapter(adapter);


        //prendi l'elemento selezionato con l'indice
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg, View arg1,
                                       int pos, long arg3) {
                selezionato=arg.getSelectedItem().toString();
                selectIndex=pos;
               Log.d("prova", selezionato +"pos" + pos);


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        votoscritto=(EditText)d.findViewById(R.id.idinserimentovoto);
        datePicker = (DatePicker) d.findViewById(R.id.idprendidata);

        Button donebtn=(Button) d.findViewById(R.id.idDoneEsame);
        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String voto=votoscritto.getText().toString();
                if(voto!=null && !voto.equals("")){
                     int score=Integer.parseInt(voto);

                        if(score>=18 && score<=30){
                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();

                            String data=Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
                            cLibretto.addEsame(score,data,exam,selectIndex,codici);
                            Log.d("prova", selezionato +" con voto " + voto );
                            d.cancel();
                            mostraElementi();
                        }
                        else{

                        }


                }else{
                    Log.d("prova", "inserisci un voto" );
                }
            }
        });
        d.show();
    }
}
