package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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
import com.student.learnlanguage.adapter.ContactAdapter1;
import com.student.learnlanguage.connection.Const;
import com.student.learnlanguage.model.Contacts1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Intermediate extends AppCompatActivity {

    Session session;
    ArrayList<String> intermediate_id;
    EditText inputSearch;
    ContactAdapter1 contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate);
        session=(Session)getApplicationContext();
        intermediate_id=new ArrayList<>();
        inputSearch=(EditText)findViewById(R.id.et_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Intermediate");
        setSupportActionBar(toolbar);
        final ProgressDialog dialog = new ProgressDialog(Intermediate.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL+"courselist&"+session.getLanguage_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.i("response",response);
                        loadgridview(response);
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
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //   Beginner.this.adapter.getFilter().filter(cs);
                String text = inputSearch.getText().toString().toLowerCase();
                contactAdapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    public void loadgridview(String responce){
        contactAdapter=new ContactAdapter1(this,R.layout.imageview);
        final GridView gridView=(GridView)findViewById(R.id.languages);
        JSONArray jsonArray;
        try {
            JSONObject jsonObject=new JSONObject(responce);
            String status=jsonObject.getString("Status");
            if(status.equals("OK")){
                jsonArray = jsonObject.getJSONArray("courselist");
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    //  Toast.makeText(getApplicationContext(),jo.getString("language_id"),Toast.LENGTH_LONG).show();
                    intermediate_id.add(jo.getString("course_id"));
                    Contacts1 contacts = new Contacts1(Const.image_URL+jo.getString("image"), jo.getString("course_name"));
                    contactAdapter.add(contacts);
                }
                gridView.setAdapter(contactAdapter);
            }else {
                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                session.setCourse_id(intermediate_id.get(i)+"");
                startActivity(new Intent(getApplicationContext(),Lecture.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
                 Toast.makeText(getApplicationContext(),intermediate_id.get(i)+"",Toast.LENGTH_LONG).show();
            }
        });
    }
}
