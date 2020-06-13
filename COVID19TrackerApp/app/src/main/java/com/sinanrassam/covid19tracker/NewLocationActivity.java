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

import com.sinanrassam.covid19tracker.Tasks.FetchTagTask;

import java.util.concurrent.ExecutionException;

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
            String id = byteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
            nfcStatusView.setText("Card supported - ID: " + id);

            FetchTagTask fetchTagTask = new FetchTagTask();
            try {
                String location = fetchTagTask.execute(id).get().toString();
                Log.d("Location", location);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String byteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
        }
        return out;
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleTagConnection(this.getIntent());
    }
}