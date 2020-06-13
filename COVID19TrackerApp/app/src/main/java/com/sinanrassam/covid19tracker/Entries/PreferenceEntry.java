package com.sinanrassam.covid19tracker.Entries;

public class PreferenceEntry {
    private static String mFirstName = null;
    private static String mLastName = null;
    private static String mEmail = null;
    private static String mUsername = null;

    public PreferenceEntry(String firstName, String lastName, String username, String email) {
        mFirstName = firstName;
        mLastName = lastName;
        mUsername = username;
        mEmail = email;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }
}
