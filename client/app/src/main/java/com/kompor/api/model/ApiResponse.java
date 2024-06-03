package com.kompor.api.model;

public class ApiResponse<T> {
    private final T result;
    private final String res_msg;

    public ApiResponse(T result, String res_msg) {
        this.result = result;
        this.res_msg = res_msg;
    }

    public T getResult() {
        return result;
    }

    public String getRes_msg() {
        return res_msg;
    }
}
