package com.student.learnlanguage.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.student.learnlanguage.R;

public class Entrance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
    }
    public void getstart(View view){
        startActivity(new Intent(getApplicationContext(),Register.class));
        overridePendingTransition(R.anim.enter,R.anim.exit);
    }
    public void login(View view){
        startActivity(new Intent(getApplicationContext(),Login.class));
        overridePendingTransition(R.anim.enter,R.anim.exit);
    }

}
