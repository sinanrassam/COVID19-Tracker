package com.sinanrassam.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sinanrassam.covid19tracker.Tasks.FetchTagTask;
import com.sinanrassam.covid19tracker.Tasks.UpdateLocationTask;
import com.sinanrassam.covid19tracker.Utils.PreferencesUtility;

import java.util.concurrent.ExecutionException;

public class LocationsActivity extends AppCompatActivity {
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        FloatingActionButton myFab = this.findViewById(R.id.btn_new_location);
        if (nfcAdapter == null) {
            Toast.makeText(getApplicationContext(), R.string.nfc_unavailable, Toast.LENGTH_SHORT).show();
        }

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nfcAdapter == null) {
                    Toast.makeText(getApplicationContext(), R.string.nfc_unavailable, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.wait_for_nfc_card, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void handleTagConnection(Intent intent) {
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent.getAction())) {
            String id = byteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));

            FetchTagTask fetchTagTask = new FetchTagTask();
            try {
                String location = fetchTagTask.execute(id).get();
                if (location != null) {
                    new UpdateLocationTask(this).execute(location, PreferencesUtility.getUser().getUsername());
                } else {
                    Toast.makeText(getApplicationContext(), R.string.nfc_tag_not_supported, Toast.LENGTH_SHORT).show();
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.nfc_tag_not_supported, Toast.LENGTH_SHORT).show();
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
    /* NfcActivity is declared in manifest to have launchMode singleTop
     * so if activity already at top of back stack this method gets
     * called rather than a new instance with onCreate
     */
    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        Log.i("Hiiiiiiiiiiiiiiiiiiii", "onNewIntent called");
        setIntent(intent); // update this activity intent to be new one
    }
}