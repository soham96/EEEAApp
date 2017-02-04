package eeea.eeeaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity implements View.OnClickListener{

    EditText etname, etemail, etregno, etpassword1, etpassword;
    Button btregister;

    private Firebase Users, adduser;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        Firebase.setAndroidContext(this);

        Users = new Firebase("https://eeea-app.firebaseio.com/users");

        etname = (EditText) findViewById(R.id.name);
        etemail = (EditText) findViewById(R.id.email);
        etregno = (EditText) findViewById(R.id.regno);
        etpassword = (EditText) findViewById(R.id.password);
        etpassword1 = (EditText) findViewById(R.id.password1);

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
                                    String UID=firebaseAuth.getCurrentUser().getUid();
                                    adduser=Users.child(UID);
                                    adduser.child("Name").setValue(name);
                                    adduser.child("Email").setValue(email);
                                    adduser.child("Reg No").setValue(regno);
                                    adduser.child("Password").setValue(password);
                                    progressDialog.dismiss();
                                    Intent intent=new Intent(Register.this, Login.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(Register.this,"Registration Error",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }

                            }
                        });





            }




    public void onClick(View v)
    {
        if(v == btregister){
            registerUser();
        }
    }
}
