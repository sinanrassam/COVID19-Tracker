package com.sinanrassam.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.sinanrassam.covid19tracker.Entries.User;
import com.sinanrassam.covid19tracker.Utils.PreferencesUtility;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        TextView mName = findViewById(R.id.nameProfile);
        TextView mUsername = findViewById(R.id.usernameProfile);
        TextView mEmail = findViewById(R.id.emailProfile);

        User user = PreferencesUtility.getUserInfo().getUser();
        mName.setText("Hi, " + user.getFirstName() + " "  + user.getLastName() + "!");
        mUsername.setText("Your username is: " + user.getUsername());
        mEmail.setText("Your email address is: " + user.getEmail());
    }
}