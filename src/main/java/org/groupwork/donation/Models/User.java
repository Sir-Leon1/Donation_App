package org.groupwork.donation.Models;

public class User {
    private final String email;
    private final String username;
    private final String location;
    private final String userType;
    private final String phoneNo;
    private final String website;

    public User(String email, String username, String location, String userType, String phoneNo, String website) {
        this.email = email;
        this.username = username;
        this.location = location;
        this.userType = userType;
        this.phoneNo = phoneNo;
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public String getUserType() {
        return userType;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getWebsite() {
        return website;
    }
}
