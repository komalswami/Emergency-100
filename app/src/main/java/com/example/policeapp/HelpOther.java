package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HelpOther extends AppCompatActivity {
    Button btn_5;
    Button btn_report;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText t_name,t_age,t_address,t_crm;

    String name,age,address,crmtype,dgender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_other);

        t_name=(EditText)findViewById(R.id.ht_name);
        t_age=(EditText)findViewById(R.id.ht_age);
        t_address=(EditText)findViewById(R.id.ht_add);
        t_crm=(EditText)findViewById(R.id.ht_crm);

        btn_5=(Button)findViewById(R.id.button5);
        btn_report=(Button)findViewById(R.id.button6);

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup=(RadioGroup)findViewById(R.id.radiogrp);
                int rdid=radioGroup.getCheckedRadioButtonId();
                radioButton=findViewById(rdid);
                dgender= String.valueOf(radioButton.getText());

                name=t_name.getText().toString();
                age=t_age.getText().toString();
                crmtype=t_crm.getText().toString();
                address=t_address.getText().toString();

                FirebaseDatabase root= FirebaseDatabase.getInstance();
                DatabaseReference reference=root.getReference().child("Help_Other");

                HelpOtherHelperClass addNewUser=new HelpOtherHelperClass(name,age,crmtype,address,dgender);
                reference.child(name).setValue(addNewUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(HelpOther.this, "Report sent successfully!", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getApplicationContext(), complain.class));
                        }
                        else
                        {
                            Toast.makeText(HelpOther.this, "error!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });


            }
        });

        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencam();
            }
        });
    }

    private void opencam() {

            Intent intent=new Intent(this,cam.class);
            startActivity(intent);
    }
}