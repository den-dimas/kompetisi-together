package com.kompor.api.utils;

public class AuthResource<T> extends Resource<T> {
    private final String token;

    private AuthResource(Boolean success, T result, String res_msg, String token) {
        super(success, result, res_msg);
        this.token = token;
    }

    public static class Success<T> extends AuthResource<T> {
        public Success(T result, String message, String token) {
            super(true, result, message, token);
        }
    }

    public static class Error<T> extends AuthResource<T> {
        public Error(String message) {
            super(false, null, message, null);
        }
    }

    public static class Loading<T> extends AuthResource<T> {
        public Loading() {
            super(null, null, null, null);
        }
    }

    public String getToken() {
        return token;
    }
}
