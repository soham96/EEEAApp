package eeea.eeeaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.firebase.client.Firebase;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class New_post extends AppCompatActivity {

    private ImageButton New_image;

    private EditText newtitle;
    private EditText newdescription;
    private EditText newlink;
    private Uri imageUri = null;
    private Button submitbtn;

    private StorageReference storageref;
    private DatabaseReference databaseref;
    private Firebase newpost;

    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        storageref= FirebaseStorage.getInstance().getReference();
        databaseref= FirebaseDatabase.getInstance().getReference().child("blog");

        New_image=(ImageButton) findViewById(R.id.btnewpic);

        newtitle= (EditText) findViewById(R.id.Post_title);
        newdescription= (EditText) findViewById(R.id.Post_description);
        newlink=(EditText) findViewById(R.id.postlink) ;

        submitbtn= (Button) findViewById(R.id.bt_newpost);

        progress=new ProgressDialog(this);

        New_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);

            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startposting();
            }


        });


    }

    private void startposting()
    {
        progress.setMessage("Uploading");
        progress.show();

        final String title= newtitle.getText().toString().trim();
        final String description= newdescription.getText().toString().trim();
        final String link=newlink.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && imageUri!=null)
        {

            StorageReference filepath=storageref.child(title).child(imageUri.getLastPathSegment());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    DatabaseReference newPost= databaseref.push();

                    newPost.child("title").setValue(title);
                    newPost.child("desc").setValue(description);
                    newPost.child("image").setValue(downloadUrl.toString());
                    newPost.child("link").setValue(link);


                    progress.dismiss();

                    Intent intent = new Intent(New_post.this, mainpage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==2 && resultCode==RESULT_OK)
        {
            imageUri= data.getData();
            New_image.setImageURI(imageUri);
        }
    }
}
