package eeea.eeeaapp;

import android.app.Application;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by vaidheeswaran on 13-09-2016.
 */
public class EEEAAPP extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        Firebase.setAndroidContext(this);

        if(!com.google.firebase.FirebaseApp.getApps(this).isEmpty())
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }

    }
}
