package com.ravikantsingh.hackathon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ravikantsingh.hackathon.Admin.Complain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeaveActivity extends AppCompatActivity {
    Button savebtn;
    EditText dol,dor,reason,address;
    List<Complain> complains;
    ImageView backButton;
    String userUID = "";


    DatabaseReference databaseReference,userdb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_form);

        try {
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){

        }

        savebtn=(Button)findViewById(R.id.saveBtn);
        dol=findViewById(R.id.tv9);
        dor=findViewById(R.id.tv11);
        reason=findViewById(R.id.tv13);
        address=findViewById(R.id.tvaddress);

        backButton=findViewById(R.id.registrationsBack);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference=FirebaseDatabase.getInstance().getReference().child("Leave");
                databaseReference=databaseReference.child(userUID);
                userdb=FirebaseDatabase.getInstance().getReference().child("users").child(userUID);
                userdb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        databaseReference.child("status").setValue("under process");
                        databaseReference.child("Name").setValue(dataSnapshot.child("name").getValue());
                        databaseReference.child("Year").setValue(String.valueOf(dataSnapshot.child("yearOfCompletion").getValue()));
                        databaseReference.child("Room").setValue(String.valueOf(dataSnapshot.child("room").getValue()));
                        databaseReference.child("reason").setValue(reason.getText().toString());
                        databaseReference.child("dol").setValue(dol.getText().toString());
                        databaseReference.child("dor").setValue(dor.getText().toString());
                        databaseReference.child("address").setValue(address.getText().toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                Toast.makeText(LeaveActivity.this,"Your Application has been submitted successfully",Toast.LENGTH_LONG).show();
            }
        });
        complains=new ArrayList<>();

    }
}
