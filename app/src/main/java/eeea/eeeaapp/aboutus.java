package eeea.eeeaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class aboutus extends AppCompatActivity {

    private Button button;
    private Button notification;
    private Button mag;
    private Button search;
    private Button userpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        button=(Button) findViewById(R.id.home);
        notification=(Button) findViewById(R.id.notification);
        mag=(Button) findViewById(R.id.mag);
        userpage=(Button) findViewById(R.id.user);

        userpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(aboutus.this, user.class);
                startActivity(intent);
            }
        });

        mag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(aboutus.this, notes.class);
                startActivity(intent);
            }
        });


        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(aboutus.this, notification.class);
                startActivity(intent);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(aboutus.this, mainpage.class);
                startActivity(intent);
            }
        });
    }
}
