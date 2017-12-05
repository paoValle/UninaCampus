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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import paovalle.uninacampus.R;

public class RecorderManager extends AppCompatActivity {

    Button btnChoose;
    Button btnUpload;
    File fileSelected = null;
    TextView txtFile;
    UploadTask uploadTask ;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

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
                Uri file = Uri.fromFile(fileSelected);
                StorageReference riversRef = storageRef.child("reg/"+file.getLastPathSegment());
                uploadTask = riversRef.putFile(file);

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
                Log.d("TESTT1", file.getName());
                if(file != null){
                    txtFile.setText(file.getName());
                    fileSelected = file;
                }
            }
        }).showDialog();



    }



}
