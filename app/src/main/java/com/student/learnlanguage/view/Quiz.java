package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.student.learnlanguage.connection.Const;
import com.student.learnlanguage.model.Wordsdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Quiz extends AppCompatActivity {
    TextView que,answerone,answertwo,answerthree,answerfour;
    ImageView imageone,imagetwo,imagethree,imagefour;
    List<Wordsdata> wordsdatalist;
    Session session;
    Button movenext;
    int row=0;
    boolean answer=true;
    int rightanswer=0;
    LinearLayout text,multipal,image;
    TextView text_que,multipal_que;
    EditText text_answer;
    RadioButton multipal_ans_a,multipal_ans_b,multipal_ans_c,multipal_ans_d;
    String answertext="";
    Button multipal_submit,text_submit;
    RadioGroup group;
    Button ans_one,ans_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Quiz");
        setSupportActionBar(toolbar);

        session=(Session)getApplicationContext();
        movenext=(Button)findViewById(R.id.movenext);
        wordsdatalist=new ArrayList();
        que=(TextView)findViewById(R.id.que);
        ans_two=(Button)findViewById(R.id.ans_two);
        ans_one=(Button)findViewById(R.id.ans_one);
        answerone=(TextView)findViewById(R.id.answerone);
        answertwo=(TextView)findViewById(R.id.answertwo);
        answerthree=(TextView)findViewById(R.id.answerthree);
        answerfour=(TextView)findViewById(R.id.answerfour);
        imagefour=(ImageView)findViewById(R.id.imagefour);
        imagethree=(ImageView)findViewById(R.id.imagethree);
        imagetwo=(ImageView)findViewById(R.id.imagetwo);
        imageone=(ImageView)findViewById(R.id.imageone);
        text=(LinearLayout)findViewById(R.id.text);
        multipal=(LinearLayout)findViewById(R.id.multipal);
        image=(LinearLayout)findViewById(R.id.image);
        text_que=(TextView)findViewById(R.id.text_que);
        multipal_que=(TextView)findViewById(R.id.multipal_que);
        text_answer=(EditText)findViewById(R.id.text_answer);
        multipal_ans_a=(RadioButton)findViewById(R.id.multipal_ans_a);
        multipal_ans_b=(RadioButton)findViewById(R.id.multipal_ans_b);
        multipal_ans_c=(RadioButton)findViewById(R.id.multipal_ans_c);
        multipal_ans_d=(RadioButton)findViewById(R.id.multipal_ans_d);
        multipal_submit=(Button)findViewById(R.id.multipal_submit);
        text_submit=(Button)findViewById(R.id.text_submit);
        group=(RadioGroup)findViewById(R.id.group);
        ans_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_answer.setText(ans_one.getText().toString());
            }
        });

        ans_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_answer.setText(ans_two.getText().toString());
            }
        });


        text_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_submit.setVisibility(View.GONE);
                movenext.setVisibility(View.VISIBLE);
                if(answertext.toLowerCase().equals(text_answer.getText().toString().toLowerCase())){
                    movenext.setText("Right Answer");
                    movenext.setBackgroundColor(Color.GREEN);
                    rightanswer=rightanswer+1;
                    session.setRightans(rightanswer+"");
                    text_answer.setText("");
                }else {
                    movenext.setBackgroundColor(Color.RED);
                    movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
                    text_answer.setText("");
                }
            }
        });
        multipal_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movenext.setVisibility(View.VISIBLE);
                multipal_submit.setVisibility(View.GONE);
                if(multipal_ans_a.isChecked()){
                    if(answertext.toLowerCase().equals(multipal_ans_a.getText().toString().toLowerCase())){
                        movenext.setText("Right Answer");
                        movenext.setBackgroundColor(Color.GREEN);
                        rightanswer=rightanswer+1;
                        session.setRightans(rightanswer+"");
                    }else {
                        movenext.setBackgroundColor(Color.RED);
                        movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
                    }

                }
                if(multipal_ans_b.isChecked()){
                    if(answertext.toLowerCase().equals(multipal_ans_b.getText().toString().toLowerCase())){
                        movenext.setText("Right Answer");
                        movenext.setBackgroundColor(Color.GREEN);
                        rightanswer=rightanswer+1;
                        session.setRightans(rightanswer+"");
                    }else {
                        movenext.setBackgroundColor(Color.RED);
                        movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
                    }
                }
                if(multipal_ans_c.isChecked()){
                    if(answertext.toLowerCase().equals(multipal_ans_c.getText().toString().toLowerCase())){
                        movenext.setText("Right Answer");
                        movenext.setBackgroundColor(Color.GREEN);
                        rightanswer=rightanswer+1;
                        session.setRightans(rightanswer+"");
                    }else {
                        movenext.setBackgroundColor(Color.RED);
                        movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
                    }
                }
                if(multipal_ans_d.isChecked()){
                    if(answertext.toLowerCase().equals(multipal_ans_d.getText().toString().toLowerCase())){
                        movenext.setText("Right Answer");
                        movenext.setBackgroundColor(Color.GREEN);
                        rightanswer=rightanswer+1;
                        session.setRightans(rightanswer+"");
                    }else {
                        movenext.setBackgroundColor(Color.RED);
                        movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
                    }
                }
            }
        });

        final ProgressDialog dialog = new ProgressDialog(Quiz.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL+"quelist&"+session.getCategory_id(),
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
        movenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(row<wordsdatalist.size()-1) {
                    row = row + 1;
                    movenext.setVisibility(View.GONE);
                    setdata(row);
                }else {
                    startActivity(new Intent(getApplicationContext(),Result.class));
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();
                }
            }
        });
    }

    public void loadgridview(String responce){

        JSONArray jsonArray;
        try {
            JSONObject jsonObject=new JSONObject(responce);
            String status=jsonObject.getString("Status");
            if(status.equals("OK")){
                jsonArray = jsonObject.getJSONArray("quelist");
                session.setTotalque(jsonArray.length()+"");
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    text.setVisibility(View.GONE);
                    multipal.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    Wordsdata wordsdata = new Wordsdata();
                    wordsdata.setQuestion_type(jo.getString("question_type"));
                    wordsdata.setQue(jo.getString("question"));
                    wordsdata.setAnswareone(jo.getString("answer_one"));
                    wordsdata.setAnswertwo(jo.getString("answer_two"));
                    wordsdata.setAnswerthree(jo.getString("answer_three"));
                    wordsdata.setAnswerfour(jo.getString("answer_four"));
                    wordsdata.setImageone(jo.getString("image_one"));
                    wordsdata.setImagetwo(jo.getString("image_two"));
                    wordsdata.setImagethree(jo.getString("image_three"));
                    wordsdata.setImagefour(jo.getString("image_four"));
                    wordsdata.setAnswer(jo.getString("answer"));
                    wordsdatalist.add(wordsdata);
                }
                setdata(row);

            }else {
                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void ansone(View view){
        if(answer) {
            answer = false;
            String text = answerone.getText().toString();
            movenext.setVisibility(View.VISIBLE);
            if (text.equals(wordsdatalist.get(row).getAnswer())) {
                movenext.setText("Right Answer");
                movenext.setBackgroundColor(Color.GREEN);
                rightanswer=rightanswer+1;
                session.setRightans(rightanswer+"");
            } else {
                movenext.setBackgroundColor(Color.RED);
                movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
            }
        }
    }

    public void anstwo(View view){
        if(answer) {
            answer = false;
            String text = answertwo.getText().toString();
            movenext.setVisibility(View.VISIBLE);
            if (text.equals(wordsdatalist.get(row).getAnswer())) {
                movenext.setText("Right Answer");
                movenext.setBackgroundColor(Color.GREEN);
                rightanswer=rightanswer+1;
                session.setRightans(rightanswer+"");
            } else {
                movenext.setBackgroundColor(Color.RED);
                movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
            }
        }
    }
    public void ansthree(View view){
        if(answer) {
            answer = false;
            String text = answerthree.getText().toString();
            movenext.setVisibility(View.VISIBLE);
            if (text.equals(wordsdatalist.get(row).getAnswer())) {
                movenext.setText("Right Answer");
                movenext.setBackgroundColor(Color.GREEN);
                rightanswer=rightanswer+1;
                session.setRightans(rightanswer+"");
            } else {
                movenext.setBackgroundColor(Color.RED);
                movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
            }
        }
    }
    public void ansfour(View view){
        if(answer) {
            answer = false;
            String text = answerfour.getText().toString();
            movenext.setVisibility(View.VISIBLE);
            if (text.equals(wordsdatalist.get(row).getAnswer())) {
                movenext.setText("Right Answer");
                movenext.setBackgroundColor(Color.GREEN);
                rightanswer=rightanswer+1;
                session.setRightans(rightanswer+"");
            } else {
                movenext.setBackgroundColor(Color.RED);
                movenext.setText("Wrong Answer," + wordsdatalist.get(row).getAnswer() + " is Right");
            }
        }
    }

    public void setdata(int i){
        if(wordsdatalist.get(i).getQuestion_type().equals("text")){
            text_submit.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            multipal.setVisibility(View.GONE);
            image.setVisibility(View.GONE);
            text_que.setText(wordsdatalist.get(i).getQue());
            ans_one.setText(wordsdatalist.get(i).getAnswareone());
            ans_two.setText(wordsdatalist.get(i).getAnswertwo());
            answertext=wordsdatalist.get(i).getAnswer();
        }else   if(wordsdatalist.get(i).getQuestion_type().equals("multiple")){
            multipal_submit.setVisibility(View.VISIBLE);
            text.setVisibility(View.GONE);
            multipal.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            multipal_que.setText(wordsdatalist.get(i).getQue());
            answertext=wordsdatalist.get(i).getAnswer();
            multipal_ans_a.setText(wordsdatalist.get(i).getAnswareone());
            multipal_ans_b.setText(wordsdatalist.get(i).getAnswertwo());
            multipal_ans_c.setText(wordsdatalist.get(i).getAnswerthree());
            multipal_ans_d.setText(wordsdatalist.get(i).getAnswerfour());

        }else  if(wordsdatalist.get(i).getQuestion_type().equals("image")) {
            answer = true;
            que.setText(wordsdatalist.get(i).getQue());
            answerone.setText(wordsdatalist.get(i).getAnswareone());
            answertwo.setText(wordsdatalist.get(i).getAnswertwo());
            answerthree.setText(wordsdatalist.get(i).getAnswerthree());
            answerfour.setText(wordsdatalist.get(i).getAnswerfour());
            Picasso.with(this)
                    .load(Const.image_URL + wordsdatalist.get(i).getImageone())
                    .resize(400, 400) // here you resize your image to whatever width and height you like
                    .into(imageone);
            Picasso.with(this)
                    .load(Const.image_URL + wordsdatalist.get(i).getImagetwo())
                    .resize(400, 400) // here you resize your image to whatever width and height you like
                    .into(imagetwo);

            Picasso.with(this)
                    .load(Const.image_URL + wordsdatalist.get(i).getImagethree())
                    .resize(400, 400) // here you resize your image to whatever width and height you like
                    .into(imagethree);

            Picasso.with(this)
                    .load(Const.image_URL + wordsdatalist.get(i).getImagefour())
                    .resize(400, 400) // here you resize your image to whatever width and height you like
                    .into(imagefour);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}

