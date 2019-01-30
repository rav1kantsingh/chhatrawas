package com.ravikantsingh.hackathon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddMedicineActivity extends AppCompatActivity {
    private EditText username,roomno,diseasename,medicinename,brandname,composition,expirydate;
    private Button addbtn;
    private DatabaseReference mRefrence;
    private String username1="",roomno1="",diseasename1="",medicinename1="",brandname1="",composition1="",expirydate1="";
    private HashMap<Object,String> medicine= new HashMap<Object,String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        username = findViewById(R.id.yourname);
        roomno=findViewById(R.id.roomno);
        diseasename=findViewById(R.id.diseasename);
        medicinename=findViewById(R.id.medicinename);
        brandname=findViewById(R.id.brandname);
        composition=findViewById(R.id.composition);
        expirydate=findViewById(R.id.expirydate);

        addbtn=findViewById(R.id.addbtn);




        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkfilldata();
            }

            private void checkfilldata() {

                username1=username.getText().toString();
                roomno1=roomno.getText().toString();
                diseasename1=diseasename.getText().toString();
                medicinename1=medicinename.getText().toString();
                brandname1 = brandname.getText().toString();
                composition1=composition.getText().toString();
                expirydate1=expirydate.getText().toString();

                if(!TextUtils.isEmpty(username1)&&!TextUtils.isEmpty(roomno1)&&!TextUtils.isEmpty(diseasename1)
                        &&!TextUtils.isEmpty(medicinename1)&&!TextUtils.isEmpty(brandname1)&&!TextUtils.isEmpty(composition1)&&!TextUtils.isEmpty(expirydate1))
                {
                    medicine.put("username",username1);
                    medicine.put("roomno",roomno1);
                    medicine.put("diseasename",diseasename1);
                    medicine.put("brandname",brandname1);
                    medicine.put("medicinename",medicinename1);
                    medicine.put("composition",composition1);
                    medicine.put("expirydate",expirydate1);

                    mRefrence= FirebaseDatabase.getInstance().getReference().child("medicine-available");
                    mRefrence.push().setValue(medicine);


                    username.setText("");
                    roomno.setText("");
                    diseasename.setText("");
                    medicinename.setText("");
                    composition.setText("");
                    expirydate.setText("");

                    Toast.makeText(AddMedicineActivity.this,"Medicine added successfully!", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(AddMedicineActivity.this,"Please add full details of medicine you have.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
