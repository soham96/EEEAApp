package eeea.eeeaapp;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.R.id.content;

public class notification extends AppCompatActivity {

    private Button button;
    private Button notification;
    private Button mag;
    private Button search;
    private Button userpage;

    private DatabaseReference databaseReference;

    private boolean doubleBackToExitPressedOnce = false;

    private RecyclerView announcementList;
    private LinearLayoutManager linearLayoutManager;

    private Boolean exit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        button=(Button) findViewById(R.id.home);
        notification=(Button) findViewById(R.id.notification);
        mag=(Button) findViewById(R.id.mag);
        userpage=(Button) findViewById(R.id.user);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("notification");

        announcementList=(RecyclerView) findViewById(R.id.announcement);
        announcementList.setHasFixedSize(true);
        linearLayoutManager=new LinearLayoutManager(notification.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        announcementList.setLayoutManager(linearLayoutManager);

        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(notification.this, user.class);
                startActivity(intent);
            }
        });

        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(notification.this, notes.class);
                startActivity(intent);
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(notification.this, notification.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(notification.this, mainpage.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<announcement_row,BlogViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<announcement_row, BlogViewHolder>(
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


    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {

        TextView ntfcn;

        View mView;
        public BlogViewHolder(View itemView) {
            super(itemView);

            mView=itemView;

        }

        public void setancmnt(String ancmnt)
        {
            TextView announcement=(TextView) mView.findViewById(R.id.announcements);
            announcement.setText(ancmnt);
        }

        public void setdate(String dates)
        {
            TextView date=(TextView) mView.findViewById(R.id.date);
            date.setText(dates);
        }
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