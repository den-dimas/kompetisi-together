package com.kompor.api.model;

public class AuthApiResponse<T> {
    private final T result;
    private final String res_msg;
    private final String token;

    public AuthApiResponse(T result, String res_msg, String token) {
        this.result = result;
        this.res_msg = res_msg;
        this.token = token;
    }

    public T getResult() {
        return result;
    }
    public String getRes_msg() {
        return res_msg;
    }
    public String getToken() {
        return token;
    }
}
