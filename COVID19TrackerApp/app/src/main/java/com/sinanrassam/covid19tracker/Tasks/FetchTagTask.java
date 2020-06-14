package com.sinanrassam.covid19tracker.Tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.sinanrassam.covid19tracker.Entries.User;
import com.sinanrassam.covid19tracker.MainActivity;
import com.sinanrassam.covid19tracker.R;
import com.sinanrassam.covid19tracker.Utils.PreferencesUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchTagTask extends AsyncTask<String, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public FetchTagTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Integer doInBackground(String... params) {
        int responseCode = 0;
        try {
            URL url = new URL(PreferencesUtility.getApiUrl() + "/tags/" + params[0] + "/" + params[1]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            responseCode = conn.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    protected void onPostExecute(Integer responseCode) {
        String msg;
        if ((responseCode >= 200) && (responseCode <= 299)) {
            msg = mContext.getResources().getString(R.string.action_location_saved);
        } else {
            msg = mContext.getResources().getString(R.string.action_request_failed);
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
