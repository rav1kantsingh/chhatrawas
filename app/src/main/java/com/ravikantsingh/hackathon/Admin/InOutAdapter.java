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

public class InOutAdapter extends RecyclerView.Adapter<InOutAdapter.ViewHolder> {
    @NonNull
    private Context context;
    List<In_out_modalClass> list;

    public InOutAdapter(Context context,List<In_out_modalClass> list){
        this.context = context;
        this.list = list;
    }

    public InOutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.in_out_card, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final InOutAdapter.ViewHolder viewHolder, int i) {
        if(!String.valueOf(list.get(i).getName()).equals("null")){
            viewHolder.name.setText(list.get(i).getName());
        }else {
            viewHolder.name.setText("Ravi kant");
        }
        viewHolder.time.setText(list.get(i).getTime());
        viewHolder.message.setText(list.get(i).getMessage());
        viewHolder.time.setText(list.get(i).getTime());
        viewHolder.room.setText(list.get(i).roomNo);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,time,message,room;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.in_name);
            time = itemView.findViewById(R.id.in_time);
            message = itemView.findViewById(R.id.in_reason);
            room = itemView.findViewById(R.id.in_room);
        }
    }
}


