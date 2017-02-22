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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class add_notification extends AppCompatActivity {

    private EditText newnotification;
    private EditText newdate;
    private Button submitbtn;

    private DatabaseReference databaseref;

    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);

        databaseref = FirebaseDatabase.getInstance().getReference().child("notification");

        newnotification = (EditText) findViewById(R.id.new_notification);
        newdate = (EditText) findViewById(R.id.date);

        submitbtn = (Button) findViewById(R.id.ntsubmitbt);

        progress = new ProgressDialog(this);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startposting();
            }


        });
    }

    private void startposting() {
        progress.setMessage("Uploading");
        progress.show();

        final String notification = newnotification.getText().toString().trim();
        final String date = newdate.getText().toString().trim();

        if (!TextUtils.isEmpty(notification) && !TextUtils.isEmpty(date)) {
            DatabaseReference newPost = databaseref.push();

            newPost.child("ancmnt").setValue(notification);
            newPost.child("date").setValue(date);

            progress.dismiss();

            Intent intent=new Intent(add_notification.this, mainpage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}