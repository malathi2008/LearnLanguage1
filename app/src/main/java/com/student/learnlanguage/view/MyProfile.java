package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.student.learnlanguage.R;
import com.student.learnlanguage.Session;
import com.student.learnlanguage.adapter.ContactAdapter;
import com.student.learnlanguage.connection.Const;
import com.student.learnlanguage.model.Contacts;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyProfile extends AppCompatActivity {

    EditText et_name, et_email;
    TextView tx_name, tx_email;
    ImageView im_pen_name, im_pen_email ;
    Button update_profile;
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session=(Session)getApplicationContext();
        setContentView(R.layout.activity_my_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("My Profile");
        setSupportActionBar(toolbar);
        init();
        addresult();
    }
    public void addresult(){
       final ContactAdapter contactAdapter=new ContactAdapter(this,R.layout.imageview);
        final ListView listView=(ListView)findViewById(R.id.plist);
        final ProgressDialog dialog = new ProgressDialog(MyProfile.this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
      //  Toast.makeText(getApplicationContext(),session.getRightans()+"&"+ session.getCourse_id() + "&" + session.getUser_id(),Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL+"resultlist&"+session.getUser_id(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        JSONArray jsonArray;
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("Status");
                            if (status.equals("OK")) {
                                jsonArray = jsonObject.getJSONArray("data");
                                session.setTotalque(jsonArray.length() + "");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jo = jsonArray.getJSONObject(i);
                                    Contacts contacts = new Contacts(jo.getString("quiz_name"),jo.getString("total_score"));
                                    contactAdapter.add(contacts);
                                }
                                listView.setAdapter(contactAdapter);
                            }
                        }catch (Exception e){

                        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(MyProfile.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
    public void init(){
        et_name = (EditText) findViewById(R.id.et_name);
        tx_name = (TextView) findViewById(R.id.tx_name);
        im_pen_name = (ImageView) findViewById(R.id.im_pen_name);
        et_email = (EditText) findViewById(R.id.et_email);
        tx_email = (TextView) findViewById(R.id.tx_email);
        im_pen_email = (ImageView) findViewById(R.id.im_pen_email);
        et_email.setText(session.getEmail());
        tx_email.setText(session.getEmail());
        et_name.setText(session.getUser_name());
        tx_name.setText(session.getUser_name());
        onpenimage();
    }

    public void onpenimage() {
        im_pen_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_name.setVisibility(View.VISIBLE);
                tx_name.setVisibility(View.GONE);
                et_name.setText(tx_name.getText().toString());
                et_name.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_name, InputMethodManager.SHOW_IMPLICIT);
                et_name.setSelection(et_name.getText().length());
            }
        });
        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update_profile.setVisibility(View.VISIBLE);
            }
        });



        im_pen_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_email.setVisibility(View.VISIBLE);
                tx_email.setVisibility(View.GONE);
                et_email.setText(tx_email.getText().toString());
                et_email.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(et_email, InputMethodManager.SHOW_IMPLICIT);
                et_email.setSelection(et_email.getText().length());

            }
        });
        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                update_profile.setVisibility(View.VISIBLE);
            }
        });
    }
}
