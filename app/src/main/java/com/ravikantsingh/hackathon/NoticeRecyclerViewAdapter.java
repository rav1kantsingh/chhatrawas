package com.ravikantsingh.hackathon;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ravikantsingh.Notice;

import java.util.List;

public class NoticeRecyclerViewAdapter extends  RecyclerView.Adapter<NoticeRecyclerViewAdapter.ViewHolder> {
    private  Context context;
    List<Notice> noticeList;

    public NoticeRecyclerViewAdapter(Context context, List<Notice> noticeList) {
        this.noticeList = noticeList;
        this.context=context;
    }
   public NoticeRecyclerViewAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.notice_element,viewGroup,false);
        ViewHolder holder= new ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notice");
        Notice resultObject = noticeList.get(i);
//        viewHolder.userImage.setText(resultObject.getImage());
        viewHolder.Name.setText(resultObject.getName());
        viewHolder.Time.setText(resultObject.getTime());
        viewHolder.userImage.setImageURI(Uri.parse(resultObject.getNoticeText()));
        viewHolder.notice.setText(resultObject.getImage());

        Log.e("userImg",resultObject.getNoticeText());
    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name,notice,Time;
        ImageView userImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        Name=itemView.findViewById(R.id.username);
        notice=itemView.findViewById(R.id.noticeText);
        userImage=itemView.findViewById(R.id.userImage);
        Time=itemView.findViewById(R.id.time);
        }

    }
}
