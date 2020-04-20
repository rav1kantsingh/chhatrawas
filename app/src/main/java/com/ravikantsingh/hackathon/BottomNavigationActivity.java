package com.ravikantsingh.hackathon;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ravikantsingh.Noticefragment;
import com.ravikantsingh.hackathon.Mess.MessFragment;

public class BottomNavigationActivity extends AppCompatActivity {
    private Dialog updatenow;
    private android.support.v7.widget.Toolbar toolbar;
    private ActionBar action;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DrawerLayout mDrawerLayout;
    private DatabaseReference dbref2, dbProfile;
    FragmentManager fm;
    HostelEntry testFragment;
    MessFragment messFragment;
    MyDashboard myDashboardFragment;
    Noticefragment noticefragment;
    ComplainActivity complaintsFragment;

    Fragment currentFragment;
    TextView titletext;
    TextView subtitle;
    ImageView toolbar_rightIcon, blueIcon;
    Typeface type2;
    BottomNavigationView navigation;
    Double actualVersion;
    DatabaseReference dbref, dbref1;
    String updateTitleString, updateDescriptionString;
    String profileComplete, currentUser;


    ColorStateList ColorStateList1, ColorStateList2, ColorStateList3, ColorStateList4, ColorStateList5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bottom_navigation);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        currentUser = "";
        try {


            currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        } catch (Exception e) {
        }


        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}

        };
        int[] colors2 = new int[]{
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#ff9315")

        };
        int[] colors4 = new int[]{
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#008ca5")

        };
        int[] colors3 = new int[]{
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#d60000")
        };
        int[] colors1 = new int[]{
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#008338")
        };
        int[] colors5 = new int[]{
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#b70d9d")
        };
        ColorStateList1 = new ColorStateList(states, colors1);
        ColorStateList2 = new ColorStateList(states, colors2);
        ColorStateList3 = new ColorStateList(states, colors3);
        ColorStateList4 = new ColorStateList(states, colors4);
        ColorStateList5 = new ColorStateList(states, colors5);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titletext = findViewById(R.id.titletext);
        subtitle = findViewById(R.id.subtitle);
        toolbar_rightIcon = findViewById(R.id.toolbar_right_image);
        blueIcon = findViewById(R.id.blue_dot);
        blueIcon.setVisibility(View.GONE);

        toolbar_rightIcon.setVisibility(View.VISIBLE);




        action = getSupportActionBar();
        titletext.setText("Hostel Management");
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeAsUpIndicator(R.drawable.ic_menu);


        fm = getSupportFragmentManager();

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //loadFragment(new SacFeedsFragment());
         testFragment = new HostelEntry();
        messFragment = new MessFragment();
        noticefragment = new Noticefragment();
        complaintsFragment = new ComplainActivity();
        myDashboardFragment = new MyDashboard();

        fm.beginTransaction().add(R.id.frame_container, noticefragment, "2").commit();
        // currentFragment = noticefragment;
        currentFragment = noticefragment;

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(false);
                        mDrawerLayout.closeDrawers();
                        int id = menuItem.getItemId();
                        if (id == R.id.id_blood_request) {
                            startActivity(new Intent(BottomNavigationActivity.this, BloodSearchActivity.class));
                        }
                        if (id == R.id.id_first_aid) {
                            startActivity(new Intent(BottomNavigationActivity.this, GeneralMedicals.class));
                        } else if (id == R.id.id_call_ambulance) {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel:" + "100"));
                            BottomNavigationActivity.this.startActivity(callIntent);

                        } else if (id == R.id.id_hospitals) {
                            Uri gmmIntentUri = Uri.parse("geo:25.6175743,85.1763928,14z?q=hospitals");
                            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                            mapIntent.setPackage("com.google.android.apps.maps");
                            startActivity(mapIntent);
                        } else if (id == R.id.id_contacts) {
                            startActivity(new Intent(BottomNavigationActivity.this, MainActivity.class));
                        } else if (id == R.id.logout) {
                            FirebaseUser currentuser = auth.getCurrentUser();
                            if (currentuser != null) {
                                auth.signOut();

                            }
                        }

                        return true;
                    }
                });

    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_notice:
                    navigation.setItemIconTintList(ColorStateList1);
                    navigation.setItemTextColor(ColorStateList1);

                    titletext.setText("Notice");

                    toolbar_rightIcon.setVisibility(View.VISIBLE);
                    blueIcon.setVisibility(View.GONE);


                    subtitle.setVisibility(View.INVISIBLE);
                    if (noticefragment == null) {
                        Log.e("check", "1");
                       /* complaintsFragment = new ComplainActivity();
                        fm.beginTransaction().replace(R.id.frame_container, complaintsFragment, "3").hide(currentFragment).show(complaintsFragment).commit();*/
                    } else {
                        Log.e("check", "2");
                        // fm.beginTransaction().hide(currentFragment).show(complaintsFragment).commit();
                        noticefragment = new Noticefragment();
                        fm.beginTransaction().replace(R.id.frame_container, noticefragment, "3").hide(currentFragment).show(noticefragment).commit();
                    }
                    currentFragment = noticefragment;
                    return true;
                case R.id.navigation_mess:


                    navigation.setItemIconTintList(ColorStateList1);
                    navigation.setItemTextColor(ColorStateList1);

                    titletext.setText("Mess");

                    toolbar_rightIcon.setVisibility(View.VISIBLE);
                    blueIcon.setVisibility(View.GONE);


                    subtitle.setVisibility(View.INVISIBLE);
                    if (messFragment == null) {
                        Log.e("check", "1");

                 } else {
                        Log.e("check", "2");
                        //fm.beginTransaction().hide(currentFragment).show(messFragment).commit();
                        messFragment = new MessFragment();
                        fm.beginTransaction().add(R.id.frame_container, messFragment, "4").hide(currentFragment).show(messFragment).commit();
                    }
                    currentFragment = messFragment;
                    return true;
                case R.id.navigation_forms:
                    navigation.setItemIconTintList(ColorStateList1);
                    navigation.setItemTextColor(ColorStateList1);

                    titletext.setText("Forms");

                    toolbar_rightIcon.setVisibility(View.VISIBLE);
                    blueIcon.setVisibility(View.GONE);


                    subtitle.setVisibility(View.INVISIBLE);
                    if (complaintsFragment == null) {
                        Log.e("check", "1");
                    } else {
                        Log.e("check", "2");
                        complaintsFragment = new ComplainActivity();
                        fm.beginTransaction().replace(R.id.frame_container, complaintsFragment, "3").hide(currentFragment).show(complaintsFragment).commit();
                    }
                    currentFragment = complaintsFragment;
                    return true;
                case R.id.navigation_payments:

                    navigation.setItemIconTintList(ColorStateList1);
                    navigation.setItemTextColor(ColorStateList1);

                    titletext.setText("In & Out");

                    toolbar_rightIcon.setVisibility(View.VISIBLE);
                    blueIcon.setVisibility(View.GONE);


                    subtitle.setVisibility(View.INVISIBLE);
                    if (testFragment == null) {
                        Log.e("check", "1");

                    } else {
                        Log.e("check", "2");
                        testFragment = new HostelEntry();
                        fm.beginTransaction().add(R.id.frame_container, testFragment, "1").hide(currentFragment).show(testFragment).commit();
                    }
                    currentFragment = testFragment;
                    return true;
                case R.id.navigation_profile:
                    navigation.setItemIconTintList(ColorStateList1);
                    navigation.setItemTextColor(ColorStateList1);

                    titletext.setText("Profile");

                    toolbar_rightIcon.setVisibility(View.VISIBLE);
                    blueIcon.setVisibility(View.GONE);


                    subtitle.setVisibility(View.INVISIBLE);
                    if (myDashboardFragment == null) {
                        Log.e("check", "1");
                    } else {
                        Log.e("check", "2");
                        myDashboardFragment = new MyDashboard();
                        fm.beginTransaction().replace(R.id.frame_container, myDashboardFragment, "3").hide(currentFragment).show(myDashboardFragment).commit();
                    }
                    currentFragment = myDashboardFragment;
                    return true;

            }

            return false;
        }
    };


    private void backActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
    @Override
    public void onStop() {

        Log.e("BottomNavigationActivit", "stoped");

//        }
        super.onStop();
    }
}






