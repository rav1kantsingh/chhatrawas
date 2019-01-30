package com.ravikantsingh.hackathon.Admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ravikantsingh.hackathon.R;

import java.util.List;

public class adminEmergencyAdapter extends RecyclerView.Adapter<adminEmergencyAdapter.ViewHolder> {
    @NonNull
    private Context context;
    List<AdminModal> list;

    public adminEmergencyAdapter(Context context,List<AdminModal> list){
        this.context = context;
        this.list = list;
    }

    public adminEmergencyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.admin_list_card_complaint, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final adminEmergencyAdapter.ViewHolder viewHolder, int i) {
        if(!String.valueOf(list.get(i).getName()).equals("null")){
            viewHolder.name.setText(list.get(i).getName());
        }else {
            viewHolder.name.setText("Ravi kant");
        }
        viewHolder.time.setText(list.get(i).getTime());
        viewHolder.message.setText(list.get(i).getMessage());
        viewHolder.solved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.solved.setText("Issue Solved!!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,time,message;
        Button solved;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.username);
            time = itemView.findViewById(R.id.time);
            message = itemView.findViewById(R.id.noticeText);
            solved = itemView.findViewById(R.id.solvebtn);
        }
    }
}


