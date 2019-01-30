package com.ravikantsingh.hackathon;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewPost extends AppCompatActivity {
    DatabaseReference dbref,dbref1;
    String userUID;
    EditText postContent;
    ImageView closeimage;
    ArrayList<Uri_List> mlist = new ArrayList<>();
    Uri image_data = null;

    TextView username1;
    SimpleDraweeView profileImage;
    ImageView uploaded;
    Button publishbtn;
    private static final int IMAGE_PERMISSION_REQUEST = 0;
    private static final int PDF_PERMISSION_REQUEST = 1;
    private static final int RESULT_LOAD_PDF = 3;
    private static final int REQUEST_LOAD_IMAGE = 2;

    RelativeLayout uploadImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_activity);


        userUID= FirebaseAuth.getInstance().getCurrentUser().getUid();

        postContent=findViewById(R.id.edit_post);
        uploaded=findViewById(R.id.image_loader);
        uploadImage=findViewById(R.id.uploadImage);
        profileImage=findViewById(R.id.notice_profile_pic);
        username1=findViewById(R.id.pop_up_blog_username);
        publishbtn=findViewById(R.id.publish_btn);
        closeimage=findViewById(R.id.close_view);

        closeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        publishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(postContent.getText())) {

                    dbref = FirebaseDatabase.getInstance().getReference().child("users").child(userUID);
                    dbref1 = FirebaseDatabase.getInstance().getReference().child("notice");

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String usernameText, imageText;
                            usernameText = String.valueOf(dataSnapshot.child("name").getValue());
                            imageText = String.valueOf(dataSnapshot.child("photoURL").getValue());
                            profileImage.setImageURI(Uri.parse(imageText));
                            username1.setText(usernameText);

                            String key = dbref1.push().getKey();
                            dbref1.child(key).child("Name").setValue(usernameText);
                            //dbref1.child(key).child("ImageURL").setValue(imageText);
                            dbref1.child(key).child("details").setValue(postContent.getText().toString());
                            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                            Date todayDate = new Date();
                            String thisDate = currentDate.format(todayDate);
                            dbref1.child(key).child("time").setValue(thisDate);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    finish();
                }

//                uploadImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                Toast.makeText(NewPost.this,"",Toast.LENGTH_LONG).show();
//                        int RESULT_LOAD_IMAGE = 1;
//
//                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType("image/*");
//                        startActivityForResult(intent, 1);
//
//                    }
//                });
                uploadImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadimage();
                    }
                });

            }
        });



    }

    private void loadimage() {
        //Toast.makeText(getContext(),"The image uploaded should not be more than 2MB",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_LOAD_IMAGE);
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case IMAGE_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadimage();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission_Denied", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_LOAD_IMAGE) {
            Uri selectedImage = data.getData();
            Log.e("image uri", "Uri getted ");
            uploaded.setImageURI(selectedImage);
            image_data = selectedImage;
        }

    }
    private void checkpermission(int i) {
        switch (i) {
            case 0:
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED  &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Log.e("permission",ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE )+ ":"+
                            ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE));

                    loadimage();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, IMAGE_PERMISSION_REQUEST);
                    }
                }
                break;


        }
    }
}
