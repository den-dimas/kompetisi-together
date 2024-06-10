package com.kompor.api.model;

public class LoginInfo {
    String id;
    String token;
    String role;

    public LoginInfo(String id, String token, String role) {
        this.id = id;
        this.token = token;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
