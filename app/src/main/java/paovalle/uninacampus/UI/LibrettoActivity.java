package paovalle.uninacampus.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import business.ControllerLibretto;
import entity.Corso;
import entity.Esame;
import paovalle.uninacampus.R;


public class LibrettoActivity extends AppCompatActivity {
    private GridView lvlistaesami;
    private GridView lv2;
    private String[] esami; //per mostrare a video gli esami fatti
    private String[] codiciesamidamostrare; //codici degli esami che ho preso
    private int selectIndex; //indice dell'esame che deve essere aggiunto
    private EditText votoscritto;
    private HashMap<String, Corso> exam; //hashmap degli esami che devo svolgere
    private List<String> codici; //codici degli esami che devo svolgere
    private DatePicker datePicker;
    private Dialog d;
    private Dialog deliminazione;
    private ControllerLibretto cLibretto;
    private CheckBox check;
    private int posizioneEsameselezionata;
    private boolean vuoto; //serve per capire se ci sono o meno esami da mostrare. Se è vuoto io non posso abilitare l'eliminazione
    private Button btnelimina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libretto);
        cLibretto = ControllerLibretto.getInstance();
        lvlistaesami = findViewById(R.id.idListaEsami);
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

         btnelimina = findViewById(R.id.idElimina);

        btnelimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        DeleteEsame();
            }
        });

        lvlistaesami.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(vuoto){
                    Toast.makeText(getBaseContext(), "CAPRA! NON HAI ESAMI " , Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    btnelimina.setVisibility(View.VISIBLE);
                   // Toast.makeText(getBaseContext(), "funziona il toast", Toast.LENGTH_SHORT).show();
                    posizioneEsameselezionata=(position/4);
                    String esame=esami[posizioneEsameselezionata*4];
                    Toast.makeText(getBaseContext(), "Esame Selezionato " + esame, Toast.LENGTH_SHORT).show();
                    return true;
                }


            }
        });
    }


    private void goToHome() {
        super.onBackPressed();
    }

    private void DeleteEsame() {
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        btnelimina.setVisibility(View.INVISIBLE);
        deliminazione=new Dialog(this);

        deliminazione.setCancelable(true);
        deliminazione.setContentView(R.layout.sicurodieliminare);

        Button btnok= deliminazione.findViewById(R.id.idokeliminazione);
        Button btncance= deliminazione.findViewById(R.id.idCancelOperazione);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    cLibretto.deleteExam(codiciesamidamostrare[posizioneEsameselezionata]);
                    deliminazione.cancel();
                    mostraElementi();
            }
        });

        btncance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deliminazione.cancel();

            }
        });
        deliminazione.show();
    }


    private void mostraElementi(){
       // btnelimina.setVisibility(View.INVISIBLE);
        posizioneEsameselezionata=-1;
        HashMap<String,Esame> hashappoggio= new HashMap<>(cLibretto.getEsamiSvolti());
        ArrayList<Esame> es = new ArrayList<>(hashappoggio.values());

        if (es.size() == 0) {
            esami = new String[]{"Non", "ci", "sono", "esami"};
            vuoto=true;
        } else {
            vuoto=false;
            codiciesamidamostrare=new String[hashappoggio.size()];
            int j=0;
            for(Map.Entry<String, Esame> entry : hashappoggio.entrySet()) {
                codiciesamidamostrare[j]=entry.getKey();
                j++;
            }
            esami = new String[es.size() * 4];
            int iterator = 0;

            for (Esame e : es) {
                esami[iterator++] = e.getCorso().getNome();
                esami[iterator++] = Integer.toString(e.getCorso().getCFU());
                esami[iterator++] = e.getData();
                if(e.getVoto()==31){
                    esami[iterator++] ="30 e lode" ;
                }
                else{
                    esami[iterator++] = Integer.toString(e.getVoto());
                }
            }
        }
        String[] header = new String[]{"Esame", "CFU", "DATA", "VOTO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.rowesami, esami);
        lvlistaesami.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.rowheader, header);
        lv2.setAdapter(adapter2);
    }


    private void AggiungiEsame() {
        //TODO: FINIRE
        d = new Dialog(this);
        d.setTitle("Inserimento nuovo esame");
        d.setCancelable(true);
        d.setContentView(R.layout.dialogo_inserimentoesame);

        exam = cLibretto.getEsamiDaSvolgere();
        List<String> esaminomi=cLibretto.getNomeEsamiDaSvolgere();
        codici=cLibretto.getIdEsamiDaSvolgere();

        Spinner s= d.findViewById(R.id.idaggiungiesame);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.row, esaminomi);
        s.setAdapter(adapter);

        //prendi l'elemento selezionato con l'indice
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg, View arg1,
                                       int pos, long arg3) {
                selectIndex=pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        votoscritto= d.findViewById(R.id.idinserimentovoto);
        datePicker = d.findViewById(R.id.idprendidata);
        check= d.findViewById(R.id.idcheckLode);
        Button donebtn= d.findViewById(R.id.idDoneEsame);
        Button backbtn= d.findViewById(R.id.idBackFromEsame);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              d.cancel();
            }
        });

        donebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String voto=votoscritto.getText().toString();
                if(!voto.equals("")){
                     int score=Integer.parseInt(voto);

                        if(score>=18 && score<=30){
                            int day = datePicker.getDayOfMonth();
                            int month = datePicker.getMonth() + 1;
                            int year = datePicker.getYear();

                            if(score==30){
                                //solo se il voto è 30 applico il +1 con la lode
                                if(check.isChecked()){
                                    score=score+1;
                                }
                            }else{
                                //se il voto è diverso da 30 e il cretino ha cliccato lode-> lode viene ignorata
                                if(check.isChecked()){
                                    Toast.makeText(getBaseContext(), "Lode ignorata", Toast.LENGTH_SHORT).show();
                                }

                            }
                            String data=Integer.toString(day)+"/"+Integer.toString(month)+"/"+Integer.toString(year);
                            cLibretto.addEsame(score,data,exam,selectIndex,codici);
                            d.cancel();
                            mostraElementi();
                        }
                        else{
                            Toast.makeText(getBaseContext(), "Voto compreso tra 18 e 30", Toast.LENGTH_SHORT).show();
                        }
                }else{

                    Toast.makeText(getBaseContext(), "Inserimento Voto obbligatorio", Toast.LENGTH_SHORT).show();
                }
            }
        });
        d.show();
    }
}
