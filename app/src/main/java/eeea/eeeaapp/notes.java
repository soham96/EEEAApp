package eeea.eeeaapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class notes extends AppCompatActivity {

    private RecyclerView noteslist;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseLike;

    private Boolean mProcessLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classnotes);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("blog");
        databaseReference.keepSynced(true);
        databaseLike = FirebaseDatabase.getInstance().getReference().child("likes");
        databaseLike.keepSynced(true);



        noteslist=(RecyclerView) findViewById(R.id.classnotes);
        noteslist.setHasFixedSize(true);
        noteslist.setLayoutManager(new LinearLayoutManager(this));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        noteslist.setLayoutManager(llm);

    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<notes_post, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<notes_post, BlogViewHolder>(
                notes_post.class,
                R.layout.post_row,
                BlogViewHolder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, notes_post model, int position) {

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext(), model.getImage());

                viewHolder.likebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mProcessLike=true;
                        if(mProcessLike==true)
                        {
                            databaseLike.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
            }
        };

        noteslist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View view;

        public ImageButton likebtn;

        public BlogViewHolder(View itemView) {
            super(itemView);

            view=itemView;
            likebtn= (ImageButton) view.findViewById(R.id.like_btn);
        }

        public void setTitle(String tile)
        {
            TextView post_title=(TextView) view.findViewById(R.id.posttitle);
            post_title.setText(tile);
        }

        public void setDesc(String desc)
        {
            TextView post_desc=(TextView) view.findViewById(R.id.postdesc);
            post_desc.setText(desc);
        }

        public void setImage(Context ctx, String image)
        {
            ImageView post_image=(ImageView) view.findViewById(R.id.postimg);
            Picasso.with(ctx).load(image).into(post_image);
        }

    }
}

