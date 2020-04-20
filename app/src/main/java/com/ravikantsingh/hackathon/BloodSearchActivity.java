package com.ravikantsingh.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;

public class BloodSearchActivity extends AppCompatActivity {


    private CardView requestBlood;
    private CardView donateBlood;
    private CardView activeDonor;
    private CardView previousrequest;

    private Button searchBloodBank;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_search);
        FirebaseApp.initializeApp(this);

        requestBlood = findViewById(R.id.request);
        donateBlood = findViewById(R.id.donate);
        activeDonor = findViewById(R.id.donor);
        previousrequest = findViewById(R.id.requestlist);

        searchBloodBank = findViewById(R.id.searchbtn);
        searchBloodBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:25.6175743,85.1763928,14z?q=bloodbanks");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        requestBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestintent = new Intent(BloodSearchActivity.this,RequestBlood.class);
                startActivity(requestintent);
                finish();
            }
        });
        donateBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestintent = new Intent(BloodSearchActivity.this,DonateBlood.class);
                startActivity(requestintent);
                finish();
            }
        });
        activeDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestintent = new Intent(BloodSearchActivity.this,ActiveDonor.class);
                startActivity(requestintent);
                finish();
            }
        });

        previousrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent previousrequestintent = new Intent(BloodSearchActivity.this,PreviousRequests.class);
                startActivity(previousrequestintent);
                finish();

            }
        });
    }
}
