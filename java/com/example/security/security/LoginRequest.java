package com.example.security.security;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginRequest {

    @JsonProperty("userName")
    private String UserName;
    private String password;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String userName, String password) {
        UserName = userName;
        this.password = password;
    }

    public LoginRequest() {
    }


}
