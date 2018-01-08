package paovalle.uninacampus.UI;

import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*import business.HTMLPageDownloader;
import paovalle.uninacampus.R;*/

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.Arrays;

import business.Util;
import paovalle.uninacampus.R;

public class QRScannerActivity extends AppCompatActivity {
    private SurfaceView cameraPreview;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private TextView txtResult;
    private final int RequestCameraPermissionID = 1001;
    private Dialog d;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        cameraPreview = findViewById(R.id.cameraPreview);

        txtResult = findViewById(R.id.txtResult);

        d = new Dialog(this);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1080, 1080)
                .build();
        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(QRScannerActivity.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {}

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0 && !d.isShowing())
                {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            final String scanned = qrcodes.valueAt(0).displayValue;
                            //cerco in db se è presente aula
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference dbRef = database.getReference();
                            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @SuppressWarnings("ConstantConditions")
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    DataSnapshot child = dataSnapshot.child("Aula").child(scanned);
                                    if (child==null) {
                                        Toast.makeText(getApplicationContext(), "Aula sconosciuta!", Toast.LENGTH_LONG).show();
                                    } else {
                                        String weekDay = Util.getWeekDay();
                                        if (!isValidUnivDay(weekDay)) {
                                            Toast.makeText(getApplicationContext(), "E' il weekend, cosa ci fai all'unina?", Toast.LENGTH_LONG).show();
                                        } else {
                                            String orario = child.child("orarioSettimana").child(weekDay).getValue().toString();
                                            showOrario(orario, scanned, weekDay);
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {  }
                            });
                        }
                    });
                }
            }
        });
    }

    private void showOrario(String orario, String classe, String weekDay) {
        //mostro dialog con orario ordierno aula scannerizzata
        d.setTitle("Orario classe "+classe+" del "+weekDay);
        d.setCancelable(true);
        d.setContentView(R.layout.dialogo_orarioaula);

        Button dismissDialog = (Button)d.findViewById(R.id.dismissOrariAula);
        dismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        //GridView headerGridView = d.findViewById(R.id.dialogOrariGridHeader);
        final GridView bodyGridView = d.findViewById(R.id.dialogOrariGridBody);
        //preparo contenuto tabella
        String[] corsiGiornata = orario.split(";");
        String[] orari = new String[] {"[08:30-09:30]","[09:30-10:30]","[10:30-11:30]",
                "[11:30-12:30]","[12:30-13:30]","[13:30-14:30]","[14:30-15:30]","[15:30-16:30]",
                "[16:30-17:30]","[17:30-18:30]","[18:30-19:30]"};
        //merge
        final String[] bodyGrid = mergeOrariCorsi(orari, corsiGiornata);
        ArrayAdapter<String> adapterBody = new ArrayAdapter<String>(this, R.layout.row, bodyGrid);
        bodyGridView.setAdapter(adapterBody);

        bodyGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) { }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                System.out.println("Scrolling...");
                bodyGridView.invalidateViews();
            }
        });

        d.show();

    }

    private String[] mergeOrariCorsi(String[] orari, String[] corsiGiornata) {
        String[] bodyGrid = new String[corsiGiornata.length+orari.length];
        int j = 0;
        for (int i=0;i<corsiGiornata.length;i++) {
            bodyGrid[j++] = orari[i];
            //mostro il nome del corso oppure "LIBERA"
            bodyGrid[j++] = corsiGiornata[i].equals("0")?"LIBERA":corsiGiornata[i];
        }
        return bodyGrid;
    }

    private boolean isValidUnivDay(String weekDay) {
        String[] valids = new String[] { "lun", "mar", "mer", "gio", "ven"};
        return Arrays.asList(valids).contains(weekDay);
    }
}