package com.ravikantsingh.hackathon;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MedicineRecyclerViewAdapter extends RecyclerView.Adapter<MedicineRecyclerViewAdapter.ViewHolder> {
    private Context context;
    List<MedicineModalClass> modalClassList;


    public MedicineRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public MedicineRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.element_general_medical_services,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MedicineRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.applicantname.setText(modalClassList.get(position).getApplicantname());
        holder.datetext.setText(modalClassList.get(position).getRoomno());
        holder.bloodgroup.setText(modalClassList.get(position).getMedicinename());
        holder.textdescription.setText(modalClassList.get(position).getDiseasename());

        holder.contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verifyintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://verify.pharmasecure.com/india/#.XEuNs88zbvE"));
                context.startActivity(verifyintent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modalClassList.size();
    }

    public MedicineRecyclerViewAdapter(Context context, List<MedicineModalClass> modalClassList) {
        this.context = context;
        this.modalClassList = modalClassList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView applicantname;
        private TextView datetext;
        private TextView bloodgroup;
        private TextView textdescription;
        private Button contacts;

        public ViewHolder(View itemView) {
            super(itemView);

            applicantname=itemView.findViewById(R.id.username);
            datetext=itemView.findViewById(R.id.roomnumber);
            bloodgroup=itemView.findViewById(R.id.medicinename);
            textdescription=itemView.findViewById(R.id.diseasename);
            contacts=itemView.findViewById(R.id.seecomposition);
        }
    }
}
