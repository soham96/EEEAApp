package eeea.eeeaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;




public class mainpage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;

    private RecyclerView announcementList;
    private LinearLayoutManager linearLayoutManager;

    private Button button;
    private Button notification;
    private Button mag;
    private Button search;
    private Button userpage;
    private TextView ntfctn;

    private boolean doubleBackToExitPressedOnce = false;

    private ViewPager viewPager;
    image_swipe image_swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("notification");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                    String UID = firebaseAuth.getCurrentUser().getUid();
                } else {
                    finish();
                    startActivity(new Intent(mainpage.this, Login.class));
                }
            }
        };

        button = (Button) findViewById(R.id.home);
        notification = (Button) findViewById(R.id.notification);
        mag = (Button) findViewById(R.id.mag);
        userpage = (Button) findViewById(R.id.user);
        ntfctn = (TextView) findViewById(R.id.knowmore);

        announcementList = (RecyclerView) findViewById(R.id.announcement);
        announcementList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(mainpage.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        announcementList.setLayoutManager(linearLayoutManager);


        ntfctn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpage.this, notification.class);
                startActivity(intent);
            }
        });

        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainpage.this, user.class);
                startActivity(intent);
            }
        });

        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainpage.this, notes.class);
                startActivity(intent);
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainpage.this, notification.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mainpage.this, mainpage.class);
                startActivity(intent);
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        image_swipe = new image_swipe(this);
        viewPager.setAdapter(image_swipe);

        announcementList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<announcement_row, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<announcement_row, BlogViewHolder>(
                announcement_row.class,
                R.layout.announcement_row,
                BlogViewHolder.class,
                databaseReference

        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, announcement_row model, int position) {
                viewHolder.setancmnt(model.getAncmnt());
                viewHolder.setdate(model.getDate());


            }
        };

        announcementList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        TextView ntfcn;

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setancmnt(String ancmnt) {
            TextView announcement = (TextView) mView.findViewById(R.id.announcements);
            announcement.setText(ancmnt);
        }

        public void setdate(String dates) {
            TextView date = (TextView) mView.findViewById(R.id.date);
            date.setText(dates);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);

            startActivity(intent);
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

}