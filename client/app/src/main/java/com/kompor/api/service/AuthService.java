package com.kompor.api.service;

import com.kompor.api.model.Participant;
import com.kompor.api.model.Penyelenggara;
import com.kompor.api.utils.AuthResource;
import com.kompor.api.utils.Resource;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthService {
    @FormUrlEncoded
    @POST("/penyelenggara/login")
    Single<Response<AuthResource<Penyelenggara>>> loginPenyelenggara(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/penyelenggara/register")
    Single<Response<AuthResource<Penyelenggara>>> registerPenyelenggara(
            @Field("email") String email,
            @Field("password") String password,
            @Field("logo") String logo,
            @Field("nama") String nama,
            @Field("deskripsi") String deskripsi
    );

    @FormUrlEncoded
    @POST("/participant/login")
    Single<Response<AuthResource<Participant>>> loginParticipant(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/participant/register")
    Single<Response<AuthResource<Participant>>> registerParticipant(
            @Field("email") String email,
            @Field("password") String password,
            @Field("nama") String nama,
            @Field("tanggal_lahir") String tanggalLahir,
            @Field("angkatan") Integer angkatan,
            @Field("sekolah") String sekolah
    );
}
