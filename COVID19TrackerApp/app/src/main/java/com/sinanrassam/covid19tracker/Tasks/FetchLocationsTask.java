package com.sinanrassam.covid19tracker.Tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FetchLocationsTask extends AsyncTask<String, Void, Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    private ListFragment mFragment;

    private List<Map<String, String>> mTicketList;

    public FetchLocationsTask(ListFragment fragment) {
        this.mFragment = fragment;
        this.mContext = mFragment.getContext();
    }

    @Override
    protected Integer doInBackground(String... params) {
        int responseCode = 0;
        try {
            URL url = new URL(PreferencesUtility.getApiUrl() + "/tracks/" + params[0]);
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

            responseCode = conn.getResponseCode();

            conn.disconnect();

            if (json != null) {
                JSONArray jsonArray = (JSONArray) json.get("tracks");
                mTicketList = new ArrayList<>(jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    String location = (String) jsonObject.get("location");
                    String checkInDate = (String) jsonObject.get("checkInDate");

                    Map<String, String> ticket = new HashMap<>();

                    ticket.put("Check In Date", checkInDate);
                    ticket.put("Location", location);

                    mTicketList.add(ticket);
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return responseCode;
    }

    protected void onPostExecute(Integer responseCode) {
        if ((responseCode >= 200) && (responseCode <= 299)) {
            SimpleAdapter adapter = new SimpleAdapter(mContext, mTicketList,
                    android.R.layout.simple_list_item_2,
                    new String[]{"Check In Date", "Location"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            mFragment.setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(mContext, "Failed to fetch locations", Toast.LENGTH_SHORT).show();
        }
    }
}
