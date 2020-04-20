package com.ravikantsingh;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ravikantsingh.hackathon.NewPost;
import com.ravikantsingh.hackathon.NoticeRecyclerViewAdapter;
import com.ravikantsingh.hackathon.R;

import java.util.ArrayList;
import java.util.List;

public class Noticefragment extends Fragment {
    private RecyclerView recyclerView;
    private List <Notice> listNotice;
    DatabaseReference noticeDatabase,databaseDownload;
    NoticeRecyclerViewAdapter noticeRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    private FloatingActionButton fab;

    public Noticefragment(){}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.notice_recyclerview,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.NoticeRecycler);
        final FragmentActivity f = getActivity();
        noticeRecyclerViewAdapter=new NoticeRecyclerViewAdapter(getContext(),listNotice);

        fab=view.findViewById(R.id.write_post_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(view.getContext(), NewPost.class);
                startActivity(intent);
            }
        });
        layoutManager= new LinearLayoutManager(f);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noticeRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listNotice=new ArrayList<>();
        noticeRecyclerViewAdapter=new NoticeRecyclerViewAdapter(getContext(),listNotice);

        noticeDatabase = FirebaseDatabase.getInstance().getReference().child("notice");
        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference imageref = rootref.child("notice");
    noticeDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
listNotice.clear();
            for(DataSnapshot ds:dataSnapshot.getChildren()){
                Log.e("snapshot",ds.toString());

                listNotice.add(new Notice(
                        String.valueOf(ds.child("Name").getValue()),
                        String.valueOf(ds.child("time").getValue()),
                        String.valueOf(ds.child("details").getValue()),
                        String.valueOf(ds.child("ImageURL").getValue())
                ));
                noticeRecyclerViewAdapter.notifyDataSetChanged();

                Log.e("list1",listNotice.get(0).getImage());
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
           noticeRecyclerViewAdapter.notifyDataSetChanged();
        }
    });
    }
}
