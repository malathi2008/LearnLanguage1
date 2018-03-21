package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.student.learnlanguage.R;
import com.student.learnlanguage.Session;
import com.student.learnlanguage.SharedPreferencesHandler;
import com.student.learnlanguage.adapter.ContactAdapter1;
import com.student.learnlanguage.connection.Const;
import com.student.learnlanguage.model.Contacts1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> arrayList;
    Session session;
    ArrayList<String> languagename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session=(Session)getApplicationContext();
        languagename=new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Select Language");
        setSupportActionBar(toolbar);
        arrayList=new ArrayList<>();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        TextView text = (TextView) header.findViewById(R.id.name);
        text.setText(session.getUser_name());
        TextView text1 = (TextView) header.findViewById(R.id.email);
        text1.setText(session.getEmail());
        final ProgressDialog dialog = new ProgressDialog(Home.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL+"languagelist",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        if(response.equals("")){
                            Toast.makeText(getApplicationContext(),"Invlaid username or password",Toast.LENGTH_LONG).show();

                        }else {
                            loadgridview(response);
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       if(id==R.id.my_profile)
        {
            Intent i=new Intent(getApplicationContext(), MyProfile.class);
            startActivity(i);
        }
        else if(id==R.id.contact_us)
        {
            Intent i=new Intent(getApplicationContext(), ContactUs.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        }
        else if(id==R.id.about_us)
        {
            Intent i=new Intent(getApplicationContext(), Aboutus.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        }
        else if(id==R.id.term)
        {
            Intent i=new Intent(getApplicationContext(), Term.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        } else if(id==R.id.logout)
        {
            SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(getApplicationContext());
            sharedPreferencesHandler.ClearPreferences();
            finishAffinity();
            Intent i=new Intent(getApplicationContext(), Entrance.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadgridview(String responce){
        ContactAdapter1 contactAdapter=new ContactAdapter1(this,R.layout.imageview);
        final GridView gridView=(GridView)findViewById(R.id.languages);

        JSONArray jsonArray;
        try {
            JSONObject jsonObject=new JSONObject(responce);
            String status=jsonObject.getString("Status");
            if(status.equals("OK")){
                jsonArray = jsonObject.getJSONArray("languagelist");
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    arrayList.add(jo.getString("language_id"));
                    languagename.add(jo.getString("language_name"));
                    Contacts1 contacts = new Contacts1(Const.image_URL+jo.getString("image_url"),jo.getString("language_name"));
                    contactAdapter.add(contacts);
                }
            }else {
                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }



        gridView.setAdapter(contactAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                session.setLanguage(languagename.get(i));
                session.setLanguage_id(arrayList.get(i));
                startActivity(new Intent(getApplicationContext(),Levels.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
                //  Toast.makeText(getApplicationContext(),i+"",Toast.LENGTH_LONG).show();
            }
        });
    }
}
