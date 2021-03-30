package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class sendotp_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendotp_);

        final EditText inputMobile= findViewById(R.id.inputMobile);
        Button buttonGetOTP = findViewById(R.id.buttonGetOTP);

        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputMobile.getText().toString().trim().isEmpty()) {
                    Toast.makeText(sendotp_Activity.this, "Enter Mobile", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    String mo=inputMobile.getText().toString();
                    Toast.makeText(sendotp_Activity.this, "Mobile "+mo, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                    intent.putExtra("mobile", inputMobile.getText().toString());
                    startActivity(intent);
                }

            }
        });
    }
}