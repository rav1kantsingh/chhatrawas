package com.ravikantsingh.hackathon;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ravikantsingh.hackathon.R;
import com.ravikantsingh.hackathon.StringVariable;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HostelEntry extends Fragment {

    EditText enterCode, reason;
    TextView verifyButton;
    DatabaseReference reference;
    String code, currentUserUID, downloadCode,currentUserName;
    Long downloadTimeStamp;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_hostel_entry,container,false);

        enterCode = view.findViewById(R.id.enter_code_editText);
        verifyButton = view.findViewById(R.id.verify_button);
        reason = view.findViewById(R.id.submit_reason);
        try {
            currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Reopen App", Toast.LENGTH_LONG).show();
        }
        reference = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        downloadCode = String.valueOf(dataSnapshot.child(currentUserUID).child(StringVariable.CODE).getValue());
                    } catch (Exception e) {
                        Log.e("HotelEntry", "datasnapshot not exist");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        verifyButton.setOnClickListener(new View.OnClickListener() {
            DatabaseReference enteryExit = FirebaseDatabase.getInstance().getReference().child("entery_exit");
            DatabaseReference userRefrence = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserUID);

            @Override
            public void onClick(View v) {
                if (enterCode.getText().toString().equals(downloadCode) && !reason.getText().toString().isEmpty()) {

                    userRefrence.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String reasonStr = reason.getText().toString();
                            Calendar c = Calendar.getInstance();
                            String date_time = c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR) + "&" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                            userRefrence.child("exit_time").setValue(date_time);
                            userRefrence.child("reason").setValue(reasonStr);
                            currentUserName = String.valueOf(dataSnapshot.child("name").getValue());

                            enterCode.setText("");
                            reason.setText("");
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    String reasonStr = reason.getText().toString();
                    Calendar c = Calendar.getInstance();
                    String date_time = c.get(Calendar.DAY_OF_MONTH) + "-" + c.get(Calendar.MONTH) + "-" + c.get(Calendar.YEAR) + "&" + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);
                    Map<String, String> submit = new HashMap<>();
                    submit.put("userUID", currentUserUID);
                    submit.put("exit_time",date_time);
                    submit.put("name",currentUserName);
                    submit.put("exit_reason", reasonStr);

                    enteryExit.push().setValue(submit);
                }
            }
        });
        return view;
    }

}
