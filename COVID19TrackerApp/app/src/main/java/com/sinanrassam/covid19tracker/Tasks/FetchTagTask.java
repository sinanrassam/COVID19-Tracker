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

public class FetchTagTask extends AsyncTask<String, Void, Boolean> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public FetchTagTask(Context context) {
        this.mContext = context;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        boolean isSuccessful = false;
        try {
            URL url = new URL(PreferencesUtility.getApiUrl() + "/tags/" + params[0] + "/" + params[1]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Read the server response and return it as JSON
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject json = new JSONObject(sb.toString());

            bufferedReader.close();
            inputStream.close();

            conn.disconnect();

            if (json != null) {
                JSONArray jsonArray = (JSONArray) json.get("tags");
                isSuccessful = jsonArray.length() == 1;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return isSuccessful;
    }

    protected void onPostExecute(Boolean isSuccessful) {
        String msg;
        if (isSuccessful) {
            msg = mContext.getResources().getString(R.string.action_location_saved);
        } else {
            msg = mContext.getResources().getString(R.string.action_request_failed);
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
