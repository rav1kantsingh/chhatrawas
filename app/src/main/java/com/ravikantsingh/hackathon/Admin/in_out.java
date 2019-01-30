package com.ravikantsingh.hackathon.Admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ravikantsingh.hackathon.R;

import java.util.ArrayList;
import java.util.List;

public class in_out extends AppCompatActivity {

    DatabaseReference reference, user;
    List<In_out_modalClass> list;
    String name;
    RecyclerView recyclerView;
    InOutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_out);
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.in_out_recycler);
        adapter = new InOutAdapter(this, list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().child("entery_exit");
        user = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e("in_out",dataSnapshot.toString());
                    String userUID = String.valueOf(snapshot.child("userUID").getValue());
                    Log.e("in_out4",userUID);
                    user.child(userUID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("in_out2",dataSnapshot.toString());
                            Log.e("in_out3",snapshot.toString());
                            String name = String.valueOf(dataSnapshot.child("name").getValue());
                            String roomNo = String.valueOf(dataSnapshot.child("room").getValue());
                            String reson = String.valueOf(snapshot.child("exit_reason").getValue());
                            String time = String.valueOf(snapshot.child("exit_time").getValue());
                            Log.e("in_out5",roomNo);
                            time = time.replace("&"," | ");
                            list.add(new In_out_modalClass(name,roomNo,reson,time));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}

