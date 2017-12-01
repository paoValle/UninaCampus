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

import entity.UtenteRegistrato;
import paovalle.uninacampus.R;
import entity.Esame;
import java.util.ArrayList;
import android.widget.Button;


public class LibrettoActivity extends AppCompatActivity {
    private UtenteRegistrato user;
    Button btnback;
    Button btnaggiorna;
    Button btndone;
    AlertDialog inserimentoEsame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  Log.d("prova","sono nell'activity del libretto" );
        super.onCreate(savedInstanceState);
       // Log.d("prova","sono nell'activity del libretto22" );
        setContentView(R.layout.activity_libretto);

        Log.d("prova","---------------------ho superato on create" );

        ArrayList<Esame> es=new ArrayList<Esame>(user.getIstanza().getLibretto());
        String[] esami;
        if(es==null|| es.size()==0){
            Log.d("prova","NIENTE LIBRETTO" );
            esami = new String[]{"ciao", "esmae","bello", "is2", "tutti","bocciati","yyy","si"};
        } else {
             esami = new String[es.size()];
            int iterator = 0;
            for (Esame e : es) {
                esami[iterator++] = e.getCorso().getNome();
                esami[iterator++] = Integer.toString(e.getCorso().getCFU());
                esami[iterator++] = e.getData().toString();
                esami[iterator++] = Integer.toString(e.getVoto());
            }
        }

      //  Log.d("prova","sto creando la giglia" );


        GridView lv = (GridView) findViewById(R.id.idListaEsami);

        //Log.d("prova","griglia apposto" );

       ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.row,esami);
        lv.setAdapter(adapter);
        Log.d("prova","bottone apposto" );
        btnback=(Button)findViewById(R.id.indietrofromLibretto);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
        btnaggiorna=(Button)findViewById(R.id.idaggiorna);
        btnaggiorna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              prova();

            }
        });
    }

    private void goToHome(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
        Log.w("prova","Sto clickando conferma" );
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }
    private void prova(){
        //TODO: PAOLO DEVE FARE LA LOGICA!!!!
       // AlertDialog.Builder builder=new AlertDialog.Builder(this);
        //inserimentoEsame=builder.create();
        Dialog d=new Dialog(this);
        d.setTitle("prova pezzotta");
        d.setCancelable(false);
        d.setContentView(R.layout.dialogo_inserimentoesame);
      //  btnback=(Button)findViewById(R.id.idDoneEsame);
        //btnback.setOnClickListener(new View.OnClickListener() {
            //@Override
          //  public void onClick(View view) {
            //    goToHome();
            //}
        //});
        d.show();
    }


}
