package com.student.learnlanguage.view;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
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
import com.student.learnlanguage.connection.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register extends AppCompatActivity {

    EditText et_fname,et_age,et_email,et_password,et_cpassword;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private DatePickerDialog datePickerDialog;
    private Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_fname=(EditText)findViewById(R.id.et_fname);
        et_age=(EditText)findViewById(R.id.et_age);
        et_email=(EditText)findViewById(R.id.et_email);
        et_password=(EditText)findViewById(R.id.et_password);
        et_cpassword=(EditText)findViewById(R.id.et_cpassword);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Sign Up");
        setSupportActionBar(toolbar);
        et_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDateTimeField();
            }
        });

    }
    // Creating Date Picker Here..
    private void setDateTimeField() {
        datePickerDialog = new DatePickerDialog(Register.this, datePicker, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();

    }

    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            et_age.setText(sdf.format(myCalendar.getTime()));
        }
    };
    public  void openHome(View view)
    {
        if(et_fname.getText().toString().equals("")){
            et_fname.setError("Can't be blank");
            et_fname.requestFocus();

        }else if(et_age.getText().toString().equals("")){
            et_age.setError("Can't be blank");
            et_age.requestFocus();
        }else if(et_email.getText().toString().equals("")){
            et_email.setError("Can't be blank");
            et_email.requestFocus();
        }else  if (!et_email.getText().toString().matches(emailPattern)) {
            et_email.setError("Please enter valid email");
            et_email.requestFocus();

        }else if(et_password.getText().toString().equals("")){
            et_password.setError("Can't be blank");
            et_password.requestFocus();
        }else if(!et_cpassword.getText().toString().equals(et_password.getText().toString())){
            et_cpassword.setError("Password confirm password must be same");
            et_cpassword.requestFocus();
        }else {
            String dob=et_age.getText().toString();
            dob=dob.replace("-","%2f");
            String url=Const.SITE_URL+"signup&"+et_fname.getText().toString()+"&"+et_email.getText().toString()+"&"+et_password.getText().toString()+"&"+dob+"&1";
           // Toast.makeText(getApplicationContext(),url,Toast.LENGTH_LONG).show();
            final ProgressDialog dialog = new ProgressDialog(Register.this);
            dialog.setMessage("Please Wait...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            if(response.equals("")){
                                Toast.makeText(getApplicationContext(),"Invlaid username or password",Toast.LENGTH_LONG).show();

                            }else {
                                try {
                                    JSONObject jsonObject=new JSONObject(response);
                                    String status=jsonObject.getString("Status");
                                    if(status.equals("OK")){
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}
