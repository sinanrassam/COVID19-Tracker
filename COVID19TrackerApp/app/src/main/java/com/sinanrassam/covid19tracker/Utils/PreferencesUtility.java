package com.sinanrassam.covid19tracker.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.sinanrassam.covid19tracker.Entries.PreferenceEntry;

public class PreferencesUtility {
    private final static String KEY_FIRST_NAME = "firstName";
    private final static String KEY_LAST_NAME = "lastName";
    private final static String KEY_EMAIL = "email";
    private final static String KEY_USERNAME = "username";

    private static SharedPreferences mSharedPreferences;

    public PreferencesUtility(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public static void setSharedPreferences(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean setUserInfo(PreferenceEntry preferenceEntry) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(KEY_FIRST_NAME, preferenceEntry.getFirstName());
        editor.putString(KEY_LAST_NAME, preferenceEntry.getLastName());
        editor.putString(KEY_EMAIL, preferenceEntry.getEmail());
        editor.putString(KEY_USERNAME, preferenceEntry.getUsername());
        return editor.commit();
    }

    public static PreferenceEntry getUserInfo() {
        String firstName = mSharedPreferences.getString(KEY_FIRST_NAME, "");
        String lastName = mSharedPreferences.getString(KEY_LAST_NAME, "");
        String email = mSharedPreferences.getString(KEY_EMAIL, "");
        String username = mSharedPreferences.getString(KEY_USERNAME, "");
        return new PreferenceEntry(firstName, lastName, email, username);
    }
}
