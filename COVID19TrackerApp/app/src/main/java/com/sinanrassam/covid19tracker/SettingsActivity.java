package com.sinanrassam.covid19tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sinanrassam.covid19tracker.Entries.User;
import com.sinanrassam.covid19tracker.Utils.PreferencesUtility;

public class SettingsActivity extends AppCompatActivity {

    TextView mApiUrl;
    boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.action_settings);

        TextView mName = findViewById(R.id.nameProfile);
        TextView mUsername = findViewById(R.id.usernameProfile);
        TextView mEmail = findViewById(R.id.emailProfile);

        User user = PreferencesUtility.getUser();
        isLoggedIn = ((user.getUsername() != null) && (user.getUsername() != ""));
        if (isLoggedIn) {
            mName.setText("Hi, " + user.getFirstName() + " " + user.getLastName() + "!");
            mUsername.setText("Your username is: " + user.getUsername());
            mEmail.setText("Your email address is: " + user.getEmail());
        }

            findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isLoggedIn) {
                        PreferencesUtility.setUserInfo(new User());
                        Toast.makeText(getApplicationContext(), R.string.action_logout_successful, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.action_already_logged_out, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        mApiUrl = findViewById(R.id.apiUrl);
        mApiUrl.setText(PreferencesUtility.getApiUrl());

        findViewById(R.id.btn_save_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtility.setApiUrl(mApiUrl.getText().toString());
                Toast.makeText(getApplicationContext(), R.string.action_successful, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}