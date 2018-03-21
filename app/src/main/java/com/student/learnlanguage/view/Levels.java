package com.student.learnlanguage.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.student.learnlanguage.R;

public class Levels extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Levels");
        setSupportActionBar(toolbar);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    public void openBeginners(View view)
    {
        Intent i=new Intent(getApplicationContext(), Beginner.class);
        startActivity(i);
    }

    public void openIntermediate(View view)
    {
        Intent i=new Intent(getApplicationContext(), Intermediate.class);
        startActivity(i);
    }
}
