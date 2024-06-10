package com.kompor.api.service;

import com.kompor.api.model.Kompetisi;
import com.kompor.api.model.Participant;
import com.kompor.api.model.Penyelenggara;
import com.kompor.api.utils.AuthResource;
import com.kompor.api.utils.Resource;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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

    @GET("/penyelenggara/{id}")
    Single<Response<Resource<Penyelenggara>>> penyelenggaraById(
            @Path("id") String id
    );

    @GET("/penyelenggara/{id}/kompetisi")
    Single<Response<Resource<ArrayList<Kompetisi>>>> getPenyelenggaraKompetisi(
            @Path("id") String id
    );

    @FormUrlEncoded
    @PUT("/penyelenggara/{id}")
    Single<Response<Resource<Penyelenggara>>> putPenyelenggara(
            @Path("id") String id,
            @Field("email") String email,
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

    @GET("/participant/{id}")
    Single<Response<Resource<Participant>>> participantById(
            @Path("id") String id
    );

    @GET("/participant/{id}/kompetisi")
    Single<Response<Resource<ArrayList<Kompetisi>>>> getParticipantKompetisi(
            @Path("id") String id
    );

    @FormUrlEncoded
    @PUT("/participant/{id}")
    Single<Response<Resource<Participant>>> putParticipant(
            @Path("id") String id,
            @Field("email") String email,
            @Field("nama") String nama,
            @Field("sekolah") String sekolah
    );
}
