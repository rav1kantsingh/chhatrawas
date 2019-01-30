package com.ravikantsingh.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ravikantsingh.hackathon.Admin.Complain;
import com.ravikantsingh.hackathon.Admin.Emergency;
import com.ravikantsingh.hackathon.Admin.Leave;
import com.ravikantsingh.hackathon.Admin.in_out;

public class AdminPanel extends AppCompatActivity{

    TextView complain,in_out,leave,emergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        complain = findViewById(R.id.complain);
        in_out = findViewById(R.id.in_out);
        leave = findViewById(R.id.leave);
        emergency = findViewById(R.id.emergency);


        complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this,Complain.class));
            }
        });

        in_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this,in_out.class));
            }
        });
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this,Leave.class));
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPanel.this,Emergency.class));
            }
        });

    }
}
