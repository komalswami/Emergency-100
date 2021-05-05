package com.example.policeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class success extends AppCompatActivity {

    TextView textView;
    String msg;
    Button _back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        textView=(TextView)findViewById(R.id.message);
        msg=getIntent().getStringExtra("success_msg");
        _back=(Button)findViewById(R.id.back);

        textView.setText(msg);

        _back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity2.class));
            }
        });
    }
}