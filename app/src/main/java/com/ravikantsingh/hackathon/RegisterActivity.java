package com.ravikantsingh.hackathon;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText register_name, register_branch, register_roll, register_year, register_phone, register_mail, register_college, Degree_registrations, blood_group;
    private Button register_male, register_female, register_trans, register_register;
    private String name, hostel, rollno, branch, mobileno, gender, year, email, degree, bloodGroup;
    private FirebaseAuth mAuth;
    private Button[] btn = new Button[3];
    private Button[] btnHostel = new Button[5];
    private Button btn_unfocus, btn_unfocus2;
    private int[] btn_id = {R.id.Male_button_registration_page, R.id.Female_button_registration_page, R.id.trans_button_registration_page};
    private int[] btn_hostel_id = {R.id.brhm, R.id.kosi, R.id.ganga, R.id.sona, R.id.sonb};
    private ImageView backButton;
    private DatabaseReference databaseReference;
    Dialog mdialog;
    View focusView;
    String curr_mail = "";
    private String key = "";
    private String imageUrl = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
//        int defaultValue = getResources().getInteger(R.integer.saved_high_score_default_key);
        int code = sharedPref.getInt("registered", 0);
        if(code==1){
            startActivity(new Intent(this,BottomNavigationActivity.class));
        }
        init();
        for (int i = 0; i < btn.length; i++) {
            btn[i] = findViewById(btn_id[i]);
            btn[i].setOnClickListener(this);
        }

        for (int i = 0; i < btnHostel.length; i++) {
            btnHostel[i] = findViewById(btn_hostel_id[i]);
            btnHostel[i].setOnClickListener(this);
        }

        btn_unfocus = btn[2];
        btn[0].performClick();
        btn_unfocus2 = btnHostel[1];
        btnHostel[0].performClick();
        backButton = findViewById(R.id.registrationsBack);
        register_register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdata();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);

        register_name = findViewById(R.id.name_registration);
        register_roll = findViewById(R.id.roll_registrations);
        register_branch = findViewById(R.id.branch_registrations);
        register_phone = findViewById(R.id.phone_registrations);
        register_year = findViewById(R.id.year_registrations);
        register_register = findViewById(R.id.registerButton_register);
        register_male = findViewById(R.id.Male_button_registration_page);
        register_female = findViewById(R.id.Female_button_registration_page);
        register_trans = findViewById(R.id.trans_button_registration_page);
        register_mail = findViewById(R.id.email_registrations);
        Degree_registrations = findViewById(R.id.Degree_registrations);
        register_mail.setEnabled(false);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Male_button_registration_page || v.getId() == R.id.Female_button_registration_page || v.getId() == R.id.trans_button_registration_page) {
            setFocus(btn_unfocus, (Button) findViewById(v.getId()));
            //           Toast.makeText(RegisterActivity.this,gender,Toast.LENGTH_LONG).show();
            switch (v.getId()) {
                case R.id.Male_button_registration_page:
                    gender = "male";
                    break;

                case R.id.Female_button_registration_page:
                    gender = "female";
                    break;

                case R.id.trans_button_registration_page:
                    gender = "trans";
                    break;
            }
        } else {
            setFocus2(btn_unfocus2, (Button) findViewById(v.getId()));
            switch (v.getId()) {
                case R.id.brhm:
                    hostel = "Brahmputra";

                    break;
                case R.id.kosi:
                    hostel = "Kosi";

                    break;
                case R.id.ganga:
                    hostel = "Ganga";

                    break;
                case R.id.sona:
                    hostel = "Son A";

                    break;
                case R.id.sonb:
                    hostel = "Son B";
                    break;
            }
        }

    }

    private void setFocus(Button btn_unfocus, Button btn_focus) {
        if (btn_unfocus == btn_focus) {
            return;
        }
        btn_focus.setBackgroundResource(R.drawable.round_button_registrations_clicked);
        btn_unfocus.setBackgroundResource(R.drawable.round_button_registrations);
        this.btn_unfocus = btn_focus;
    }

    private void setFocus2(Button btn_unfocus2, Button btn_focus) {
        if (btn_unfocus2 == btn_focus) {
            return;
        }
        btn_focus.setBackgroundResource(R.drawable.round_button_registrations_clicked);
        btn_unfocus2.setBackgroundResource(R.drawable.round_button_registrations);
        this.btn_unfocus2 = btn_focus;
    }

    private void checkdata() {
        name = register_name.getText().toString();
        rollno = register_roll.getText().toString();
        branch = register_branch.getText().toString();
        mobileno = register_phone.getText().toString();
        year = register_year.getText().toString();
        email = register_mail.getText().toString();
        degree = Degree_registrations.getText().toString();

        boolean cancel = false;
        //rollExist();
        if (mobileno.length() != 10) {
            cancel = true;
            register_phone.setError("Enter 10 digit Mobile Number");
            focusView = register_phone;
            focusView.requestFocus();
        } else if (gender.isEmpty()) {
            cancel = true;
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_LONG).show();
        } else if (hostel.isEmpty()) {
            cancel = true;
            Toast.makeText(this, "Please select your college", Toast.LENGTH_LONG).show();
        }/* else if (year.isEmpty() || year.length() > 1) {
            cancel = true;
            register_year.setError("Enter values from 1 to 5");
            focusView = register_year;
            focusView.requestFocus();
        }*/ else if (degree == null || degree.equals("")) {
            cancel = true;
            Toast.makeText(getApplicationContext(), "Enter your degree please", Toast.LENGTH_SHORT).show();
        } else if (branch.isEmpty()) {
            cancel = true;
            register_branch.setError("Enter your branch");
            focusView = register_branch;
            focusView.requestFocus();
        } else if (rollno.isEmpty()) {
            cancel = true;
            register_roll.setError("Enter your roll number");
            focusView = register_roll;
            focusView.requestFocus();
        } else if (name.isEmpty()) {
            cancel = true;
            register_name.setError("Enter name");
            focusView = register_name;
            focusView.requestFocus();
        }


        if (cancel != true)
            confirmDialog();
        // BeginRegistration();
    }

    private void confirmDialog() {
        mdialog = new Dialog(this);
        mdialog.setContentView(R.layout.pop_up_sac);
        mdialog.getWindow().getDecorView()
                .setBackgroundResource(android.R.color.transparent);
        mdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView confirmtext = mdialog.findViewById(R.id.message_edittext);
        confirmtext.setText("Do you Want to confirm your Registration ?");
        Button confirmbtn = mdialog.findViewById(R.id.yes_btn);
        confirmbtn.setText("Confirm");
        Button nobtn = mdialog.findViewById(R.id.no_btn);
        nobtn.setText("Deny");

        mdialog.show();


        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkdata();
                settingUpDetails();


            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });
    }

    private void settingUpDetails() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Registering...");
        progressDialog.show();

        curr_mail = mAuth.getCurrentUser().getEmail();
//        Log.e("SignUp1", "SettingUpDeatils the details");
        key = mAuth.getCurrentUser().getUid();

        Map<String, Object> otherDataMap = new HashMap<>();
        otherDataMap.put(StringVariable.USER_IS_PROFILE_COMPLETED, 1);
        otherDataMap.put(StringVariable.USER_PHONENUMBER_PRIVATE, 0);
        otherDataMap.put(StringVariable.PUBLISH, 0);


        // databaseReference.child(key).child(StringVariable.APP). setValue(otherDataMap);
        databaseReference.child(key).child(StringVariable.USER_PHONENUMBER).setValue(mobileno);
        databaseReference.child(key).child(StringVariable.USER_email).setValue(email);
        databaseReference.child(key).child(StringVariable.USER_name).setValue(name);
        databaseReference.child(key).child(StringVariable.USER_USER_UID).setValue(key);
        databaseReference.child(key).child(StringVariable.USER_BRANCH).setValue(branch);
        databaseReference.child(key).child("hostel").setValue(hostel);
        databaseReference.child(key).child(StringVariable.USER_IMAGE).setValue(imageUrl);
        databaseReference.child(key).child(StringVariable.USER_ROLLNO).setValue(rollno);
        databaseReference.child(key).child(StringVariable.USER_YEAR).setValue(year);
        databaseReference.child(key).child("room").setValue(degree);
        databaseReference.child(key).child(StringVariable.USER_GENDER).setValue(gender);
        databaseReference.child(key).child(StringVariable.USER_EMAIL_VERIFIED).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                nextActivity(key);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });        // startActivity(new Intent(getApplicationContext(), Dashboard.class));

//        SharedPreferences preferences = this.getSharedPreferences("Registration", this.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putInt("registered",1);
//        editor.apply();

    }

    private void nextActivity(String key) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(key);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(RegisterActivity.this, "Registrations Successful. Please restart the app to unlock the pages!", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    finish();
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }

}
