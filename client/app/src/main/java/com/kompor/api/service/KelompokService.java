package com.kompor.api.service;

import com.kompor.api.model.kelompok.KelompokDetails;
import com.kompor.api.model.kelompok.Kelompok;
import com.kompor.api.utils.Resource;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KelompokService {
    @FormUrlEncoded
    @POST("/kelompok")
    Single<Response<Resource<ArrayList<Kelompok>>>> getAllKelompok(
            @Field("id_kompetisi") String id
    );

    @FormUrlEncoded
    @POST("/kelompok/create")
    Single<Response<Resource<Kelompok>>> postCreateKelompok(
            @Field("id_kompetisi") String idKompetisi,
            @Field("id_pembuat") String idParticipant
    );

    @GET("/kelompok/{id}")
    Single<Response<Resource<ArrayList<KelompokDetails>>>> getkelompokDetail(
            @Path("id") String id
    );

    @FormUrlEncoded
    @POST("/kelompok/{id}")
    Single<Response<Resource<ArrayList<KelompokDetails>>>> postChangePendaftaranKelompok(
            @Path("id") String id,
            @Field("id_ketua") String idKetua,
            @Field("id_participant") String idParticipant,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("/kelompok/{id}/apply")
    Single<Response<Resource<ArrayList<KelompokDetails>>>> postDaftarKelompok(
      @Path("id") String id,
      @Field("id_participant") String idParticipant
    );
}
