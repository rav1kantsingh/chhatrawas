package com.ravikantsingh.hackathon;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DonateBlood extends AppCompatActivity {
    private EditText donorname,mobileno,age,weight;
    RadioButton male,female,yes1,no1,yes2,no2;
    TextView datetext,timetext;
    Button submit;
    Spinner mSpinner1,mSpinner2;
    String donor="",phone="",age1="",weight1="",bloodgroup="",quantityofblood="";
    boolean blood_donor = false;
    DatabaseReference reference;
    ArrayList<String> bloodgrouplist = new ArrayList<>();
    ArrayList<String> quantitylist = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood);

        donorname = findViewById(R.id.donor_name);
        mobileno = findViewById(R.id.mobileno);
        age = findViewById(R.id.agetext);
        weight = findViewById(R.id.weighttext);
        mSpinner1= findViewById(R.id.bloodgroupspinner);
        mSpinner2 = findViewById(R.id.quantity);
        datetext = findViewById(R.id.datetext);
        timetext = findViewById(R.id.timetext);
        submit = findViewById(R.id.submitbtn);

        addlisttospinner();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkfilleddata();

            }
        });

        timetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimepicker;
                mTimepicker = new TimePickerDialog( DonateBlood.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int selectedhour, int minute) {

                        timetext.setText(selectedhour + ":" + minute);

                    }
                },hour,minute,true);
                mTimepicker.setTitle("Select Time");
                mTimepicker.show();

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        mCurrentTime.set(Calendar.YEAR,year);
                        mCurrentTime.set(Calendar.MONTH,month);
                        mCurrentTime.set(Calendar.MONTH,dayOfMonth);
                        String myFormat = "MM/dd/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        datetext.setText(sdf.format(mCurrentTime.getTime()));

                    }
                };

                datetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(DonateBlood.this,date,mCurrentTime.get(Calendar.YEAR),mCurrentTime.get(Calendar.MONTH),
                                mCurrentTime.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });
            }
        });


    }

    private void addlisttospinner() {

        bloodgrouplist.add("Select Blood Group");
        bloodgrouplist.add("A+");
        bloodgrouplist.add("A-");
        bloodgrouplist.add("B+");
        bloodgrouplist.add("B-");
        bloodgrouplist.add("AB+");
        bloodgrouplist.add("AB-");
        bloodgrouplist.add("O+");
        bloodgrouplist.add("O-");


        quantitylist.add("1 unit");
        quantitylist.add("2 unit");
        quantitylist.add("Not specified");

        setAdaptertospinner();
    }

    private void setAdaptertospinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, bloodgrouplist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, quantitylist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner2.setAdapter(adapter1);

        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bloodgroup = mSpinner1.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mSpinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                quantityofblood = mSpinner2.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkfilleddata() {

        donor=donorname.getText().toString();
        phone = mobileno.getText().toString();
        age1=age.getText().toString();
        weight1 = weight.getText().toString();

        if(!TextUtils.isEmpty(donor)&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(age1)&&!TextUtils.isEmpty(weight1)&&!bloodgroup.equals("")){

            reference = FirebaseDatabase.getInstance().getReference().child("donor-info");
            reference.child("name").setValue(donor);
            reference.child("mobile").setValue(phone);
            reference.child("age").setValue(age1);
            reference.child("weight").setValue(weight1);
            reference.child("active-donor").setValue(blood_donor);
            reference.child("blood-group").setValue(bloodgroup);
            reference.child("quantity").setValue(quantityofblood);



        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please fill your full details", Toast.LENGTH_LONG);
        }

    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.yes:
                if (checked)
                    blood_donor = true;
                    break;
            case R.id.no:
                if (checked)
                    blood_donor = false;
                    break;
        }
    }



}
