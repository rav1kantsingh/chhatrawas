package com.ravikantsingh.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GeneralMedicals extends AppCompatActivity {
    private Button addmedicine;
    RecyclerView medicineRecyclerView;
    MedicineRecyclerViewAdapter medicineRecyclerViewAdapter;
    DatabaseReference mdatabase;
    private List<MedicineModalClass> modalClassList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_medical);

        medicineRecyclerView=findViewById(R.id.generalmedicalservice);
        modalClassList = new ArrayList<>();
        medicineRecyclerViewAdapter = new MedicineRecyclerViewAdapter(this,modalClassList);
        addmedicine=findViewById(R.id.addyourmedicine);
        mdatabase = FirebaseDatabase.getInstance().getReference().child("medicine-available");
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
                    modalClassList.add(new MedicineModalClass(String.valueOf(ds.child("username").getValue()),
                            String.valueOf(ds.child("diseasename").getValue()),
                            String.valueOf(ds.child("medicinename").getValue()),
                            String.valueOf(ds.child("roomno").getValue())));
                    medicineRecyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        addmedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addmedicineintent = new Intent(GeneralMedicals.this,AddMedicineActivity.class);
                startActivity(addmedicineintent);
                finish();
            }
        });
    }
}
