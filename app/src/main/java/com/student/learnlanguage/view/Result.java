package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
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
import com.student.learnlanguage.connection.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Result extends AppCompatActivity {

    TextView result;
    Session session;
    RatingBar ratingbar1;
    Button button;
    EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        comment=(EditText)findViewById(R.id.comment);
        toolbar.setTitle("Result");
        setSupportActionBar(toolbar);
        session=(Session)getApplicationContext();
        result=(TextView)findViewById(R.id.resule);
        result.setText("Your Score is "+session.getRightans()+"/"+session.getTotalque());
        addListenerOnButtonClick();
        addresult();
    }
    public void shareresult(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi i got "+session.getRightans()+"/"+session.getTotalque()+" in Learn Language.");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
    public void home(View view){
        startActivity(new Intent(getApplicationContext(),Home.class));
        overridePendingTransition(R.anim.enter,R.anim.exit);
    }
    public void addListenerOnButtonClick(){
        ratingbar1=(RatingBar)findViewById(R.id.ratingBar1);
        button=(Button)findViewById(R.id.button1);
        //Performing action on Button Click
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                //Getting the rating and displaying it on the toast
                String rating=String.valueOf(ratingbar1.getRating());
                rating=rating.split("\\.", 2)[0];
              //  Toast.makeText(getApplicationContext(),rating+"  "+session.getCategory_id(),Toast.LENGTH_LONG).show();
                if(comment.getText().toString().equals("")){
                    comment.setError("Comment can't be blank");
                    comment.requestFocus();
                }else {

                    final ProgressDialog dialog = new ProgressDialog(Result.this);
                    dialog.setMessage("Please Wait...");
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL + "addcomment&"+comment.getText().toString()+"&"+rating+"&"+ session.getCategory_id() + "&" + session.getUser_id(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Thanks to Rate ", Toast.LENGTH_LONG).show();

                                }

                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dialog.dismiss();
                                     Toast.makeText(Result.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            }) {

                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            30000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    RequestQueue requestQueue = Volley.newRequestQueue(Result.this);
                    requestQueue.add(stringRequest);
                }
            }

        });

    }

    public void addresult(){
        final ProgressDialog dialog = new ProgressDialog(Result.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        //Toast.makeText(getApplicationContext(),session.getRightans()+"&"+ session.getCourse_id() + "&" + session.getUser_id(),Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL+"result&"+session.getRightans()+"&"+ session.getCategory_id() + "&" + session.getUser_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                      //  Toast.makeText(getApplicationContext(), "Thanks to Rate ", Toast.LENGTH_LONG).show();

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                       //  Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Result.this);
        requestQueue.add(stringRequest);
    }



}
