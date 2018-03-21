package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.student.learnlanguage.R;
import com.student.learnlanguage.Session;
import com.student.learnlanguage.adapter.ContactAdapter1;
import com.student.learnlanguage.adapter.WordsListAdapter;
import com.student.learnlanguage.connection.Const;
import com.student.learnlanguage.model.Contacts1;
import com.student.learnlanguage.model.WordsModel;
import com.student.learnlanguage.model.Wordsdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Words extends AppCompatActivity {

    Session session;
    ListView wordslist;
    ArrayList<String> al;
    Button quiz;
    TextToSpeech t1;
    EditText inputSearch;
    WordsListAdapter contactAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words);
        session = (Session) getApplicationContext();
        inputSearch=(EditText)findViewById(R.id.et_search);
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
        al=new ArrayList<>();
        quiz=(Button)findViewById(R.id.quiz);
        wordslist = (ListView) findViewById(R.id.wordslist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Words");
        setSupportActionBar(toolbar);
        final ProgressDialog dialog = new ProgressDialog(Words.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL + "wordslist&"+session.getCategory_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        Log.i("response", response);
                        loadlist(response);
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
        wordslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String toSpeak = al.get(i);
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Quiz.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
    }

    public void loadlist(String response){
        contactAdapter=new WordsListAdapter(this,R.layout.wordslistitems);
        JSONArray jsonArray;
        try {
            JSONObject jsonObject = new JSONObject(response);
            String status = jsonObject.getString("Status");
            if (status.equals("OK")) {
                jsonArray = jsonObject.getJSONArray("wordslist");
                session.setTotalque(jsonArray.length() + "");
                int j=1;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    al.add(jo.getString("secoundword"));
                    WordsModel contacts = new WordsModel(j+". "+jo.getString("firstword"), jo.getString("secoundword"));
                    contactAdapter.add(contacts);
                    j++;
                }
            }
        }catch (Exception e){
        }
        wordslist.setAdapter(contactAdapter);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
