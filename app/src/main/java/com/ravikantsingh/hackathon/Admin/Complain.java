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

public class Complain extends AppCompatActivity {

    DatabaseReference reference,user;
    List<AdminModal> list;
    String name;
    RecyclerView recyclerView;
    AdminRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.complain_recycler);
        adapter = new AdminRecyclerAdapter(this,list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().child("complaints");
        user = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                Log.e("log",dataSnapshot.toString());
                for(final DataSnapshot snapshot:dataSnapshot.getChildren()) {

                list.add(new AdminModal(String.valueOf(snapshot.child("name").getValue()),
                        String.valueOf(snapshot.child("time").getValue()),
                        String.valueOf(snapshot.child("content").getValue()),
                        String.valueOf(snapshot.child("ImageURL").getValue())));

                adapter.notifyDataSetChanged();
            }}
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
