package com.sinanrassam.covid19tracker.Entries;

public class User {

    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mUsername;

    public User(String firstName, String lastName, String email, String username) {
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mUsername = username;
    }

    public User() {
        this(null, null, null,null);
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getUsername() {
        return mUsername;
    }

}
