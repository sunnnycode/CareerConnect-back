package com.careerconnect.backend.domain.user.dto;

public class CustomUserDetail {

    private String username;
    private String location;

    // Constructors, getters, and setters
    public CustomUserDetail(String username, String location) {

        this.username = username;
        this.location = location;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}

