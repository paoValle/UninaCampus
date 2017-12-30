package paovalle.uninacampus.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import paovalle.uninacampus.R;

public class RecorderManager extends AppCompatActivity {

    private  Button btnChoose;
    private Button btnUpload;
    private Button  btnBack;
    private Button btnLista;
    private Button btnDownload;
    private  ListView lstReg;
    private  String listSelected = null;
    private File fileSelected = null;
    private TextView txtFile;
    private UploadTask uploadTask;
    private  FirebaseStorage storage = FirebaseStorage.getInstance();
    private  StorageReference storageRef = storage.getReference();
    private DatabaseReference dbRef;

    private ArrayList<String> registrazioni = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder_manager);

        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        btnBack= (Button)findViewById(R.id.idBackFromRecorderManager);
        btnLista = (Button) findViewById(R.id.btnLista);
        btnDownload = (Button) findViewById(R.id.btnDownload);
        lstReg = (ListView) findViewById(R.id.lstReg);
        txtFile = (TextView) findViewById(R.id.txtFile);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHome();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                dbRef = database.getReference();
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //DataSnapshot userDB = dataSnapshot.child("utente").child(UID);
                      //  registrazioni.clear();
                        for (DataSnapshot registrazione : dataSnapshot.child("Registrazioni").getChildren())
                            registrazioni.add(registrazione.getValue().toString());


                        if (fileSelected == null) {
                            Toast.makeText(getApplicationContext(), "Selezionare un file", Toast.LENGTH_LONG).show();
                        } else {
                            Uri file = Uri.fromFile(fileSelected);
                            if (registrazioni.contains(fileSelected.getName())) {
                                Toast.makeText(getApplicationContext(), "File già presente", Toast.LENGTH_LONG).show();
                            } else {
                                StorageReference riversRef = storageRef.child("reg/" + file.getLastPathSegment());
                                uploadTask = riversRef.putFile(file);
                                String element = dbRef.child("Registrazioni").push().getKey();
                                dbRef.child("Registrazioni").child(element).setValue(fileSelected.getName());
                                Toast.makeText(getApplicationContext(), "File caricato", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                /*
                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });*/

            }
        });

        lstReg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listSelected = registrazioni.get(i);
            }
        });

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                dbRef = database.getReference();
                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //DataSnapshot userDB = dataSnapshot.child("utente").child(UID);
                            registrazioni.clear();
                        for (DataSnapshot registrazione : dataSnapshot.child("Registrazioni").getChildren())
                            registrazioni.add(registrazione.getValue().toString());
                        showList();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

                /*
                // Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                });*/

            }
        });

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listSelected != null)
                    storageRef.child("reg/" + listSelected).getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            // Use the bytes to display the image
                            File sd = Environment.getExternalStorageDirectory();

                            try {
                                FileOutputStream fos = new FileOutputStream(sd.getAbsolutePath()+ "/" + listSelected);
                                fos.write(bytes);
                                fos.close();

                            } catch (IOException e) {

                            }
                            Toast.makeText(getBaseContext(), "Download completato" , Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                else{
                   Toast.makeText(getBaseContext(), "Selezionare un file" , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    private void showList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, registrazioni);
        lstReg.setAdapter(adapter);
    }
    public void fileChooser() {
        final FileChooser f = new FileChooser(this);
        f.setFileListener(new FileChooser.FileSelectedListener() {
            @Override
            public void fileSelected(final File file) {
                //  something with the file
                if (file != null) {
                    txtFile.setText(file.getName());
                    fileSelected = file;
                }
            }
        }).showDialog();
    }


    private void gotoHome(){
        super.onBackPressed();
    }
}
