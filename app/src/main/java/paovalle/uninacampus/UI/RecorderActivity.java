package paovalle.uninacampus.UI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import business.ControllerCorso;
import paovalle.uninacampus.Manifest;
import paovalle.uninacampus.R;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecorderActivity extends AppCompatActivity {

    private static final String LOG_TAG = "AudioRecordTest";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static String mFileName = null;

    ControllerCorso cCorso;

    Button btnRecorder;
    Button btnStop;
    Button btnBack;
    Chronometer time;
    TextView txtTitolo;
    TextView txtDate;
    TextView txtCorso;

    String AudioSavePathInDevice = null;
    String corso = "";
    String titolo2 = "";
    MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "ABCDEFGHIJKLMNOP";
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        cCorso = ControllerCorso.getInstance();

        btnRecorder=(Button)findViewById(R.id.btnRecorder);
        btnStop=(Button)findViewById(R.id.btnStop);
        btnBack=(Button)findViewById(R.id.btnBack);
        time = (Chronometer)findViewById(R.id.time);
        txtTitolo = (TextView) findViewById(R.id.txtTitolo);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtCorso = (TextView) findViewById(R.id.txtCorso);

        Intent i = getIntent();
        String idCorso = i.getExtras().getString("IDCORSO");
        if (idCorso!=null) {//ho un id corso, cerco mail prof associato
            corso = cCorso.getNameByIdCorso(idCorso);

        }

        String titolo ="";
        for(char s : corso.toCharArray()){
            if(Character.isUpperCase(s) || !Character.isLetter(s))
                titolo = titolo.concat(String.valueOf(s));
        }

        titolo = titolo.replaceAll("\\s+","");
        txtCorso.setText("Corso: " +corso);

        Date curDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateToStr = format.format(curDate);
        txtDate.setText("Data: "+dateToStr);

         titolo2 = titolo+" "+dateToStr;
        txtTitolo.setText("Titolo: "+titolo2);

        btnStop.setEnabled(false);

        btnRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkPermission()) {

                    AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +""  + titolo2+".3gp";
                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        time.setBase(SystemClock.elapsedRealtime());
                        time.start();
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    btnRecorder.setEnabled(false);
                    btnStop.setEnabled(true);

                    Toast.makeText(getApplicationContext(), "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                time.stop();
                btnStop.setEnabled(false);

                btnRecorder.setEnabled(true);
                Toast.makeText(RecorderActivity.this, "Recording Completed",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToHome();
            }
        });
    }


    private void goToHome(){
        Intent intent = new Intent(this,HomePage.class);
        startActivity(intent);
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        Random random = new Random();
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(RecorderActivity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(getApplicationContext(), "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }


}
