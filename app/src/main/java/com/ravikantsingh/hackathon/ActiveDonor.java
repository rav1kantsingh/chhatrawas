package com.ravikantsingh.hackathon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ActiveDonor extends AppCompatActivity {
    RecyclerView medicineRecyclerView;
    PreviousRequestsAdapter medicineRecyclerViewAdapter;
    DatabaseReference mdatabase;
    private List<ModalClass> modalClassList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element_active_donor);

    }
}
