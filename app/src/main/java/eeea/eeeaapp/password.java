package eeea.eeeaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class password extends AppCompatActivity {

    private Button reset;
    private EditText email;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        reset= (Button) findViewById(R.id.resetpassword);
        email=(EditText) findViewById(R.id.email_set);

        progressDialog=new ProgressDialog(this);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth auth = FirebaseAuth.getInstance();
                String emailid= email.getText().toString().trim();

                progressDialog.setMessage("Logging In");
                progressDialog.show();

                auth.sendPasswordResetEmail(emailid)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(password.this, "Reset link sent to email address", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(password.this, Login.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();

                                }
                            }
                        });
            }
        });

    }
}
