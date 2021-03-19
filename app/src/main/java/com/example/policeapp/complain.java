package com.example.policeapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class complain extends AppCompatActivity {
    EditText name,aadhar,age,crimetype,crm;
    TextView btn_logout;
    RadioGroup gender;
    RadioButton radioButton;
    Button btn_panic,btn_help,report,get_add;
    private FusedLocationProviderClient fusedLocationProviderClient;
    double lat,lon;
    String country,locality,adrline;

    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        name=(EditText)findViewById(R.id.ht_name);

        aadhar=(EditText)findViewById(R.id.ht_aadhar);
        age=(EditText)findViewById(R.id.ht_age);
        crm=(EditText)findViewById(R.id.ht_crm);
        report=(Button)findViewById(R.id.btn_report);


        get_add=(Button)findViewById(R.id.get_loc);
        btn_logout=(TextView)findViewById(R.id.lgt_out);



        btn_panic=(Button)findViewById(R.id.btn_pnc);
        btn_help=(Button)findViewById(R.id.btn_5);
        btn_logout=(TextView)findViewById(R.id.lgt_out);


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getInstance().signOut();

                Toast.makeText(complain.this, "logged out successfully!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Frontpage_Activity.class));

            }
        });

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HelpOther.class));
            }
        });


        get_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(complain.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                   getthatloc();

                } else {
                    ActivityCompat.requestPermissions(complain.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        btn_panic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),cam.class));
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String crimetype=crm.getText().toString();
                String dloc="Kaij";
                String dname=name.getText().toString();
                String daadhar=aadhar.getText().toString();
                String dage=age.getText().toString();

                gender=(RadioGroup)findViewById(R.id.radioGroup2);

                int rdid=gender.getCheckedRadioButtonId();
                radioButton=findViewById(rdid);
                String dgender= String.valueOf(radioButton.getText());


                 FirebaseDatabase rootnode= FirebaseDatabase.getInstance();
                 DatabaseReference reference=rootnode.getReference().child("Users");

                 UserHelperClass addNewUser=new UserHelperClass(daadhar,dname,crimetype,dgender,dage,lat,lon,country,locality,adrline);
                 reference.child(daadhar).setValue(addNewUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(complain.this, "Report sent successfully!", Toast.LENGTH_LONG).show();
                            //startActivity(new Intent(getApplicationContext(), complain.class));
                        }
                        else
                        {
                            Toast.makeText(complain.this, "error!!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }
                    }
                });

            }
        });

    }

    public void getthatloc(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location=task.getResult();
                if(location!=null){

                    try {
                        Geocoder geocoder=new Geocoder(complain.this, Locale.getDefault());
                        List<Address> address=geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1
                        );
                        //set var
                        lat=address.get(0).getLatitude();
                        lon=address.get(0).getLongitude();
                        country=address.get(0).getCountryName();
                        adrline=address.get(0).getAddressLine(0);
                        locality=address.get(0).getLocality();
                        Toast.makeText(getApplicationContext(),"Address: "+country,Toast.LENGTH_LONG).show();

                    /*    show_loc.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Latitude:</b><br></font>"+locality
                        )); */
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


}