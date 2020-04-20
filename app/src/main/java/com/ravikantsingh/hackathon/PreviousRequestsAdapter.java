package com.ravikantsingh.hackathon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class PreviousRequestsAdapter extends RecyclerView.Adapter<PreviousRequestsAdapter.ViewHolder> {
    private Context context;
    List<ModalClass> modalClassList;


    public PreviousRequestsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.element_previous_requests,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.applicantname.setText(modalClassList.get(position).getApplicantname());
        holder.datetext.setText("Date :"+modalClassList.get(position).getDate());
        holder.bloodgroup.setText(modalClassList.get(position).getBlooggroup());
        holder.textdescription.setText(modalClassList.get(position).getDescription());
        holder.timetext.setText(modalClassList.get(position).getTime());

        holder.giveresponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.giveresponse.setText("Response Added");
            }
        });
        holder.contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.contacts.setText("Call-8114536004");
            }
        });

    }

    @Override
    public int getItemCount() {
        return modalClassList.size();
    }

    public PreviousRequestsAdapter(Context context, List<ModalClass> modalClassList) {
        this.context = context;
        this.modalClassList = modalClassList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView applicantname;
        private TextView datetext;
        private TextView bloodgroup;
        private TextView textdescription;
        private Button giveresponse;
        private Button contacts;
        private TextView timetext;

        public ViewHolder(View itemView) {
            super(itemView);

            applicantname=itemView.findViewById(R.id.applicantname);
            datetext=itemView.findViewById(R.id.dateview);
            bloodgroup=itemView.findViewById(R.id.bloodgroup);
            textdescription=itemView.findViewById(R.id.textdescription);
            timetext=itemView.findViewById(R.id.timeview);
            giveresponse=itemView.findViewById(R.id.responsebtn);
            contacts=itemView.findViewById(R.id.contactbtn);
        }
    }
}
