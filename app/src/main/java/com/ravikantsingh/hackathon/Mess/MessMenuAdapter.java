package com.ravikantsingh.hackathon.Mess;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.ravikantsingh.hackathon.R;

import java.util.List;

public class MessMenuAdapter extends RecyclerView.Adapter<MessMenuAdapter.ViewHolder> {

    Context context;
    public List<MessMenu> messMenuList;
    String userUID = "";

    public MessMenuAdapter() {
    }

    public MessMenuAdapter(Context context, List<MessMenu> messMenuList) {
        this.context = context;
        this.messMenuList = messMenuList;
        try {
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_mess_menu, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        MessMenu messMenu = messMenuList.get(i);
        viewHolder.simpleDraweeView.setImageURI(Uri.parse(messMenuList.get(i).getItemName()));


    }

    @Override
    public int getItemCount() {
        return messMenuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        SimpleDraweeView simpleDraweeView;

        public ViewHolder(@NonNull View view) {
            super(view);

            simpleDraweeView=view.findViewById(R.id.highlights_card_imageView);


        }
    }
}
