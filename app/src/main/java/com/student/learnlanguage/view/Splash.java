package com.student.learnlanguage.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.student.learnlanguage.R;
import com.student.learnlanguage.Session;
import com.student.learnlanguage.SharedPreferencesHandler;

public class Splash extends AppCompatActivity {
    private SharedPreferencesHandler sharedPreferencesHandler;
    String login="",email,password;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = (Session) getApplicationContext();
        sharedPreferencesHandler = new SharedPreferencesHandler(this);

        login = login + sharedPreferencesHandler.ReadPreferences("login");
        if (login.equals("true")) {
            session.setEmail(sharedPreferencesHandler.ReadPreferences("email"));
            session.setPassword(sharedPreferencesHandler.ReadPreferences("password"));
            session.setUser_name(sharedPreferencesHandler.ReadPreferences("user_name"));
            session.setDob(sharedPreferencesHandler.ReadPreferences("dob"));
            session.setUser_id(sharedPreferencesHandler.ReadPreferences("user_id"));
            startActivity(new Intent(getApplicationContext(), Home.class));
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();

        } else {
            Thread timer = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        startActivity(new Intent(getApplicationContext(), Entrance.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        finish();
                    }
                }
            };
            timer.start();
        }
    }
}
