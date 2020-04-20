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

public class MessComplainsActivity extends AppCompatActivity {
 Button savebtn;
 TextView mName,mRoll,mYear,mBranch,mContact;
 private EditText title,problem,suggestions;
 List<Complain> complains;
 ImageView backButton;
 String userUID="";


 DatabaseReference databaseReference,userdb;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mess_complain_element);

        try {
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){

        }
        savebtn=(Button)findViewById(R.id.saveBtn);
        mName=findViewById(R.id.name_tv);
        mRoll=findViewById(R.id.roll_tv);
        mBranch=findViewById(R.id.branch_tv);
        mYear=findViewById(R.id.branch_tv);
        mContact=findViewById(R.id.contact_tv);
        title=findViewById(R.id.title_tv);
        problem=findViewById(R.id.problem);
        backButton=findViewById(R.id.registrationsBack);
        suggestions=findViewById(R.id.write_suggestion_tv);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference= FirebaseDatabase.getInstance().getReference().child("complaints");

                String key=databaseReference.push().getKey();
                databaseReference.child(key).child("Title").setValue(String.valueOf(title.getText()));
                databaseReference.child(key).child("content").setValue(String.valueOf(problem.getText()));
                databaseReference.child(key).child("suggestions").setValue(String.valueOf(suggestions.getText()));
                databaseReference.child(key).child("department").setValue(String.valueOf("mess"));
                databaseReference.child(key).child("status").setValue("submitted");
                databaseReference.child(key).child("useruid").setValue(userUID);
                databaseReference.child(key).child("name").setValue(mName.getText());
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
                Date d = new Date();
                String date = sdf.format(d);
                databaseReference.child(key).child("time").setValue(date);



                Toast.makeText(MessComplainsActivity.this,"Your Complain has been submitted successfully",Toast.LENGTH_LONG).show();
            }
        });
        complains=new ArrayList<>();
     databaseReference= FirebaseDatabase.getInstance().getReference().child("complaints");
     userdb= FirebaseDatabase.getInstance().getReference().child("users").child(userUID);
     userdb.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             mName.setText(String.valueOf(dataSnapshot.child("name").getValue()));
             mRoll.setText(String.valueOf(dataSnapshot.child("roll").getValue()));
             mYear.setText(String.valueOf(dataSnapshot.child("year").getValue()));
             mBranch.setText(String.valueOf(dataSnapshot.child("room").getValue()));
             mContact.setText(String .valueOf(dataSnapshot.child("contact").getValue()));

         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
    }
}
