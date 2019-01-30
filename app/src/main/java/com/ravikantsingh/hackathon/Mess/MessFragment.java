package com.ravikantsingh.hackathon.Mess;


        import android.app.Dialog;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.PagerSnapHelper;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;
        import com.ravikantsingh.hackathon.R;

        import java.util.ArrayList;
        import java.util.List;

public class MessFragment extends Fragment {

    private String userUID = "";
    private String myUserUID;
    private DatabaseReference dbref,dref1;
    private Dialog couponDialog;
    private LinearLayout couponsButton,goingOutButton;
    public TextView goingOut_tv;
    private RecyclerView recyclerViewMenu;
    private List<MessMenu> messMenuList;
    private FloatingActionButton fab;
    DatabaseReference TodayDatabase;
    MessMenuAdapter messMenuAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.frag_mess,null);
        try {
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){

        }
        couponDialog=new Dialog(view.getContext());
        couponDialog.setContentView(R.layout.popup_coupons);

        messMenuAdapter=new MessMenuAdapter(getContext(),messMenuList);

        recyclerViewMenu=(RecyclerView)view.findViewById(R.id.menu_recycler_view);


        messMenuAdapter=new MessMenuAdapter(getContext(),messMenuList);

        final FragmentActivity f = getActivity();

        final LinearLayoutManager layoutManager3= new LinearLayoutManager(f);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);

        recyclerViewMenu.setLayoutManager(layoutManager3);

        PagerSnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewMenu);
        recyclerViewMenu.setBackgroundColor(000000);
        recyclerViewMenu.addItemDecoration(new CirclePagerIndicatorDecoration());



        recyclerViewMenu.setAdapter(messMenuAdapter);

        dbref=FirebaseDatabase.getInstance().getReference().child("users").child(userUID).child("coupons");

        couponsButton=view.findViewById(R.id.coupons_issued_linear);
        goingOutButton=view.findViewById(R.id.going_out_linear);
        goingOut_tv=view.findViewById(R.id.tv_going_out);

        couponDialog.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        final TextView total = couponDialog.findViewById(R.id.total_coupons);
        final TextView issued_coup = couponDialog.findViewById(R.id.issued_coupons);
        final TextView remain_coup = couponDialog.findViewById(R.id.remaining_coupons);
        final TextView remain_amount = couponDialog.findViewById(R.id.remaining_balance);
        final Button okButton= couponDialog.findViewById(R.id.okButton_coupon_dialog);


        couponsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        couponDialog.setCancelable(true);
                        couponDialog.setCanceledOnTouchOutside(true);
                        couponDialog.show();


                        Log.e("totalcoup",dataSnapshot.toString());

                        couponDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                        total.setText(String.valueOf(dataSnapshot.child("total").getValue()));
                        issued_coup.setText(String.valueOf(dataSnapshot.child("issued").getValue()));
                        try {
                            remain_coup.setText(""+(Integer.parseInt(String.valueOf(dataSnapshot.child("total").getValue()))-Integer.parseInt(String.valueOf(dataSnapshot.child("issued").getValue()))));

                        }
                        catch (Exception e){}
                        remain_amount.setText("Rs. "+Integer.parseInt(String.valueOf(remain_coup.getText()))*500);

                        okButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                couponDialog.dismiss();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });
        dref1=FirebaseDatabase.getInstance().getReference().child("mess").child("going_out").child(userUID);

        dref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("null")) {
                    goingOutButton.setBackgroundColor(Color.parseColor("#c1cc174a"));
                    goingOut_tv.setText("Going Out??\nSave Food!");

                }
                else {
                    goingOutButton.setBackgroundColor(Color.parseColor("#009933"));
                    goingOut_tv.setText("Thanks!\nYou are saving Food!");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        goingOutButton=view.findViewById(R.id.going_out_linear);
        goingOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("null")){
                            dref1.setValue("0");
                            goingOutButton.setBackgroundColor(Color.parseColor("#009933"));
                            goingOut_tv.setText("Thanks!\nYou are saving Food!");

                        }
                        else {
                            dref1.removeValue();
                            goingOutButton.setBackgroundColor(Color.parseColor("#c1cc174a"));
                            goingOut_tv.setText("Going Out??\nSave Food!");


                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });




//        Log.e("USERIT",userUID);
//        myUserUID=FirebaseAuth.getInstance().getCurrentUser().getUid();





        return view;


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        messMenuList=new ArrayList<>();


        messMenuList.add(new MessMenu("http://4.bp.blogspot.com/-IcGZGucW1-o/UCf-VEax0yI/AAAAAAAAFnU/bMHzUG3T1pM/s1600/Menu.JPG"));
        messMenuList.add(new MessMenu("http://4.bp.blogspot.com/-IcGZGucW1-o/UCf-VEax0yI/AAAAAAAAFnU/bMHzUG3T1pM/s1600/Menu.JPG"));

        messMenuList.add(new MessMenu("http://4.bp.blogspot.com/-IcGZGucW1-o/UCf-VEax0yI/AAAAAAAAFnU/bMHzUG3T1pM/s1600/Menu.JPG"));
        messMenuList.add(new MessMenu("http://4.bp.blogspot.com/-IcGZGucW1-o/UCf-VEax0yI/AAAAAAAAFnU/bMHzUG3T1pM/s1600/Menu.JPG"));




    }
}
