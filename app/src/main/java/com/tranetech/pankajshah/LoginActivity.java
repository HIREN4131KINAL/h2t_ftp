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
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private TextInputLayout til_mobile;
    private EditText et_mobile_num;
    private Button bt_login;
    private TextView tv_signupLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LadUielements();
        LoadLisners();

    }

    private void LadUielements() {

        et_mobile_num = (EditText) findViewById(R.id.et_mobile_num);
        bt_login = (Button) findViewById(R.id.bt_login);
        tv_signupLink = (TextView) findViewById(R.id.tv_signupLink);
        til_mobile = (TextInputLayout) findViewById(R.id.til_mobile);

    }

    private void LoadLisners() {

        bt_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        tv_signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        bt_login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String mobile_no = et_mobile_num.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        bt_login.setEnabled(true);
        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        bt_login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String mobile_no = et_mobile_num.getText().toString();


        if (mobile_no.isEmpty() || (mobile_no.length() != 10)) {
            til_mobile.setError(Html.fromHtml("<font color='red'>enter a 10 digits valid mobile number</font>"));
            valid = false;
        } else {
            til_mobile.setError(null);
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
                case R.id.et_mobile_num:
                    validate();
                    break;

            }
        }
    }

}
