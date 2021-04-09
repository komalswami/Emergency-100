package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    String CodeBySystem,_what,_phoneNo;
    private TextView resend_otp;
    private PinView pn_v;
    private Button verify;
    String aadhar1,email1,pass1;
    FirebaseAuth mauth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);


        pn_v=(PinView)findViewById(R.id.pin_view);
        verify=(Button)findViewById(R.id.buttonVerify);
        resend_otp=(TextView)findViewById(R.id.textResendOTP);

        _phoneNo=getIntent().getStringExtra("mobile_no");

        aadhar1=getIntent().getStringExtra("adhar");
        email1=getIntent().getStringExtra("email");
        pass1=getIntent().getStringExtra("pass");

        TextView textMobile = findViewById(R.id.textMobile);

        textMobile.setText(_phoneNo);

        sendVerificationCodeToUser(_phoneNo);

        resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCodeToUser(_phoneNo);
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=pn_v.getText().toString();

                if(!code.isEmpty()){
                    verifyCode(code);
                }

            }
        });

    }
    private void verifyCode(String code) {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(CodeBySystem,code);
        signInWithPhoneAuthCredential(credential);
    }
    private void sendVerificationCodeToUser(String phoneNo) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mauth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(VerificationActivity.this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            CodeBySystem=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code=phoneAuthCredential.getSmsCode();
                if(code!=null){
                    pn_v.setText(code);
                    verifyCode(code);
                }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(VerificationActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           Toast.makeText(VerificationActivity.this,"Phone Number Verified",Toast.LENGTH_LONG).show();

                               uploadData();
                               startActivity(new Intent(getApplicationContext(),complain.class));

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerificationActivity.this,"Verification not completed",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void uploadData() {
        //name,phoneNo,Aadhar,aadress(lat,loc,locality,adrline,country),gender,email,pass

        FirebaseDatabase rootnode= FirebaseDatabase.getInstance();
        DatabaseReference reference=rootnode.getReference().child("Users");

        regHelperClass addReg=new regHelperClass(email1,aadhar1,pass1,_phoneNo);
        reference.child(_phoneNo).setValue(addReg).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(VerificationActivity.this, "Reg successfully!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(), complain.class));
                    }
                    else{
                        Toast.makeText(VerificationActivity.this, "error!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
            }
        });

    }
}