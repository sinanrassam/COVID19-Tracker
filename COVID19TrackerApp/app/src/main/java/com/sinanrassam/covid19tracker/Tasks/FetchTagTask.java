package com.sinanrassam.covid19tracker.Tasks;

import android.os.AsyncTask;

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

public class FetchTagTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        String location = null;
        try {
            URL url = new URL(PreferencesUtility.getApiUrl() + "/tags/" + params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

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
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);
                location = (String) jsonObject.get("location");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return location;
    }
}
