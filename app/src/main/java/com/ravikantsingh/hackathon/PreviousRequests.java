package com.ravikantsingh.hackathon;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PreviousRequests extends AppCompatActivity {

    RecyclerView medicineRecyclerView;
    PreviousRequestsAdapter medicineRecyclerViewAdapter;
    DatabaseReference mdatabase;
    private List<ModalClass> modalClassList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_requests);

        medicineRecyclerView=findViewById(R.id.previousrequest);
        modalClassList = new ArrayList<>();
        medicineRecyclerViewAdapter = new PreviousRequestsAdapter(this,modalClassList);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Blood-request");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        medicineRecyclerView.setLayoutManager(linearLayoutManager);
        medicineRecyclerView.setAdapter(medicineRecyclerViewAdapter);

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e("DataSnapshot",dataSnapshot.toString());
                modalClassList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    modalClassList.add(new ModalClass(String.valueOf(ds.child("name").getValue()),
                            String.valueOf(ds.child("bloodgroup").getValue()),
                            String.valueOf(ds.child("date").getValue()),
                            String.valueOf(ds.child("time").getValue()),
                            String.valueOf(ds.child("description").getValue())));
                    medicineRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
