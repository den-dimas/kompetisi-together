package com.kompor.api.utils;

public class Resource<T> {
    Boolean success;
    private final T result;
    private final String res_msg;

    private Resource(Boolean success, T result, String res_msg) {
        this.success = success;
        this.result = result;
        this.res_msg = res_msg;
    }

    public static class Success<T> extends Resource<T> {
        public Success(T result, String message) {
            super(true, result, message);
        }
    }

    public static class Error<T> extends Resource<T> {
        public Error(String message) {
            super(false, null, message);
        }
    }

    public static class Loading<T> extends Resource<T> {
        public Loading() {
            super(null, null, null);
        }
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getRes_msg() {
        return res_msg;
    }

}
