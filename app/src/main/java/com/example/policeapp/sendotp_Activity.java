package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class sendotp_Activity extends AppCompatActivity {

    Button btn_cmp;
    EditText mobileno;
    String phn_no,aadhar,email,pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp_);

        aadhar=getIntent().getStringExtra("adhar");
        email=getIntent().getStringExtra("email");
        pass=getIntent().getStringExtra("pass");

        mobileno=(EditText)findViewById(R.id.inputMobile);
        btn_cmp=(Button)findViewById(R.id.buttonGetOTP);
        btn_cmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phn_no= "+91" + mobileno.getText().toString();
                Intent intent=new Intent(getApplicationContext(), VerificationActivity.class);
                intent.putExtra("mobile_no",phn_no);
                intent.putExtra("adhar",aadhar);
                intent.putExtra("email",email);
                intent.putExtra("pass",pass);
                startActivity(intent);
            }
        });

    }
}