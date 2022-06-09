package com.example.stor_it;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFile extends AppCompatActivity {

    Button selectFile, uploadFile;
    TextView fileSelected;
    Uri fileUri;
    ProgressDialog progressDialog;
    FirebaseStorage storage; //used for uploading files... Ex: pdf
    FirebaseDatabase database; //used to store URLs of uploaded files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_file);

        selectFile = findViewById(R.id.btnSelectFile);
        uploadFile = findViewById(R.id.btnUpload);
        fileSelected = findViewById(R.id.txtNoFileSelected);


        storage = FirebaseStorage.getInstance(); //return an object of Firebase Storage
        database = FirebaseDatabase.getInstance(); //return an object of Firebase Database

        selectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(AddFile.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    goSelectFile();
                }else {
                    ActivityCompat.requestPermissions(AddFile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }
            }
        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fileUri!=null){
                    //user has selected file
                    uploadTheFile(fileUri);
                }else{
                    Toast.makeText(AddFile.this, "Select a file", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadTheFile(Uri fileUri) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading file...");
        progressDialog.setProgress(0);
        progressDialog.show();

        String fileName = System.currentTimeMillis()+"";
        StorageReference storageReference = storage.getReference(); //returns the root path

        storageReference.child("File Uploads").child(fileName).putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String url = taskSnapshot.getUploadSessionUri().toString(); //returns url of uploaded file

                //store the url in real time database

                DatabaseReference reference = database.getReference(); //returns the root path

                reference.child(fileName).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(AddFile.this, "File succesfully uploaded", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(AddFile.this, "File not succesfully uploaded", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddFile.this, "File not succesfully uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //track the progress of the upload
                int currentProgress = (int) (100 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());

                progressDialog.setProgress(currentProgress);
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            goSelectFile();
        } else {
            Toast.makeText(AddFile.this, "Please provide permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void goSelectFile() {
        // to offer user to select a file using file manager
        //we will be using an Intent
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT); //to fetch files
        startActivityForResult(intent,86);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //check whether user has selected a file or not

        if(requestCode == 86 && resultCode == RESULT_OK && data!=null){
            fileUri = data.getData(); //return the uri of selected file
            fileSelected.setText("A file is selected: " + data.getData().getLastPathSegment());
        }else {
            Toast.makeText(AddFile.this, "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}
























