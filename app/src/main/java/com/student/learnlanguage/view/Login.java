package com.student.learnlanguage.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    EditText et_email,et_password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session=(Session)getApplicationContext();
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sign In");
        setSupportActionBar(toolbar);

    }
    public void forgotpassword(View view) {

            startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            overridePendingTransition(R.anim.enter, R.anim.exit);

    }
    public void login(View view){
        if (!et_email.getText().toString().matches(emailPattern)) {
            et_email.setError("Please enter valid email");
            et_email.requestFocus();

        } else if (et_password.getText().toString().equals("")) {
            et_password.setError("Password Can'y be blank");
            et_password.requestFocus();

        } else {
            final ProgressDialog dialog = new ProgressDialog(Login.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, Const.SITE_URL+"signIn&"+et_email.getText().toString()+"&"+et_password.getText().toString(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            if(response.equals("")){
                                Toast.makeText(getApplicationContext(),"Invlaid username or password",Toast.LENGTH_LONG).show();

                            }else {
                                try {
                                    JSONArray jsonArray;
                                    JSONObject jsonObject=new JSONObject(response);
                                    String status=jsonObject.getString("Status");
                                    if(status.equals("OK")){
                                        jsonArray = jsonObject.getJSONArray("login");
                                        JSONObject jo = jsonArray.getJSONObject(0);
                                        SharedPreferencesHandler sharedPreferencesHandler = new SharedPreferencesHandler(getApplication().getBaseContext());
                                        sharedPreferencesHandler.WritePreference("login","true");
                                        sharedPreferencesHandler.WritePreference("email",et_email.getText().toString());
                                        sharedPreferencesHandler.WritePreference("password",et_password.getText().toString());
                                        sharedPreferencesHandler.WritePreference("user_name",jo.getString("user_name"));
                                        sharedPreferencesHandler.WritePreference("dob",jo.getString("dob"));
                                        sharedPreferencesHandler.WritePreference("user_id",jo.getString("user_id"));
                                        session.setUser_id(jo.getString("user_id"));
                                        session.setEmail(et_email.getText().toString());
                                        session.setUser_name(jo.getString("user_name"));
                                        session.setDob(jo.getString("dob"));
                           // Toast.makeText(getApplicationContext(),session.getUser_id(),Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(), Home.class));
                                        overridePendingTransition(R.anim.enter, R.anim.exit);
                                    }else {
                                        Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
    }
}
