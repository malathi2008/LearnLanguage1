package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.student.learnlanguage.connection.Const;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

public class Lecture extends AppCompatActivity {

    TextView title,tute;
    Button quiz;
    Session session;
    TextToSpeech t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        session=(Session)getApplicationContext();
        session.setCategory_id(session.getCourse_id());
        if(session.getLanguage().equals("FRENCH")) {
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.FRENCH);
                    }
                }
            });
        }

        if(session.getLanguage().equals("SPANISH")){
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        Locale locSpanish = new Locale("spa", "MEX");
                        t1.setLanguage(locSpanish);
                    }
                }
            });
        }

        if(session.getLanguage().equals("ITALIAN")){
            t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status != TextToSpeech.ERROR) {
                        t1.setLanguage(Locale.ITALIAN);
                    }
                }
            });
        }

        quiz=(Button)findViewById(R.id.quiz);
        toolbar.setTitle("Intermediate");
        setSupportActionBar(toolbar);
        tute=(TextView)findViewById(R.id.tute);
        title=(TextView)findViewById(R.id.title);
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t1.stop();
                t1.shutdown();
                startActivity(new Intent(getApplicationContext(),Quiz.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
        final ProgressDialog dialog = new ProgressDialog(Lecture.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL + "lecture&"+session.getCourse_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.i("response", response);
                        JSONArray jsonArray;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("Status");
                            if (status.equals("OK")) {
                                jsonArray = jsonObject.getJSONArray("wordslist");
                                int j = 1;
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    tute.setText(jo.getString("secoundword"));
                                    title.setText(jo.getString("firstword"));
                                }
                                String toSpeak = title.getText().toString();
                                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                                String toSpeak1 = tute.getText().toString();
                                t1.speak(toSpeak1, TextToSpeech.QUEUE_FLUSH, null);
                            }
                        }catch (Exception e){

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
        super.onBackPressed();
        t1.stop();
        t1.shutdown();
    }
}
