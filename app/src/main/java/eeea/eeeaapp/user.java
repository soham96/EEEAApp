package eeea.eeeaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;

public class user extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    private RecyclerView userlist;
    private DatabaseReference databaseReference;

    private LinearLayoutManager linearLayoutManager;

    private Button button;
    private Button notification;
    private Button mag;
    private Button userpage;

    private boolean doubleBackToExitPressedOnce = false;

    String UID;

    user_page userPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference.keepSynced(true);

        UID = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userPage = dataSnapshot.getValue(user_page.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        userlist = (RecyclerView) findViewById(R.id.profilepage);
        userlist.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(user.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        userlist.setLayoutManager(linearLayoutManager);


        button = (Button) findViewById(R.id.home);
        notification = (Button) findViewById(R.id.notification);
        mag = (Button) findViewById(R.id.mag);
        userpage = (Button) findViewById(R.id.user);


        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(user.this, user.class);
                startActivity(intent);
            }
        });

        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(user.this, notes.class);
                startActivity(intent);
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(user.this, notification.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(user.this, mainpage.class);
                startActivity(intent);
            }
        });

        userlist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_aboutus) {
            Intent intent = new Intent(user.this, aboutus.class);
            startActivity(intent);

        }

        if (id == R.id.action_addannouncement) {
            Intent intent = new Intent(user.this, add_notification.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.action_logout) {
            firebaseAuth.getInstance().signOut();
            Intent intent = new Intent(user.this, Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(user.this, New_post.class));
        }

        return super.onOptionsItemSelected(item);


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<user_page, user.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<user_page, BlogViewHolder>(

                user_page.class,
                R.layout.profile_layout,
                user.BlogViewHolder.class,
                databaseReference
        ) {

            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, user_page model, int po) {

                po = 1;

                viewHolder.setEmail(userPage.getEmail());
                viewHolder.setName(userPage.getName());
                viewHolder.setReg(userPage.getReg());
                viewHolder.setImage(getApplicationContext(), userPage.getImage());

            }


        };

        userlist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View view;

        public BlogViewHolder(View itemView) {
            super(itemView);

            view = itemView;
        }

        public void setEmail(String Email) {
            TextView email = (TextView) view.findViewById(R.id.email);
            email.setText(Email);
        }

        public void setReg(String Reg) {
            TextView reg = (TextView) view.findViewById(R.id.reg);
            reg.setText(Reg);
        }

        public void setName(String Name) {
            TextView name = (TextView) view.findViewById(R.id.name);
            name.setText(Name);
        }

        public void setImage(Context ctx, String Image) {
            ImageView post_image = (ImageView) view.findViewById(R.id.picprofile);
            Picasso.with(ctx).load(Image).into(post_image);
        }


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
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
