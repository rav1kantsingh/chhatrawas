package com.ravikantsingh.hackathon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

public class TestFragment extends Fragment {

    private String userUID;
    private String myUserUID;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.test_fragment, null);


        userUID = getActivity().getIntent().getStringExtra("userUID");
//        Log.e("USERIT",userUID);
//        myUserUID=FirebaseAuth.getInstance().getCurrentUser().getUid();


        return view;


    }
}
