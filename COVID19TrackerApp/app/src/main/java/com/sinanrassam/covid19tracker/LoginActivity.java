package com.sinanrassam.covid19tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sinanrassam.covid19tracker.Tasks.LoginTask;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsernameView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.action_sign_in);

        mUsernameView = findViewById(R.id.username);
        mPasswordView = findViewById(R.id.password);

        findViewById(R.id.btn_sign_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignin();
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void attemptSignin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString().toLowerCase();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // validate info
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            new LoginTask(this).execute(username, password);
        }

    }
}