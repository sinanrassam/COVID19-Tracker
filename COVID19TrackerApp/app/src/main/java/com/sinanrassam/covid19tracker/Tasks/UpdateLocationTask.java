package com.sinanrassam.covid19tracker.Tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.sinanrassam.covid19tracker.LoginActivity;
import com.sinanrassam.covid19tracker.MainActivity;
import com.sinanrassam.covid19tracker.R;
import com.sinanrassam.covid19tracker.Utils.PreferencesUtility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UpdateLocationTask extends AsyncTask<String, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public UpdateLocationTask(Context context) {
        this.mContext = context;
    }

    protected Integer doInBackground(String... params) {
        int responseCode = 0;
        try {
            String postData = URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&";
            postData += URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");

            URL url = new URL(PreferencesUtility.getApiUrl() + "/tracks/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // Send the request to the server
            OutputStream outputStream = conn.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(postData);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            responseCode = conn.getResponseCode();

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    protected void onPostExecute(Integer responseCode) {
        String msg;
        if ((responseCode >= 200) && (responseCode <= 299)) {
            msg = "Location Updated!";
        } else {
            msg = "Location Update Failed";
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
