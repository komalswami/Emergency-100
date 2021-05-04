package com.example.policeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;


public class cam extends AppCompatActivity {
    ImageView imageView;
    Button btOpen,btn;
    ProgressBar prgbar;
    private Uri imgUri;
    Bitmap captureImage;


    private StorageReference reference= FirebaseStorage.getInstance().getReference();

    FirebaseDatabase rootnode= FirebaseDatabase.getInstance();
    DatabaseReference root=rootnode.getReference().child("url");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);

        btOpen=(Button)findViewById(R.id.cam_open);
        imageView=(ImageView)findViewById(R.id.img1);
        btn=findViewById(R.id.send_req);
        prgbar=findViewById(R.id.prgBar2);

        prgbar.setVisibility(View.INVISIBLE);




        if(ContextCompat.checkSelfPermission(cam.this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(cam.this,new String[]{Manifest.permission.CAMERA},100);
        }
        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    upload();
            }
        });
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==100) {
            captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);

        }
       }

    private void upload()
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        captureImage.compress(Bitmap.CompressFormat.JPEG,100,stream);

        final String random= UUID.randomUUID().toString();
        StorageReference imgref=reference.child("image/"+random);

        byte[] b=stream.toByteArray();
        imgref.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloaduri=uri;

                        Model model=new Model(uri.toString());
                        String modelId=root.push().getKey();
                        root.child(modelId).setValue(model);
                    }
                });
                //Toast.makeText(cam.this,"upload complete",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),success.class);
                intent.putExtra("success_msg","Request sent successfully!");
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(cam.this,"upload failed"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

   }

