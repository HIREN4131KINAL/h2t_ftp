package com.tranetech.pankajshah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText et_name;
    private EditText et_address;
    private EditText et_email;
    private EditText et_mobile;
    private Button bt_signup;
    private TextView tv_link_login;
    private TextInputLayout til_name, til_address, til_email, til_mobile_num;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        LoauiEleMents();
        LoadliSners();


    }

    private void LoauiEleMents() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_address = (EditText) findViewById(R.id.et_address);
        et_email = (EditText) findViewById(R.id.et_email);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        bt_signup = (Button) findViewById(R.id.bt_signup);
        tv_link_login = (TextView) findViewById(R.id.tv_link_login);

        til_name = (TextInputLayout) findViewById(R.id.til_name);
        til_address = (TextInputLayout) findViewById(R.id.til_address);
        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_mobile_num = (TextInputLayout) findViewById(R.id.til_mobile_num);
    }

    private void LoadliSners() {
        et_name.addTextChangedListener(new MyTextWatcher(et_name));
        et_address.addTextChangedListener(new MyTextWatcher(et_address));
        et_email.addTextChangedListener(new MyTextWatcher(et_email));
        et_mobile.addTextChangedListener(new MyTextWatcher(et_mobile));

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        tv_link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        bt_signup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String email = et_email.getText().toString();
        String mobile = et_mobile.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        bt_signup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        // Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        bt_signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = et_name.getText().toString();
        String address = et_address.getText().toString();
        String email = et_email.getText().toString();
        String mobile = et_mobile.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            til_name.setError(Html.fromHtml("<font color='red'>at least 3 characters</font>"));
            valid = false;
        } else {
            til_name.setError(null);
        }

        if (address.isEmpty()) {
            til_address.setError("Enter Valid Address");
            valid = false;
        } else {
            til_address.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            til_email.setError("enter a valid email address");
            valid = false;
        } else {
            til_email.setError(null);
        }

        if (mobile.isEmpty() || mobile.length() != 10) {
            til_mobile_num.setError("Enter 10 digits Valid Mobile Number");
            valid = false;
        } else {
            til_mobile_num.setError(null);
        }


        return valid;
    }

 /*   private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }*/

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.et_name:
                    validate();
                    break;
                case R.id.et_address:
                    validate();
                    break;
                case R.id.et_email:
                    validate();
                    break;
                case R.id.et_mobile:
                    validate();
                    break;


            }
        }
    }
}