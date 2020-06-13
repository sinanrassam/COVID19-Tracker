package com.sinanrassam.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

public class NewLocationActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;
    private TextView nfcStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_location);

        nfcStatusView = findViewById(R.id.nfc_status);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            nfcStatusView.setText(R.string.nfc_uavailable);
        }
    }

    private void handleTagConnection(Intent intent) {
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            nfcStatusView.setText("Card supported");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleTagConnection(this.getIntent());
    }
}