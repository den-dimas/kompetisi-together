package com.kompor.action;

import android.util.Log;

import com.google.gson.Gson;
import com.kompor.api.RetrofitClient;
import com.kompor.api.model.response.ApiResponse;
import com.kompor.api.utils.AuthResource;
import com.kompor.api.utils.Resource;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Response;

public abstract class BaseAction {
    private static final String BASE_API_URL = "http://192.168.1.43:5000";

    public static <T> Single<Resource<T>> apiRequest(Single<Response<Resource<T>>> apiToBeCalled) {
        return apiToBeCalled.subscribeOn(Schedulers.io()).map((response) -> {
            if (response.isSuccessful()) {
                assert response.body() != null;
                return new Resource.Success<>(response.body().getResult(), response.message());
            } else if (response.code() >= 400 && response.code() < 600) {
                Gson gson = new Gson();

                ApiResponse<T> d = gson.fromJson(response.errorBody().string(), ApiResponse.class);

                return new Resource.Success<T>(null, d.getRes_msg());
            } else {
                return new Resource.Error<T>(response.message());
            }
        }).onErrorReturn(throwable -> {
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                return new Resource.Error<>(httpException.getMessage());
            } else {
                return new Resource.Error<>(throwable.getMessage());
            }
        });
    }

    public static <T> Single<AuthResource<T>> authApiRequest(Single<Response<AuthResource<T>>> apiToBeCalled) {

        return apiToBeCalled.subscribeOn(Schedulers.io()).map(response -> {
            if (response.isSuccessful()) {
                return new AuthResource.Success<>(response.body().getResult(), response.body().getRes_msg(), response.body().getToken());
            } else if (response.code() >= 400 && response.code() < 600) {
                Gson gson = new Gson();

                ApiResponse<T> d = gson.fromJson(response.errorBody().string(), ApiResponse.class);

                return new AuthResource.Success<T>(null, d.getRes_msg(), null);
            } else {
                return new AuthResource.Error<T>(response.message());
            }
        }).onErrorReturn(throwable -> {
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                return new AuthResource.Error<>(httpException.getMessage());
            } else {
                return new AuthResource.Error<>(throwable.getMessage());
            }
        });
    }

    public static <T> T getService(Class<T> ApiClass) {
        return RetrofitClient.getClient(BASE_API_URL).create(ApiClass);
    }

    public static <T> T getServiceWithAuth(Class<T> ApiClass) {
        return RetrofitClient.getClientWithAuth(BASE_API_URL).create(ApiClass);
    }
}
