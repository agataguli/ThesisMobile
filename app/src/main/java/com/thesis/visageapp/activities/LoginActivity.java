package com.thesis.visageapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.JsonHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.helpers.ValidateHelper;
import com.thesis.visageapp.helpers.VolleySingleton;
import com.thesis.visageapp.objects.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_login_l)
    EditText loginText;
    @Bind(R.id.input_password_l)
    EditText passwordText;
    @Bind(R.id.button_login_l)
    Button loginButton;
    @Bind(R.id.link_signup_l)
    TextView signupLinkedText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLinkedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login processed");

        if (!validateLoginData()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(this.getResources().getString(R.string.authorizing));
        progressDialog.show();
        processLogin(this.loginText.getText().toString(),
                this.passwordText.getText().toString());
    }

    private void processLogin(String login, String password) {
        final String helpReq = UrlHelper.getUserLoginRequest(login, password, UrlHelper.AGATA_URL);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, helpReq, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                User responseUser = JsonHelper.processUserStringJSON(response);
                if (responseUser.getUserId().equals(getResources().getString(R.string.ERROR))) {
                    onLoginFailed();
                } else {
                    onLoginSuccess(responseUser);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getBaseContext(), getResources().getString(R.string.loginRequestError),
                        Toast.LENGTH_SHORT).show();
            }
        });
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // make impossible going back to main activity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(User responseUser) {
        loginButton.setEnabled(true);
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra(getResources().getString(R.string.bundle_fields_user), new Gson().toJson(responseUser));
        startActivityForResult(intent, REQUEST_SIGNUP);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), this.getResources().getString(R.string.loginFailed),
                Toast.LENGTH_SHORT).show();
        loginButton.setEnabled(true);
    }

    public boolean validateLoginData() {
        boolean isValidate = true;
        if (!ValidateHelper.isValidLogin(this.loginText.getText().toString())) {
            this.loginText.setError(this.getResources().getString(R.string.loginError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidPassword(this.passwordText.getText().toString())) {
            this.passwordText.setError(this.getResources().getString(R.string.passwordError));
            isValidate = false;
        }
        return isValidate;
    }
}
