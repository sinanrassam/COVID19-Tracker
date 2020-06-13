package com.sinanrassam.covid19tracker.Entries;

import java.net.URL;

public class PreferenceEntry {
    private static User mUser = null;
    private static String mApiUrl = null;

    public PreferenceEntry() {
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public String getApiUrl() {
        return mApiUrl;
    }

    public void setApiUrl(String url) {
        mApiUrl = url;
    }
}
