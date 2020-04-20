package com.ravikantsingh.hackathon;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MyDashboard extends Fragment {

    TextView codetw, name, hostel, contact, room;
    ImageView image;
    String userUID, nameStr, hostelStr, contactStr, roomStr, codeStr, photoURl, codeGenerated,code = "",newcode ="";
    DatabaseReference reference;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_dashboard, container, false);

        codetw = view.findViewById(R.id.code);
        name = view.findViewById(R.id.name);
        hostel = view.findViewById(R.id.hostel);
        contact = view.findViewById(R.id.contact);
        room = view.findViewById(R.id.room);
        image = view.findViewById(R.id.photo);
        try {
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){
        }
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userUID);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("log123",dataSnapshot.toString());
                codeGenerated = String.valueOf(dataSnapshot.child("codeGenerated").getValue());
                if (codeGenerated.equals("0")||!dataSnapshot.child("codeGenerated").exists()) {
                    //code never generated.
                    //generate code.
                    Log.e("code", code);
                    reference.child("codeGenerated").setValue("1");
                    Random random = new Random();
                    newcode = String.format("%04d", random.nextInt(10000));
                    reference.child("code").setValue(newcode);
                    if(!(code.equals("null")||code.equals(""))){
                        codetw.setText(code);
                        image.setImageURI(Uri.parse(String.valueOf(dataSnapshot.child("photoURL").getValue())));

                    }
                } else {
                    code = String.valueOf(dataSnapshot.child("code").getValue());
                    if(!(code.equals("null")||code.equals(""))){
                        codetw.setText(code);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.e("log321", String.valueOf(dataSnapshot.child("name").getValue()));
                name.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                hostel.setText(String.valueOf(dataSnapshot.child("hostel").getValue()));
                contact.setText(String.valueOf(dataSnapshot.child("phoneNumber").getValue()));
                room.setText(String.valueOf(dataSnapshot.child("room").getValue()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return view;
    }

}
