package com.sinanrassam.covid19tracker.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sinanrassam.covid19tracker.Entries.User;

public class PreferencesUtility {
    private final static String KEY_FIRST_NAME = "firstName";
    private final static String KEY_LAST_NAME = "lastName";
    private final static String KEY_EMAIL = "email";
    private final static String KEY_USERNAME = "username";
    private final static String KEY_API_URL = "apiUrl";

    private final static String DEFAULT_API_URL = "http://192.168.1.9:8080/COVID19TrackerAPI/api";

    private static SharedPreferences mSharedPreferences;

    public PreferencesUtility(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public static void setSharedPreferences(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean setUserInfo(User user) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, user.getFirstName());
        editor.putString(KEY_LAST_NAME, user.getLastName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_USERNAME, user.getUsername());
        return editor.commit();
    }

    public static boolean setApiUrl(String apiUrl) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_API_URL, apiUrl);
        return editor.commit();
    }

    public static String getApiUrl() {
        return mSharedPreferences.getString(KEY_API_URL, DEFAULT_API_URL);
    }

    public static User getUser() {
        String firstName = mSharedPreferences.getString(KEY_FIRST_NAME, "");
        String lastName = mSharedPreferences.getString(KEY_LAST_NAME, "");
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        String username = mSharedPreferences.getString(KEY_USERNAME, "");
        return new User(firstName, lastName, email, username);
    }
}
