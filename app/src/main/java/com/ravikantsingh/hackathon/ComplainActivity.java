package com.ravikantsingh.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ComplainActivity extends Fragment {
    LinearLayout linearlayout,linearlayout2,linearlayout3,linearLayout4;
    DatabaseReference databaseReference;
    String userUID;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view=inflater.inflate(R.layout.complain_element,null);

        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        linearlayout = (LinearLayout) view.findViewById(R.id.complain_mess);
        linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MessComplainsActivity.class);
                startActivity(intent);
            }
        });
        linearlayout2=(LinearLayout)view.findViewById(R.id.complain_hostel);
        linearlayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), HostelComplaintActivity.class);
                startActivity(intent);
            }
        });
        linearlayout3=(LinearLayout)view.findViewById(R.id.miscllenious) ;
        linearlayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Miscellinous.class);
                startActivity(intent);
            }
        });
        linearLayout4=(LinearLayout)view.findViewById(R.id.LeaveForm) ;
        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference=FirebaseDatabase.getInstance().getReference().child("Leave").child(userUID);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(String.valueOf(dataSnapshot.child("Name").getValue()).equalsIgnoreCase("null")){
                            Intent intent = new Intent(view.getContext(), LeaveActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(view.getContext(),"Your Application is "+String.valueOf(dataSnapshot.child("status").getValue()),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });




        return view;
    }



}
