package com.kompor.api;

import androidx.annotation.NonNull;

import com.kompor.ui.activity.MainActivity;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer " + MainActivity.getToken())
                .build()
        );
    }
}
