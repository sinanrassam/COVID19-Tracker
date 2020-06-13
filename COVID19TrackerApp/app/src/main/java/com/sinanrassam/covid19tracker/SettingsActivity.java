package com.sinanrassam.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sinanrassam.covid19tracker.Entries.User;
import com.sinanrassam.covid19tracker.Utils.PreferencesUtility;

public class SettingsActivity extends AppCompatActivity {

    TextView mApiUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView mName = findViewById(R.id.nameProfile);
        TextView mUsername = findViewById(R.id.usernameProfile);
        TextView mEmail = findViewById(R.id.emailProfile);

        User user = PreferencesUtility.getUser();
        mName.setText("Hi, " + user.getFirstName() + " "  + user.getLastName() + "!");
        mUsername.setText("Your username is: " + user.getUsername());
        mEmail.setText("Your email address is: " + user.getEmail());

        mApiUrl = findViewById(R.id.apiUrl);
        mApiUrl.setText(PreferencesUtility.getApiUrl());

        findViewById(R.id.btn_save_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreferencesUtility.setApiUrl(mApiUrl.getText().toString());
                Toast.makeText(getApplicationContext(), R.string.action_successful, Toast.LENGTH_SHORT).show();
            }
        });
    }
}