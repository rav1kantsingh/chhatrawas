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

public class Leave extends AppCompatActivity {

    DatabaseReference reference, user;
    List<AdminModal> list;
    String name;
    RecyclerView recyclerView;
    AdminRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave);
        list = new ArrayList<>();
        recyclerView = findViewById(R.id.leave_recycler);
        adapter = new AdminRecyclerAdapter(this, list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().child("Leave");
        user = FirebaseDatabase.getInstance().getReference().child("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.e("datasnapshot_complain", snapshot.toString());
                    String userUid = String.valueOf(snapshot.child("useruid").getValue());
                    user.child(userUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot po) {
                            for (DataSnapshot snapshot1 : po.getChildren()) {
                                name = String.valueOf(snapshot1.child("name").getValue());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    Log.e("datasnapshot_complain", String.valueOf(name));
                    list.add(new AdminModal(name,
                            String.valueOf(snapshot.child("dol").getValue()),
                            String.valueOf(snapshot.child("reason").getValue()),
                            String.valueOf(snapshot.child("ImageURL").getValue())));

                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}

