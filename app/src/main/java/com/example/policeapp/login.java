package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    Button login;
    EditText email, pass;
    FirebaseAuth fAuth;
    ProgressBar prgbar;
    EditText e_aadhar1;
    String username, password;
    TextView fgt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_login);
        email = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.pass);
        prgbar = (ProgressBar) findViewById(R.id.prgbar2);

        fgt=(TextView)findViewById(R.id.textView2);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!= null)
        {
            startActivity(new Intent(getApplicationContext(), Registration.class));
            finish();
        }

        fgt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),forgotPassword_Activity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = email.getText().toString().trim();
                password = pass.getText().toString().trim();


                prgbar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login.this, "logged in successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Registration.class));
                        } else {
                            Toast.makeText(login.this, "error!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            prgbar.setVisibility(View.GONE);
                        }

                    }


                });

            }
        });

    }



    public void gotomain(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}