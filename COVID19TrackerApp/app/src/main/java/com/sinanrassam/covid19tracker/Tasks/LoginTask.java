package com.sinanrassam.covid19tracker.Tasks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.sinanrassam.covid19tracker.MainActivity;
import com.sinanrassam.covid19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginTask extends AsyncTask<String, Void, Boolean> {
    public static final String API_URL = "http://10.0.2.2:8080/TicketerRestfulService/api";

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    public LoginTask(Context context) {
        this.mContext = context;
    }

    protected Boolean doInBackground(String... params) {
        boolean isLoggedIn = false;
        try {
            URL url = new URL(API_URL + "/users/" + params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

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
                JSONArray jsonArray = (JSONArray) json.get("users");
                for (int i = 0; (i < jsonArray.length()) && !isLoggedIn; i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String password = (String) jsonObject.get("password");
                    isLoggedIn = password.equals(params[1]);
                }
            }
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isLoggedIn;
    }

    protected void onPostExecute(Boolean isLoggedIn) {
        String msg;
        if (isLoggedIn) {
            msg = mContext.getResources().getString(R.string.action_sign_in_successful);
            Intent intent = new Intent(mContext, MainActivity.class);
            ActivityCompat.finishAffinity((Activity) mContext);
            mContext.startActivity(intent);
        } else {
            msg = mContext.getResources().getString(R.string.action_sign_in_unsuccessful);
        }
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
