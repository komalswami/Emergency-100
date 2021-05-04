package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class help_other_fragment extends Fragment {

    Button btn_5;
    Button btn_report;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText t_name,t_age,t_address,t_crm;

    String name,age,address,crmtype,dgender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help_other, container, false);

        t_name=view.findViewById(R.id.dash_name);
        t_age=view.findViewById(R.id.dash_age);
        t_address=view.findViewById(R.id.ht_add);
        t_crm=view.findViewById(R.id.dash_crm);

        btn_5=view.findViewById(R.id.button5);
        btn_report=view.findViewById(R.id.button6);

        btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup=view.findViewById(R.id.radiogrp);
                int rdid=radioGroup.getCheckedRadioButtonId();
                radioButton=view.findViewById(rdid);
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
                            Toast.makeText(getActivity(), "Report sent successfully!", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getApplicationContext(), complain.class));
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "error!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

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

        return view;
    }

    private void opencam() {

        Intent intent=new Intent(getContext(),cam.class);
        startActivity(intent);
    }
}