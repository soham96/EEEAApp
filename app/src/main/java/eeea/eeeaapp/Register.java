package eeea.eeeaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;


public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText etname, etemail, etregno, etpassword1, etpassword;
    Button btregister;
    ImageButton profile;

    private Firebase Users, adduser;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private StorageReference mStorageImage;

    private Uri mImageUri=null;

    public static final int GALLERY_REQUEST_CODE=1;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        Firebase.setAndroidContext(this);

        Users = new Firebase("https://eeea-app.firebaseio.com/users");

        mStorageImage= FirebaseStorage.getInstance().getReference().child("Profile_Pics");

        etname = (EditText) findViewById(R.id.name);
        etemail = (EditText) findViewById(R.id.email);
        etregno = (EditText) findViewById(R.id.regno);
        etpassword = (EditText) findViewById(R.id.password);
        etpassword1 = (EditText) findViewById(R.id.password1);
        profile= (ImageButton) findViewById(R.id.profilepic);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
            }
        });




        btregister = (Button) findViewById(R.id.btregister);

        progressDialog = new ProgressDialog(this);

        btregister.setOnClickListener(this);
    }

        public void registerUser()
        {
                final String name= etname.getText().toString().trim();
                final String email=etemail.getText().toString().trim();
                final String regno=etregno.getText().toString().trim();
                final String password=etpassword.getText().toString().trim();
                final String password1=etpassword1.getText().toString().trim();




            if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(Register.this, "Please Enter your name", Toast.LENGTH_LONG).show();
                    return;
                }


                if(TextUtils.isEmpty(email) && !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    Toast.makeText(Register.this, "Please Enter your email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(regno))
                {
                    Toast.makeText(Register.this, "Please Enter your Registration Number", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password) || TextUtils.isEmpty(password1))
                {
                    Toast.makeText(Register.this, "Please Enter your Password Twice", Toast.LENGTH_LONG).show();
                    return;
                }

                if(password.length()<6 && password1.length()<6)
                {
                    Toast.makeText(Register.this, "Password too short", Toast.LENGTH_LONG).show();
                    return;

                }

            if (!password.equals(password1)) {
                Toast.makeText(Register.this, "Passwords dont Match", Toast.LENGTH_LONG).show();
                return;

            }
                progressDialog.setMessage("Registering User");
                progressDialog.show();



                firebaseAuth.createUserWithEmailAndPassword(email, password)

                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    StorageReference filepath= mStorageImage.child(mImageUri.getLastPathSegment());
                                    filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            String downloadUri=taskSnapshot.getDownloadUrl().toString();

                                            String UID=firebaseAuth.getCurrentUser().getUid();
                                            adduser=Users.child(UID);
                                            adduser.child("Name").setValue(name);
                                            adduser.child("Email").setValue(email);
                                            adduser.child("Reg").setValue(regno);
                                            adduser.child("Password").setValue(password);
                                            adduser.child("Image").setValue(downloadUri);
                                            FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(Register.this, "Signup successful. Verification email sent", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(name).build();
                                            firebaseUser.updateProfile(profileUpdates);
                                            progressDialog.dismiss();




                                            Intent intent=new Intent(Register.this, Login.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);

                                        }
                                    });

                                }
                                else{
                                    Toast.makeText(Register.this,"Registration Error",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }

                            }
                        });

                    user=FirebaseAuth.getInstance().getCurrentUser();




            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_REQUEST_CODE && resultCode==RESULT_OK)
        {

            Uri imageUri=data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                profile.setImageURI(mImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void onClick(View v)
    {
        if(v == btregister){
            registerUser();
        }
    }
}
