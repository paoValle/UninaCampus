package paovalle.uninacampus.UI;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import paovalle.uninacampus.R;

public class RecorderManager extends AppCompatActivity {

    Button btnChoose;
    Button btnUpload;
    File fileSelected = null;
    TextView txtFile;
    UploadTask uploadTask ;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    ArrayList<String> registrazioni = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder_manager);

        btnChoose = (Button)findViewById(R.id.btnChoose);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        txtFile = (TextView)findViewById(R.id.txtFile);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileChooser();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference dbRef = database.getReference();
                dbRef.addValueEventListener(new ValueEventListener() {
                    @SuppressWarnings("ConstantConditions")
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //DataSnapshot userDB = dataSnapshot.child("utente").child(UID);
                        for(DataSnapshot registrazione : dataSnapshot.child("Registrazioni").getChildren())
                            registrazioni.add(registrazione.getValue().toString());

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) { }
                });

                Uri file = Uri.fromFile(fileSelected);
                if(registrazioni.contains(fileSelected.getName())){
                    Toast.makeText(getApplicationContext(), "File già presente", Toast.LENGTH_LONG).show();
                }else{
                    StorageReference riversRef = storageRef.child("reg/"+file.getLastPathSegment());
                    uploadTask = riversRef.putFile(file);
                    Toast.makeText(getApplicationContext(), "File caricato", Toast.LENGTH_LONG).show();
                }

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
                });

            }
        });
    }

    public void fileChooser(){
        final FileChooser f=new FileChooser(this);
        f.setFileListener(new FileChooser.FileSelectedListener() {
            @Override public void fileSelected(final File file) {
                //  something with the file
                if(file != null){
                    txtFile.setText(file.getName());
                    fileSelected = file;
                }
            }
        }).showDialog();
    }
}
